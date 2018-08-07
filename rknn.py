import math
import numpy as np
import pandas as pd
import random
import time
import texttable as tt
from sklearn import preprocessing, cross_validation, neighbors, metrics
from collections import namedtuple

'''
Funció que accepta cinc paràmetres:
    file: arxiu a carregar
    k: nombre de knn
    r: nombre de rknn
    m: nombre d'atributs de cada knn (arrel quadrada del nombre total 
        d'atributs)
    num_attr: un cop acabat l'algorisme rknn, el nombre d'atributs que 
        es vol fer servir per a llençar un knn, d'entre els atributs 
        més rellevants
        
Exemple d'utilització:
    rknn("wine3c.csv", 3, 10000, 3, 7)

    La crida està a la part final d'aquest codi, només cal executar-lo
    per a obtenir resultats al terminal
'''
def rknn(file, k, r, m, num_attr):
    '''
    Gestió d'errors
    '''
    if (m != int(math.sqrt(13))):
        print("El valor d'm ha de ser l'arrel quadrada del nombre d'atributs")
        return
    if (num_attr not in range(1,14)):
        print("El nombre d'atributs ha d'estar entre 1 i 13")
        return
    
    '''
    Càclcul del temps d'exexució del rknn, inici
    '''
    start_time_rknn = time.time()
    
    '''
    Llegim el fitxer
    '''
    df = pd.read_csv(file)
    
    '''
    Iniciem els arrays dels atributs i les variables
    '''
    
    ALCOHOL_acc = []
    MALIC_acc = []
    ASH_acc = []
    ALCALINITY_acc = []
    MAGNESIUM_acc = []
    PHENOLS_acc = []
    FLAVANOIDS_acc = []
    NONFLAVANOIDS_acc = []
    PROANTHOCYANIS_acc = []
    COLOR_acc = []
    HUE_acc = []
    OD280_OD315_acc = []
    PROLINE_acc = []
    
    ALCOHOL_num = 0
    MALIC_num = 0
    ASH_num = 0
    ALCALINITY_num = 0
    MAGNESIUM_num = 0
    PHENOLS_num = 0
    FLAVANOIDS_num = 0
    NONFLAVANOIDS_num = 0
    PROANTHOCYANIS_num = 0
    COLOR_num = 0
    HUE_num = 0
    OD280_OD315_num = 0
    PROLINE_num = 0
    
    '''
    Triem tots els atributs a X menys el de classe
    '''
    X_norm_cols = df.columns[:len(df.columns)-1]
    
    X_norm = df[X_norm_cols]
    
    '''
    Normalitzem les dades
    '''
    X_aux = ((X_norm-X_norm.min())/(X_norm.max()-X_norm.min()))
    
    '''
    Triem l'atribut classe a Y
    '''
    Y = df["CLASS"]
    
    '''
    Generem el conjunt d'entrenament a partir del 50% de les dades
    '''
    X_train_aux, X_test_aux, Y_train, Y_test = cross_validation.train_test_split(X_aux, Y, test_size=0.5)
    
    '''
    iteració de 0 a r, sent r el paràmetre d'entrada del procediment
    '''
    for x in range(0,r):
        '''
        Triem tres dels 13 atributs a l'atzar
        '''
        all_columns_list = X_train_aux.columns.values
        random1 = random.randint(0,len(all_columns_list)-m)            
        random2 = random1+3
        
        '''
        Tallem verticalment els conjunts de test i entrenament amb els tres atributs aleatoris anteriors
        '''
        X_train_slice_list = all_columns_list[random1:random2]
        X_test_slice_list = all_columns_list[random1:random2]
        X_train = X_train_aux[X_train_slice_list]
        X_test = X_test_aux[X_test_slice_list]
        
        '''
        Creem el knn amb k veïns
        '''
        clf = neighbors.KNeighborsClassifier(n_neighbors=k)
        
        '''
        Entrenem el conjunt d'entrenament i en calculem la precisió a partir del de test
        '''
        clf.fit(X_train, Y_train)
        accuracy = clf.score(X_test,Y_test)
        
        '''
        loop per a guardar a les arrays de cada atribut la precisió del knn quan l'atribut a aparegut a la 
        selecció d'atributs a l'atzar. També guardem el nombre de cops que ha aparegut
        '''
        for x in X_test.columns.values.tolist():
            if x == 'ALCOHOL':
                ALCOHOL_acc.append(float(accuracy))
                ALCOHOL_num += 1
            if x == 'MALIC':
                MALIC_acc.append(float(accuracy))
                MALIC_num += 1
            if x == 'ASH':
                ASH_acc.append(float(accuracy))
                ASH_num += 1
            if x == 'ALCALINITY':
                ALCALINITY_acc.append(float(accuracy))
                ALCALINITY_num += 1
            if x == 'MAGNESIUM':
                MAGNESIUM_acc.append(float(accuracy))
                MAGNESIUM_num += 1
            if x == 'PHENOLS':
                PHENOLS_acc.append(float(accuracy))
                PHENOLS_num += 1
            if x == 'FLAVANOIDS':
                FLAVANOIDS_acc.append(float(accuracy))
                FLAVANOIDS_num += 1
            if x == 'NONFLAVANOIDS':
                NONFLAVANOIDS_acc.append(float(accuracy))
                NONFLAVANOIDS_num += 1
            if x == 'PROANTHOCYANIS':
                PROANTHOCYANIS_acc.append(float(accuracy))
                PROANTHOCYANIS_num += 1
            if x == 'COLOR':
                COLOR_acc.append(float(accuracy))
                COLOR_num += 1
            if x == 'HUE':
                HUE_acc.append(float(accuracy))
                HUE_num += 1
            if x == 'OD280_OD315':
                OD280_OD315_acc.append(float(accuracy))
                OD280_OD315_num += 1
            if x == 'PROLINE':
                PROLINE_acc.append(float(accuracy))
                PROLINE_num += 1
        
    '''
    Calculem el valor del "support" sumant les precisions de l'array de 
    cada atribut i dividint el resultat entre el nombre de vegades que
    ha aparegut
    '''
    if ALCOHOL_num == 0:
        ALCOHOL_score = 0
    else:
        ALCOHOL_score = sum(ALCOHOL_acc)/ALCOHOL_num
        
    if MALIC_num == 0:
        MALIC_score = 0
    else:
        MALIC_score = sum(MALIC_acc)/MALIC_num
        
    if ASH_num == 0:
        ASH_score = 0
    else:
        ASH_score = sum(ASH_acc)/ASH_num
    if ALCALINITY_num == 0:
        ALCALINITY_score = 0
    else:
        ALCALINITY_score = sum(ALCALINITY_acc)/ALCALINITY_num
        
    if MAGNESIUM_num == 0:
        MAGNESIUM_score = 0
    else:
        MAGNESIUM_score = sum(MAGNESIUM_acc)/MAGNESIUM_num
        
    if PHENOLS_num == 0:
        PHENOLS_score = 0
    else:
        PHENOLS_score = sum(PHENOLS_acc)/PHENOLS_num
        
    if FLAVANOIDS_num == 0:
        FLAVANOIDS_score = 0
    else:
        FLAVANOIDS_score = sum(FLAVANOIDS_acc)/FLAVANOIDS_num
        
    if NONFLAVANOIDS_num == 0:
        NONFLAVANOIDS_score = 0
    else:
        NONFLAVANOIDS_score = sum(NONFLAVANOIDS_acc)/NONFLAVANOIDS_num
        
    if PROANTHOCYANIS_num == 0:
        PROANTHOCYANIS_score = 0
    else:
        PROANTHOCYANIS_score = sum(PROANTHOCYANIS_acc)/PROANTHOCYANIS_num
        
    if COLOR_num == 0:
        COLOR_score = 0
    else:
        COLOR_score = sum(COLOR_acc)/COLOR_num
        
    if HUE_num == 0:
        HUE_score = 0
    else:
        HUE_score = sum(HUE_acc)/HUE_num
        
    if OD280_OD315_num == 0:
        OD280_OD315_score = 0
    else:
        OD280_OD315_score = sum(OD280_OD315_acc)/OD280_OD315_num
        
    if PROLINE_num == 0:
        PROLINE_score = 0
    else:
        PROLINE_score = sum(PROLINE_acc)/PROLINE_num
    
    
    '''
    Creem tuples per a guardar el nom i el valor "support" de cada atribut    
    '''
    MyStruct = namedtuple("MyStruct","attributeName, attributeValue")
    t_ALCOHOL = MyStruct("ALCOHOL",ALCOHOL_score)
    t_MALIC = MyStruct("MALIC",MALIC_score)
    t_ASH = MyStruct("ASH",ASH_score)
    t_ALCALINITY = MyStruct("ALCALINITY",ALCALINITY_score)
    t_MAGNESIUM = MyStruct("MAGNESIUM",MAGNESIUM_score)
    t_PHENOLS = MyStruct("PHENOLS",PHENOLS_score)
    t_FLAVANOIDS = MyStruct("FLAVANOIDS",FLAVANOIDS_score)
    t_NONFLAVANOIDS = MyStruct("NONFLAVANOIDS",NONFLAVANOIDS_score)
    t_PROANTHOCYANIS = MyStruct("PROANTHOCYANIS",PROANTHOCYANIS_score)
    t_COLOR = MyStruct("COLOR",COLOR_score)
    t_HUE = MyStruct("HUE",HUE_score)
    t_OD280_OD315 = MyStruct("OD280_OD315",OD280_OD315_score)
    t_PROLINE = MyStruct("PROLINE",PROLINE_score)
    
    listStruct = []
    
    listStruct.append(t_ALCOHOL)
    listStruct.append(t_MALIC)
    listStruct.append(t_ASH)
    listStruct.append(t_ALCALINITY)
    listStruct.append(t_MAGNESIUM)
    listStruct.append(t_PHENOLS)
    listStruct.append(t_FLAVANOIDS)
    listStruct.append(t_NONFLAVANOIDS)
    listStruct.append(t_PROANTHOCYANIS)
    listStruct.append(t_COLOR)
    listStruct.append(t_HUE)
    listStruct.append(t_OD280_OD315)
    listStruct.append(t_PROLINE)
    
    '''
    Ordenem els atributs a partir del valor del seu suport, de menys a més    
    '''
    listStruct.sort(key=lambda tup: tup[1])
    listAttrName = []
    listAttrValue = []
    
    for x in range(len(listStruct)):
        listAttrName.append(listStruct[x].attributeName)
        listAttrValue.append(listStruct[x].attributeValue)
    
    '''
    Reordenem els atributs més rellevants, ara, però, de més a menys 
    '''
    rknnlist_aux = []
    for i in reversed(listAttrName):
        rknnlist_aux.append(i)
    
    '''
    Triem els n atributs més rellevants. El valor d'n ve definit pel
    paràmetre d'entrada num_attr
    '''
    rknnlist = rknnlist_aux[:num_attr]
    
    '''
    Comencem a comptar el temps d'execució del knn per a comparar-lo amb el de l'rknn
    '''
    start_time = time.time()
    
    '''
    Fem el knn de tots els atributs del data set
    Fem l'rknn dels n atributs més rellevants
    '''
    X_norm_cols = df.columns[:len(df.columns)-1]
    X_norm = df[X_norm_cols]
    X = ((X_norm-X_norm.min())/(X_norm.max()-X_norm.min()))
    Y = df["CLASS"]
    X_train, X_test, Y_train, Y_test = cross_validation.train_test_split(X, Y, test_size=0.5)
    X_train_rknn = X_train[rknnlist]
    X_test_rknn = X_test[rknnlist]
    clf = neighbors.KNeighborsClassifier(n_neighbors=3)
    clf.fit(X_train, Y_train)
    '''
    Calculem la precisió del knn
    '''
    accuracy = clf.score(X_test,Y_test)
    clf_rknn = neighbors.KNeighborsClassifier(n_neighbors=3)
    clf_rknn.fit(X_train_rknn, Y_train)
    '''
    Calculem la precisió del knn dels n atributs més rellevants extrets amb
    l'rknn
    '''
    accuracy_rknn = clf_rknn.score(X_test_rknn,Y_test)
    
    '''
    Matriu de configuració del knn
    '''
    y_pred = clf.predict(X_test)
    conf_matx = metrics.confusion_matrix(Y_test, y_pred)
    num_wrong_ex = conf_matx[0][1]+conf_matx[0][2]+conf_matx[1][0]+conf_matx[1][2]+conf_matx[2][0]+conf_matx[2][1]
    '''
    Matriu de precisió del knn dels n atributs més rellevants extrets amb
    l'rknn
    '''
    y_pred_rknn = clf_rknn.predict(X_test_rknn)
    conf_matx_rknn = metrics.confusion_matrix(Y_test, y_pred_rknn)
    num_wrong_ex_rknn = conf_matx_rknn[0][1]+conf_matx_rknn[0][2]+conf_matx_rknn[1][0]+conf_matx_rknn[1][2]+conf_matx_rknn[2][0]+conf_matx_rknn[2][1]
    
    '''
    Calculem el temps d'execució del knn i de l'rknn
    '''
    elapsed_time_rknn = (time.time() - start_time_rknn)
    elapsed_time = (time.time() - start_time)
    print("Elapsed time knn:")
    print("--- %s seconds ---" % (round(elapsed_time,2)))
    print("")
    print("Elapsed time rknn:")
    if (elapsed_time_rknn > 60):
        
        seconds = elapsed_time_rknn%60
        minutes = (elapsed_time_rknn-seconds)/60
        
        print("--- %s minutes %s seconds ---" % (round(minutes), round(seconds, 2)))
    else: 
        print("--- %s seconds ---" % (round(elapsed_time_rknn, 2)))
    print('')
    '''
    Creem la taula de resultats amb la precisió, la matriu de confusió i el 
    nombre d'exemples erronis, tant del knn amb tots els atributs com del
    knn dels n atributs més rellevants aconseguits a partir de l'rknn
    '''
    tab = tt.Texttable()
    headings = ['Metrics', 'Accuracy (%)', 'Confusion matrix', '# wrong examples']
    tab.header(headings)
    names = ['KNN','rKNN']
    Accuracy = [accuracy*100, accuracy_rknn*100]
    Confusion_matrix = [conf_matx, conf_matx_rknn]
    Wrong_examples = [num_wrong_ex, num_wrong_ex_rknn]
    for row in zip(names,Accuracy,Confusion_matrix,Wrong_examples):
        tab.add_row(row)
    s = tab.draw()
    '''
    Imprimim la taula
    '''
    print (s)
    print('')
    '''
    Imprimim el llistat dels atributs per ordre de rellevància, de més a menys
    '''
    print('Llistat ordenat dels atributs més rellevants, de més a menys:')
    print(rknnlist_aux)
    


'''
EXEMPLE DE CRIDA AL PROCEDIMENT RKNN:
'''
rknn("wine3c.csv", 3, 10000, 3, 7)