
public class ValeursManquantes {
    public static String[][] remplacerNaN(String[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == null || data[i][j].trim().isEmpty()) {
                    data[i][j] = "NA";
                }
            }
        }
        return data;
    }
}
