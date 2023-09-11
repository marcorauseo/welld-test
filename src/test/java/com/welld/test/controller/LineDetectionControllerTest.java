package com.welld.test.controller;

import com.welld.test.command.BaseCommand;
import com.welld.test.command.DetectLinesCommand;
import com.welld.test.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class LineDetectionControllerTest {

    private LineDetectionController controller;
    private DetectLinesCommand command;
    private List<Point> allPoints;

    @BeforeEach
    public void setUp() {
        allPoints = new ArrayList<>();
        command = mock(DetectLinesCommand.class);


    }

    @Test
    public void testAddPoint_Success() {
        Point newPoint = new Point(5.0, 10.0);

        ResponseEntity<?> responseEntity = controller.addPoint(newPoint);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testAddPoint_Duplicate() {
            Point existingPoint = new Point(5.0, 10.0);
            allPoints.add(existingPoint);
            Point newPoint = new Point(5.0, 10.0);

            ResponseEntity<?> responseEntity = controller.addPoint(newPoint);

            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
            assertFalse(allPoints.contains(newPoint));
        }

}