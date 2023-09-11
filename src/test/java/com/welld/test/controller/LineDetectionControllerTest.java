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
        //aggiungo un punto
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 2, \"y\": 2}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        //testo la getallpoints
        mockMvc.perform(MockMvcRequestBuilders.get("/space")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }




    @Test
    public void testGetPoints_NoPoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/space")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
