package uoc.JenaReasoner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import uoc.utils.FileUtilities;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class JenaReasoner {

private FileUtilities fileUtilities;
	
	static Logger logger = Logger.getLogger(JenaReasoner.class);
	
	public static final String NSHABITS = "http://www.semanticweb.org/ontologies/2018/1/habits#";
	public JenaReasoner() {
		super();
		fileUtilities = new FileUtilities();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JenaReasoner app = new JenaReasoner();
		app.run();
	}
	
	private void run() {
		List<List<String>> fileContents = null;
		try {
			fileContents = fileUtilities.readFileFromClasspath("data/questionnaire_very_short.csv");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ERROR: I/O error");
			e.printStackTrace();
		}
		if (fileContents == null) {
			return;
		}
		BasicConfigurator.configure();
		Model model = FileManager.get().loadModel(
				"file:src/data/PAC3.owl");
		
		OntModel om = sectionA(fileContents, model);
		
		sectionB(om);

	}
	
	/**
	 * @param fileContents
	 * @param model
	 */
	private OntModel sectionA(List<List<String>> fileContents, Model model) {
		OntModel m = createOntModel(model);
		Individual person,alcohol,tobacco,weight;
		for (List<String> row : fileContents) {	
			person = createIndividual(m, row.get(0),"Person");
			if(person != null){
			
			//add drinks	
			alcohol = createIndividual(m, row.get(1),"Alcohol");
			addObjectPropertyToIndividual(m, person, "alcoholDrinksPerDay", alcohol);
			
			//add cigarettes
			tobacco = createIndividual(m, row.get(6),"Tobacco");
			addObjectPropertyToIndividual(m, person, "cigarretesSmokedPerDay", tobacco);
			
			//add weight
			weight = createIndividual(m, row.get(5),"Weight");
			addObjectPropertyToIndividual(m, person, "hasWeight", weight);
			
			}
		}
		
		//Print People
		printPeople(m);
		
		//Print Tobacco > 3
		printMoreTobaccoThan(m, 3);
		
		return m;
	}
	/**
	 * Create an individual from class
	 * @param m
	 * @param name
	 * @return
	 */
	private Individual createIndividual(OntModel m, String className, String name) {
		OntClass c = m.getOntClass(NSHABITS+name);		
		return c.createIndividual(NSHABITS+className);
	}
	
	/**
	 * Function add a property with a value, if value is diferent ""
	 * @param m
	 * @param i
	 * @param property
	 * @param value
	 */
	private void addObjectPropertyToIndividual(OntModel m, Individual i,
			String property, Individual value) {
		i.addProperty(m.getProperty(NSHABITS+property), value);	
	}	
	
	/**
	 * @param m
	 */
	private void printMoreTobaccoThan(OntModel m, int limit) {
		System.out.println("Tobacco > limit:");
		ExtendedIterator<? extends OntResource> it = m.getOntClass(NSHABITS+"Tobacco").listInstances();
		while (it.hasNext()) {
			OntResource ontResource = (OntResource) it.next();
			if (m.getProperty(NSHABITS+"cigarretesSmokedPerDay") != null && ontResource.getPropertyValue(m.getProperty(NSHABITS+"cigarretesSmokedPerDay")) != null){
				
				Integer length = ontResource.getPropertyValue(m.getProperty(NSHABITS+"cigarretesSmokedPerDay")).toString().length();
				String valueCigarettesString = ontResource.getPropertyValue(m.getProperty(NSHABITS+"cigarretesSmokedPerDay")).toString().substring(length-1);
				Integer valueCigarrettes = Integer.parseInt(valueCigarettesString);
				
				if (valueCigarrettes > limit){
					System.out.println(" - " + PrintUtil.print(ontResource));
				}
			}
		}
	}
	
	/**
	 * @param m
	 */
	private void printPeople(OntModel m) {
		System.out.println("People:");
		ExtendedIterator<? extends OntResource> it = m.getOntClass(NSHABITS+"Person").listInstances();
		while (it.hasNext()) {
			OntResource ontResource = (OntResource) it.next();
			System.out.println(" - " + PrintUtil.print(ontResource));
		}
	}
	

	/**
	 * Exercise 2 B
	 * @param om
	 */
	private void sectionB(OntModel om) {
		List<Rule> rules = Rule.rulesFromURL("file:src/data/healthyRules.rules");
		Reasoner reasoner2 = new GenericRuleReasoner(rules);
		reasoner2 = reasoner2.bindSchema(om);
		InfModel infmodel = ModelFactory.createInfModel(reasoner2, om);
		printHighAlcohol(infmodel);
	}
	
	private void printHighAlcohol(InfModel m) {
		
		System.out.println("Values of high alcohol: ");
		if(m!=null)
			printStatements(m, null, RDF.type, m.getResource(NSHABITS+"HighAlcoholicConsumption"));
	
	}
	
	/**
	 * Generate an OntModel
	 * @param model
	 * @return
	 */
	private OntModel createOntModel(Model model) {
		
		ModelMaker maker = ModelFactory.createMemModelMaker();
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		spec.setImportModelMaker(maker);
		OntModel m = ModelFactory.createOntologyModel(spec, model);
		return m;
	}
	
	public static void printStatements(Model m, Resource s, Property p, Resource o) {
		if(m!=null && (s!= null || p != null || o!= null)){
		    for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
		        Statement stmt = i.nextStatement();
		        if(stmt.getObject().isURIResource()){
					if(stmt.toString().contains("7f")==false) {
		        		System.out.println(" - " + PrintUtil.print(stmt));
		        	}
		        }else if(stmt.getObject().isLiteral()){
		        	System.out.println(" - " + PrintUtil.print(stmt));
		        }
		    }
		}
	}
}
