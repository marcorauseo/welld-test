package com.welld.test.command;

import com.welld.test.model.Point;
import com.welld.test.service.AddPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class AddPointCommandTest {


        @InjectMocks
        private AddPointCommand addPointCommand;

        @Mock
        private AddPointService addPointService;

        private List<Point> allPoints;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        allPoints = new ArrayList<>();
        addPointCommand.setAllPoints(allPoints); // Inizializza la lista nel comando
    }





    @Test
         void testAddPoint_Success() {
            Point point = new Point(1, 2);
            allPoints.add(point);

            when(addPointService.pointExists(allPoints, point)).thenReturn(false);

            ResponseEntity<?> response = addPointCommand.addPoint(point);

            verify(addPointService, times(1)).pointExists(allPoints, point);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(allPoints, response.getBody());
        }

        @Test
         void testAddPoint_AlreadyExists() {
            Point point = new Point(1, 2);

            when(addPointService.pointExists(allPoints, point)).thenReturn(true);

            ResponseEntity<?> response = addPointCommand.addPoint(point);

            verify(addPointService, times(1)).pointExists(allPoints, point);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }


