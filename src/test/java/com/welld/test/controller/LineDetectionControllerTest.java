package com.welld.test.controller;

;
import com.welld.test.command.DetectLinesCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
public class LineDetectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private LineDetectionController controller;


    @Mock
    private DetectLinesCommand detectLinesCommand;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddPoint_Success() throws Exception {
        // Crea una richiesta POST con un punto nel corpo
        String requestBody = "{\"x\": 1, \"y\": 2}";
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
     void testAddPoint_AlreadyExists() throws Exception {
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
     void testGetPoints_WithPoints() throws Exception {
        //aggiungo un punto
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 2, \"y\": 2}")
                );
        //testo la getallpoints
        mockMvc.perform(MockMvcRequestBuilders.get("/space")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
     void testGetPoints_NoPoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/space")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
     void testDeleteAllPoints() throws Exception{
        //aggiungo un punto
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 2, \"y\": 2}")
                );
        //tolgo il punto
        mockMvc.perform(MockMvcRequestBuilders.delete("/space")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }


    @Test
     void testDetectLines_WithValidNAndNoLines() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lines/2")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
     void testDetectLines_WithInValidN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lines/0")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
     void testDetectLines_WithValidNAndLines() throws Exception {
        //aggiungo un punto
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 2, \"y\": 2}")
                );
        //aggiungo un punto
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 0, \"y\": 0}")
                );


        mockMvc.perform(MockMvcRequestBuilders.get("/lines/2")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
     void testDetectLines_WithValidNAndOnlyOnePoint() throws Exception {
        //aggiungo un punto
        mockMvc.perform(MockMvcRequestBuilders.post("/point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"x\": 2, \"y\": 2}")
                )
                ;


        mockMvc.perform(MockMvcRequestBuilders.get("/lines/2")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }







}
