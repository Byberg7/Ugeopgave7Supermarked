
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Vare> varer = ReadData.readData("data/varer.csv");
        ArrayList<Vare> tilbud = ReadData.readData("data/tilbud.csv");

        Map<String, Vare> varerMap = new HashMap<>();
        Map<String, Vare> tilbudMap = new HashMap<>();

        for (Vare vare : varer) {
            varerMap.put(vare.getId(), vare);
        }

        for (Vare vare : tilbud) {
            tilbudMap.put(vare.getId(), vare);
        }

        Robot robot = new Robot();
        Collection<Vare> kurv = robot.fyldIKurv(varer);

        Map<String, Integer> antalPrVare = new HashMap<>();

        for (Vare vare : kurv) {
            String ean = vare.getId();

            int antal = antalPrVare.getOrDefault(ean, 0);
            antalPrVare.put(ean, antal + 1);
        }

        Set<String> forskelligeVarer = new HashSet<>(antalPrVare.keySet());

        System.out.println("Antal forskellige varer: " + forskelligeVarer.size());

        double samletPris = 0;
        double samletRabat = 0;

        System.out.println("========== KVITTERING ==========");

        for (String vareId : antalPrVare.keySet()) {

            Vare vare = varerMap.get(vareId);
            Vare tilbudsVare = tilbudMap.get(vareId);

            int antal = antalPrVare.get(vareId);

            if (vare == null) {
                System.out.println("Vare ikke fundet: " + vareId);
                continue;
            }

            double stykPris;
            double rabatPrStk = 0;

            if (tilbudsVare != null) {
                stykPris = tilbudsVare.getPrice();
                rabatPrStk = Math.max(0, vare.getPrice() - stykPris);
            } else {
                stykPris = vare.getPrice();
            }

            double vareSamletPris = stykPris * antal;
            double vareSamletRabat = rabatPrStk * antal;

            samletPris += vareSamletPris;
            samletRabat += vareSamletRabat;

            System.out.println();
            System.out.println(vare.getName());
            System.out.println("Antal: " + antal);
            System.out.printf("Stykpris: %.2f kr.%n", stykPris);
            System.out.printf("Rabat: %.2f kr.%n", vareSamletRabat);
            System.out.printf("Pris: %.2f kr.%n", vareSamletPris);
        }

        double moms = samletPris * 0.20;

        System.out.println();
        System.out.println("========== TOTAL ==========");
        System.out.printf("Samlet rabat: %.2f kr.%n", samletRabat);
        System.out.printf("Pris efter rabat: %.2f kr.%n", samletPris);
        System.out.printf("Heraf moms: %.2f kr.%n", moms);
        System.out.println("===========================");
    }
}
