import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LireFichierCSV {

    //Methode statique qui prend en argument un chemin String
    //qui est le fichier CSV et le renvoi sous forme de String[][]

    public static String [][] lireCSV(String chemin){

        List<String[]> lignes = new ArrayList<>();
        //on prepare une liste pour recevoir notre set de donnees

        //Pour eviter le risque que notre bloc de code echoue on le passe dans un bloc try and catch
        //pour gerer les exceptions

        try (BufferedReader br =  new BufferedReader(
                new InputStreamReader( new FileInputStream(chemin), StandardCharsets.UTF_8))) {

            String line;
            boolean first = true;


            //Boucle pour s'assurer que le fichier soit lu jusqu'a la fin ligne par ligne;
            while ((line = br.readLine()) != null) {

                //condition pour enlever le caractere BOM precedant le tout premier champ d'un fichier UTF-8
                // (Ceci est dans la majorite des cas).
                //La condition if ne s'applique que sur la premier ligne retourne
                if (first) {
                    line = removeBOM(line);
                    first = false;
                }

                //Decoupage des lignes recues en colonnes puis nettoyage des espaces en debut et fin de la valeur
                String [] cols = line.split(",", -1);

                for (int i = 0; i < cols.length; i++){
                    cols[i] = cols[i].trim();
                }

                //Empiler les ligne lues et nettoyees dans une collection, pour ensuite
                // pouvoir retourner tout le fichier sous forme de String[][].
                lignes.add(cols);

            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture CSV: " + e.getMessage(), e);
        }

        //Conversion des lignes en tableau 2D via .toArray
        return lignes.toArray(new String[0][]);

    }

    //Methode pour retirer le caractere BOM si toutefois il se trouve en debut de la chaine
    private static String removeBOM(String s) {
        return (s != null && !s.isEmpty() && s.charAt(0) == '\uFEFF') ? s.substring(1) : s;
    }

}

