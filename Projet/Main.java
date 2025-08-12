
public class Main {
    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.err.println("Erreur: Le fichier en argument doit être de type CSV.");
//            System.exit(1);
//        }

        String cheminFichier =
                "C:\\Users\\angig\\OneDrive\\Desktop\\TOUS LES DOCUMENTS\\SDA\\session 2\\programmation avancee\\projet_final_PA_UA3\\titanic_projetUA3.csv";
        ;

        // 1 - Lecture du fichier CSV
        String[][] data = LireFichierCSV.lireCSV(cheminFichier);

        // 2 - Suppression et remplacement des valeurs manquantes
        String[][] dataPropre = ValeursManquantes.remplacerNaN(data);

        // 3 - Standardisation des données
        String[][] donneesFinales = Standardisation.standardiser(dataPropre);

        // 4 - Sauvegarde des données nettoyées
        SauvegardeFichierCSV.sauvegarderCSV(donneesFinales, "donnees_nettoyees1.csv");

        System.out.println("✅ Nettoyage terminé. Fichier sauvegardé sous 'donnees_nettoyees.csv'.");
    }
}
