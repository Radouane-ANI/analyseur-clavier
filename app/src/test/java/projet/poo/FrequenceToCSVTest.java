package projet.poo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projet.poo.analyseurfrequence.FrequenceToCSV;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrequenceToCSVTest {

    private FrequenceToCSV frequenceToCSV;
    private Map<String, Integer> ungramme;
    private Map<String, Integer> bigramme;
    private Map<String, Integer> trigramme;

    @BeforeEach
    public void setUp() {
        ungramme = new HashMap<>();
        bigramme = new HashMap<>();
        trigramme = new HashMap<>();

        ungramme.put("\\", 3);
        bigramme.put("\n,", 2);
        trigramme.put("abc", 1);

        frequenceToCSV = new FrequenceToCSV(ungramme, bigramme, trigramme, 6);
    }

    @Test
    public void testConstructorWithValidArguments() {
        assertNotNull(frequenceToCSV);
    }

    @Test
    public void testConstructorWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FrequenceToCSV(null, bigramme, trigramme, 6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new FrequenceToCSV(ungramme, null, trigramme, 6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new FrequenceToCSV(ungramme, bigramme, null, 6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new FrequenceToCSV(ungramme, bigramme, trigramme, -1);
        });
    }

    @Test
    public void testEcrisDansCSV() {
        try {
            frequenceToCSV.ecrisDansCSV();
            File csvFile = new File("frequence.csv");
            assertTrue(csvFile.exists());

            try (FileReader reader = new FileReader(csvFile)) {
                char[] buffer = new char[100];
                int length = reader.read(buffer);
                String content = new String(buffer, 0, length);
                assertTrue(content.contains("Taille,6"));
                assertTrue(content.contains("Ungramme,\"\\\",3"));
                assertTrue(content.contains("Bigramme,\"\n,\",2"));
                assertTrue(content.contains("Trigramme,\"abc\",1"));
            }

            csvFile.delete();
        } catch (IOException e) {
            fail("IOException should not have been thrown.");
        }
    }
}