#  Projet Java – Nettoyage de données CSV

## Collaborateurs 
NGO NGWE MADELEINE IRMA  — valeurs manquantes 
FEUSSI NGUEMKAM ANGELE BLANDINE— standardisation 

## Objectif
Développer un programme Java pour effectuer le **nettoyage automatique** d’un fichier CSV contenant des données brutes.

---

##  Fonctionnalités implémentées

- Creation d'une classe Main pour implementer le programme
  
- Lecture d’un fichier CSV :
  Methode:`String[][] data = LireFichierCSV.lireCSV(cheminFichier);`
  
- Détection et suppression/remplacement des **valeurs manquantes**
  Methode:`ValeursManquantes.remplacerNaN(data);`
  
- Standardisation des chaînes de caractères (ex : mise en **minuscule**)
  Methode:`Standardisation.standardiser(dataPropre);`
  
- Sauvegarde des données nettoyées dans un nouveau fichier
  Methode:`SauvegardeFichierCSV.sauvegarderCSV(donneesFinales, "donnees_nettoyees1.csv");`

---

##  Technologies utilisées

- Java (JDK 8+)
- IDE : IntelliJ 
- Fichiers de test au format `.csv`
- Fichiers de test au format `.txt`

---
## Bibliotheques utilisees

- import java.io.BufferedReader;
- import java.io.FileInputStream;
- import java.io.IOException;
- import java.io.InputStreamReader;
- import java.nio.charset.StandardCharsets;
- import java.util.ArrayList;
- import java.util.List;
- import java.io.OutputStreamWriter;
- import java.io.FileOutputStream;
- import java.nio.charset.StandardCharsets;

---
## Compilation & exécution
```bash
javac -d out src/*.java
java -cp out Main```

---



