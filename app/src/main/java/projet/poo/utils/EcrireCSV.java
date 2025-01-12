package projet.poo.utils;

import java.io.IOException;

/**
 * Interface pour écrire des données dans un fichier CSV.
 */
public interface EcrireCSV {

    /**
     * Écrit les données dans un fichier CSV.
     * 
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    void ecrisDansCSV() throws IOException;

}
