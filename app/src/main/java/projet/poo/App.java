package projet.poo;

import projet.poo.analyseurfrequence.AnalyseurCorpus;
import projet.poo.evaluateurdisposition.EvaluateurDisposition;
import projet.poo.utils.Logicel;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Calculer les fréquences");
            System.out.println("2. Analyser une disposition clavier");
            System.out.println("3. Quitter");
            System.out.println("Choisissez une option: ");
            int choix = -1;
            try {
                choix = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Veuillez entrer un nombre valide.");
                scanner.nextLine(); // clear the invalid input
                continue;
            }
            scanner.nextLine();

            switch (choix) {
                case 1:
                    Logicel a = new AnalyseurCorpus();
                    a.exec();
                    break;
                case 2:
                    Logicel e = new EvaluateurDisposition();
                    e.exec();
                    break;
                case 3:
                    System.out.println("Au revoir!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }
}
