import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public class LireFichierCSV {

    /** Lit un fichier CSV UTF-8 et le retourne sous forme de tableau 2D. */
    public static String[][] lireCSV(String chemin) {
        List<String[]> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(chemin), StandardCharsets.UTF_8))) {

            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) {
                    line = removeBOM(line);
                    first = false;
                }
                String[] cols = parseCSVLine(line);
                // On ne nettoie pas agressivement ici : on trim simplement.
                for (int i = 0; i < cols.length; i++) {
                    cols[i] = cols[i] == null ? "" : cols[i].trim();
                }
                lignes.add(cols);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }

        // Normaliser la largeur (certains fichiers ont des lignes plus courtes)
        int maxCols = 0;
        for (String[] r : lignes) maxCols = Math.max(maxCols, r.length);
        String[][] data = new String[lignes.size()][maxCols];
        for (int i = 0; i < lignes.size(); i++) {
            String[] r = lignes.get(i);
            for (int j = 0; j < maxCols; j++) {
                data[i][j] = j < r.length ? r[j] : "";
            }
        }
        return data;
    }

    /** Parse une ligne CSV en respectant les guillemets et l'échappement "" -> " */
    private static String[] parseCSVLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    sb.append('\"');
                    i++; // saut du guillemet échappé
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        out.add(sb.toString());
        return out.toArray(new String[0]);
    }

    /** Supprime un éventuel BOM UTF-8 en début de chaîne. */
    private static String removeBOM(String s) {
        if (s == null || s.isEmpty()) return s;
        return (s.charAt(0) == '\uFEFF') ? s.substring(1) : s;
    }
}

