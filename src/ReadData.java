import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {

    public static ArrayList<Vare> readData(String path) throws IOException {
        ArrayList<Vare> varer = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";");
                String id = parts[0].trim();
                String name = parts[1].trim();
                String priceDate = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                boolean isBudgetBrand = Boolean.parseBoolean(parts[4].trim());
                double quantity = Double.parseDouble(parts[5].trim());
                String unit = parts[6].trim();
                boolean isWeighted = Boolean.parseBoolean(parts[7].trim());
                boolean isBio = Boolean.parseBoolean(parts[8].trim());
                boolean isAvailable = Boolean.parseBoolean(parts[9].trim());

                Vare vare = new Vare(id, name, priceDate, price, isBudgetBrand, quantity, unit, isWeighted, isBio, isAvailable);
                varer.add(vare);
            }
        }
        return varer;
    }
}
