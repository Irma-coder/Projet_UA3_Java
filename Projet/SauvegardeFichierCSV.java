import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class SauvegardeFichierCSV {

    /**
     * Écrit le tableau 2D dans un fichier CSV UTF-8.
     *
     * @param data   Données à écrire (data[ligne][colonne]).
     * @param chemin Chemin de sortie du fichier .csv
     */
    public static void sauvegarderCSV(String[][] data, String chemin) {
        if (data == null) return; // rien à écrire
        final String EOL = System.lineSeparator(); // fin de ligne selon l'OS (Windows, macOS, Linux)

        // try-with-resources : ferme automatiquement le writer même en cas d'erreur
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(chemin), StandardCharsets.UTF_8))) {

            for (String[] row : data) {
                bw.write(joinCSV(row)); // convertit une ligne en CSV valide
                bw.write(EOL);          // passe à la ligne suivante
            }
        } catch (IOException e) {
            // On re-lève une RuntimeException avec le message d'origine pour simplifier l'appelant
            throw new RuntimeException("Erreur écriture CSV : " + e.getMessage(), e);
        }
    }

    private static String joinCSV(String[] row) {
        if (row == null) return "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < row.length; i++) {
            String v = (row[i] == null) ? "" : row[i];

            boolean mustQuote =
                    v.contains(",") ||
                            v.contains("\"") ||
                            v.contains("\n") || v.contains("\r") ||
                            startsOrEndsWithSpace(v); // pour préserver les espaces en bord

            if (mustQuote) {
                sb.append('"');
                // Remplacer chaque " par "" (échappement CSV standard)
                sb.append(v.replace("\"", "\"\""));
                sb.append('"');
            } else {
                sb.append(v);
            }

            if (i < row.length - 1) sb.append(',');
        }
        return sb.toString();
    }

    /** Indique si la chaîne commence ou finit par un espace (cas à citer pour préserver ces espaces). */
    private static boolean startsOrEndsWithSpace(String s) {
        return !s.isEmpty() && (s.charAt(0) == ' ' || s.charAt(s.length() - 1) == ' ');
        // Exemple : "  MALE " -> on le cite pour éviter que les espaces disparaissent à la relecture
    }
}