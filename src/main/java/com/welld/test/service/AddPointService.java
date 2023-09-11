package com.welld.test.service;

import com.welld.test.model.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddPointService {
    public boolean pointExists(List<Point> allPoints, Point newPoint) {
        for (Point existingPoint : allPoints) {
            if (existingPoint.getX() == newPoint.getX() && existingPoint.getY() == newPoint.getY()) {
                return true; // Il punto esiste già
            }
        }
        return false; // Il punto non esiste nella lista
    }
}

