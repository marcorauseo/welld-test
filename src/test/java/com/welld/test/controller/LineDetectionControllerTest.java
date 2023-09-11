package com.welld.test.controller;

import com.welld.test.command.AddPointCommand;
import com.welld.test.model.Point;
import com.welld.test.util.CustomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class LineDetectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private LineDetectionController controller;

    @Mock
    private AddPointCommand addPointCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPoint_Success() throws Exception {
        // Crea una richiesta POST con un punto nel corpo
        String requestBody = "{\"x\": 1, \"y\": 2}";
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddPoint_AlreadyExists() throws Exception {
        // Aggiungi un punto alla lista (simula un punto già presente)
        // Assicurati che il punto aggiunto corrisponda a quello nella richiesta
        // Invia una richiesta POST con lo stesso punto nel corpo
        String requestBody = "{\"x\": 1, \"y\": 2}";
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetPoints_WithPoints() throws Exception {

        // Crea un punto fittizio
        Point point = new Point(2, 2);

        // Crea una lista di punti fittizia contenente il punto
        List<Point> mockPoints = new ArrayList<>();
        mockPoints.add(point);

        // Configura il mock per restituire la lista mockPoints quando getAllPoints() viene chiamato
        when(addPointCommand.getAllPoints()).thenReturn(mockPoints);


        // Chiamata al controller
        ResponseEntity<?> response = controller.getPoints();

        // Verifica che il metodo getAllPoints() sia stato chiamato
       verify(addPointCommand, times(1)).getAllPoints();

        // Verifica che la risposta abbia lo stato OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica che la risposta contenga la lista di punti attesa
        assertEquals(mockPoints, response.getBody());
    }




    @Test
    public void testGetPoints_NoPoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/space")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
