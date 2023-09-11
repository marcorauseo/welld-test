package com.welld.test.service;

import com.welld.test.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddPointServiceTest {

    private AddPointService addPointService;
    private List<Point> allPoints;

    @BeforeEach
    public void setUp() {
        addPointService = new AddPointService();
        allPoints = new ArrayList<>();
    }

    @Test
    public void testPointExists_WhenPointDoesNotExist() {
        Point newPoint = new Point(2, 2);
        boolean exists = addPointService.pointExists(allPoints, newPoint);
        assertFalse(exists, " punto non esiste");
    }

    @Test
    public void testPointExists_WhenPointExists() {
        Point existingPoint = new Point(2, 2);
        allPoints.add(existingPoint);

        Point newPoint = new Point(2, 2);
        boolean exists = addPointService.pointExists(allPoints, newPoint);
        assertTrue(exists, "punto esiste già");
    }

    @Test
    public void testPointExists_WhenDifferentPointExists() {
        Point existingPoint = new Point(2, 2);
        allPoints.add(existingPoint);

        Point newPoint = new Point(3, 3);
        boolean exists = addPointService.pointExists(allPoints, newPoint);
        assertFalse(exists, "C'è già un punto, ma questo ha cordinate diverse");
    }
}
