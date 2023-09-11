package com.welld.test.service;

import com.welld.test.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
 class DetectLinesServiceTest {

    @Autowired
    private DetectLinesService detectLinesService;

    private List<Point> testPoints;

    @BeforeEach
     void setUp() {
        // Inizializza una lista di punti di prova per il test
        testPoints = new ArrayList<>();
        testPoints.add(new Point(1.0, 2.0));
        testPoints.add(new Point(2.0, 3.0));
        testPoints.add(new Point(3.0, 4.0));
        testPoints.add(new Point(4.0, 5.0));
        testPoints.add(new Point(5.0, 6.0));
    }

    @Test
     void testDetectLines() {
        // Definisci il numero minimo di punti per una linea
        int N = 3;

        // Esegui la rilevazione delle linee sui punti di prova
        List<List<Point>> lines = detectLinesService.detectLines(testPoints, N);

        // Verifica i risultati del test (mi aspetto che per 3 punti di questo set passi una sola retta)
        assertEquals(1, lines.size()); // Verifica il numero di linee rilevate

        // Verifica le coordinate delle linee rilevate (in caso di un set di punti che genera più linee)

        for (List<Point> firstLine : lines) {
            assertTrue(firstLine.size() > N);
        }


    }
}
