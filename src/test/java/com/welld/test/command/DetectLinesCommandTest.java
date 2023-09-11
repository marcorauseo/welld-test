package com.welld.test.command;

import com.welld.test.model.Point;
import org.junit.jupiter.api.BeforeEach;
import com.welld.test.service.DetectLinesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DetectLinesCommandTest {

    @InjectMocks
    private DetectLinesCommand detectLinesCommand;

    @Mock
    private DetectLinesService detectLinesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDetectLines() {
        List<Point> inputPoints = new ArrayList<>();
        List<List<Point>> expectedLines = new ArrayList<>();
        // Popola inputPoints e expectedLines con dati di test

        // Configura il comportamento del mock DetectLinesService
        when(detectLinesService.detectLines(inputPoints, 3)).thenReturn(expectedLines);

        // Chiama il metodo detectLinesCommand.detectLines
        List<List<Point>> actualLines = detectLinesCommand.detectLines(inputPoints, 3);

        // Verifica che il metodo detectLinesService.detectLines sia stato chiamato con i parametri corretti
        verify(detectLinesService, times(1)).detectLines(inputPoints, 3);

        // Verifica che il risultato sia quello atteso
        assertEquals(expectedLines, actualLines);
    }
}
