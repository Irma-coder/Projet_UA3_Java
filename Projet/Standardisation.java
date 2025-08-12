import java.math.BigDecimal;
public class Standardisation {

    /** Seuil pour considérer qu'une colonne est numérique (≥ 50% de valeurs parseables). */
    private static final double NUMERIC_COL_THRESHOLD = 0.5;

       public static String[][] standardiser(String[][] data) {
        // Sécurité : si rien à traiter, on renvoie tel quel
        if (data == null || data.length == 0) return data;

        int rows = data.length;        // nombre de lignes (entête incluse)
        int cols = data[0].length;     // nombre de colonnes

        // 1) DÉTECTION DES COLONNES NUMÉRIQUES
        // -------------------------------------
        // Idée : pour chaque colonne, on compte combien de cellules (lignes 1..n)
        // sont "convertibles" en nombre. Si ≥ 50%, on tague la colonne comme numérique.
        boolean[] isNumericCol = new boolean[cols];
        for (int j = 0; j < cols; j++) {
            int numericHits = 0;
            int total = 0;

            // On saute l'entête (i = 0) : on ne l'utilise pas pour la détection
            for (int i = 1; i < rows; i++) {
                // On récupère la cellule, on la normalise "décimales FR -> point"
                String val = safe(data[i][j]);
                String norm = normalizeDecimalComma(val);

                // Si ça ressemble à un nombre pour Java, on compte un "hit"
                if (isNumeric(norm)) numericHits++;
                total++;
            }

            // Ratio de cellules numériques sur la colonne
            double ratio = (total > 0) ? (numericHits * 1.0 / total) : 0.0;
            isNumericCol[j] = ratio >= NUMERIC_COL_THRESHOLD;
        }

        // 2) STANDARDISATION DES VALEURS
        String[][] out = new String[rows][cols];

        // Ne pas toucher à l'entête : on copie tel quel
        System.arraycopy(data[0], 0, out[0], 0, cols);

        // Pour chaque cellule de données (i >= 1)
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String val = safe(data[i][j]); // jamais null

                // Cas des "valeurs manquantes" les plus courantes
                if (val.equalsIgnoreCase("NA") || val.equalsIgnoreCase("null") || val.equals("")) {
                    out[i][j] = "NA";  // convention : on garde "NA"
                    continue;
                }

                // Colonne numérique détectée ?
                if (isNumericCol[j]) {
                    // On convertit d'abord "1 234,56" -> "1234.56"
                    String norm = normalizeDecimalComma(val);

                    if (isNumeric(norm)) {
                        // Forme canonique : pas de ".00" forcé, pas de notation scientifique
                        out[i][j] = toCanonicalNumber(norm);   // ex: "518.625"
                    } else {
                        // Valeur non numérique dans une colonne num -> on ne casse pas la donnée :
                        // on met un format texte propre (trim)
                        out[i][j] = val.trim();
                    }
                } else {
                    // Colonne texte : on standardise simplement (minuscules + trim).
                    // NB : on pourrait paramétrer (upper/lower/none) si le prof le demande.
                    out[i][j] = val.trim().toLowerCase();
                }
            }
        }

        return out;
    }

    // ---------------------------------------------------------------------
    //                      MÉTHODES UTILITAIRES
    // ---------------------------------------------------------------------

    /**
     * @return la chaîne s, mais garantie non nulle ("" si null).
     */
    private static String safe(String s) {
        return (s == null) ? "" : s;
    }

    /**
     * Convertit les nombres "à la française" en format "point décimal".
     * Exemples :
     *   "1 234,56"  -> "1234.56"
     *   "518,625"   -> "518.625"
     *   "12"        -> "12" (inchangé)
     *
     * Règle :
     *  - Si le motif ressemble à un nombre FR (groupes de milliers + virgule décimale),
     *    on supprime les séparateurs d'espaces/points entre milliers et on remplace la virgule par un point.
     *  - Sinon, on renvoie tel quel.
     */
    private static String normalizeDecimalComma(String s) {
        String t = safe(s).trim();

        // Motifs possibles :
        //  - ^-?[0-9]{1,3}([ .][0-9]{3})*(,[0-9]+)?$   -> gère "1 234,56" ou "1.234,56"
        //  - ^-?[0-9]+,[0-9]+$                        -> gère "518,625"
        boolean looksLikeFrenchNumber =
                t.matches("^-?[0-9]{1,3}([ .][0-9]{3})*(,[0-9]+)?$") ||
                        t.matches("^-?[0-9]+,[0-9]+$");

        if (looksLikeFrenchNumber) {
            // On supprime les séparateurs de milliers, puis on remplace la virgule par un point
            t = t.replace(" ", "").replace(".", "").replace(",", ".");
            return t;
        }
        return t; // pas un nombre FR : on renvoie tel quel
    }

    /**
     * Indique si la chaîne peut être interprétée comme un nombre valide.
     * On utilise BigDecimal pour être tolérant et éviter les surprises de double.
     */
    private static boolean isNumeric(String s) {
        try {
            new BigDecimal(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Convertit un nombre en chaîne "propre" :
     *  - supprime les zéros inutiles ("12.3400" -> "12.34")
     *  - évite la notation scientifique ("1E+3" -> "1000")
     */
    private static String toCanonicalNumber(String s) {
        try {
            BigDecimal bd = new BigDecimal(s);
            bd = bd.stripTrailingZeros();
            return bd.toPlainString();
        } catch (Exception e) {
            // Si ça plante, on renvoie la valeur telle quelle (principe de robustesse).
            return s;
        }
    }
}