#  Projet Java – Nettoyage de données CSV

## Objectif
Développer un programme Java pour effectuer le **nettoyage automatique** d’un fichier CSV contenant des données brutes.

---

##  Fonctionnalités implémentées

- Creation d'une classe Main pour implementer le programme
  
- Lecture d’un fichier CSV :
  Methode:
  public static String [][] lireCSV(String chemin)
  private static String removeBOM(String s)

- Détection et suppression/remplacement des **valeurs manquantes**
  Methode:
  
- Standardisation des chaînes de caractères (ex : mise en **majuscules**)
  Methode:
  
- Sauvegarde des données nettoyées dans un nouveau fichier
  Methode:
  public static void sauvegarderCSV(String[][] data, String chemin)
  private static String joinCSV(String[] row)


---

##  Technologies utilisées

- Java (JDK 8+)
- IDE : IntelliJ (au choix)
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
## Collaborateurs 
NGO NGWE MADELEINE IRMA



