import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class SauvegardeFichierCSV {
    public static void sauvegarderCSV(String[][] data, String chemin) {
        if (data == null) return; // rien à écrire

        // Pas de Path ici : on part directement de la String 'chemin'
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(chemin), StandardCharsets.UTF_8))) {

            for (String[] row : data) {
                bw.write(joinCSV(row));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur écriture CSV: " + e.getMessage(), e);
        }
    }

    // Assemble une ligne CSV : met entre guillemets si nécessaire et double les guillemets internes
    private static String joinCSV(String[] row) {
        if (row == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            String v = (row[i] == null) ? "" : row[i];
            boolean mustQuote = v.contains(",") || v.contains("\"") || v.contains("\n") || v.contains("\r");
            if (mustQuote) {
                v = "\"" + v.replace("\"", "\"\"") + "\"";
            }
            sb.append(v);
            if (i < row.length - 1) sb.append(',');
        }
        return sb.toString();
    }
}
