package com.welld.test.service;


import com.welld.test.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Autore: Marco Rauseo 08/09/2023
 */

/**
Uso un service per implementare la logica:
Dato un set di N punti nel piano, determinare ogni segmento che contiene N o più punti e
restituire tutti i punti coinvolti.
* */

@Service
public class LineDetectionService {

        public List<List<Point>> detectLines(List<Point> points, int n) {
            List<List<Point>> lines = new ArrayList<>();
            Set<Set<Point>> lineSets = new HashSet<>();

            // Uso un ciclo for per iterare su ogni coppia di punti
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point point1 = points.get(i);
                    Point point2 = points.get(j);

                   // Calcolo l'equazione della retta che passa attraverso i due punti
                    // Ovvero y = mx + b
                    // Dove x e y sono le cordinate di un punto sulla retta
                    // m è lo "slope" o inclinazione della retta ---> m= (y2-y1) / (x2-x1)
                    // b è l'"intercept" ovvero dove la retta interseca l'asse y --> b = y1-m*1

                    double slope = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
                    double intercept = point1.getY() - slope * point1.getX();

                    // Verifico quanti altri punti giacciono sulla retta :
                    List<Point> linePoints = new ArrayList<>();
                    linePoints.add(point1);
                    linePoints.add(point2);

                    for (Point point : points) {
                        if (point != point1 && point != point2) {

                            //Se il valore assouluto di y-mx+q è circa uguale a 0 il punto appartiene alla stessa retta del punto iniziale
                            if (Math.abs(point.getY() - (slope * point.getX() + intercept)) < 0.001) {
                                linePoints.add(point);
                            }
                        }
                    }

                    // Se la retta contiene n o più punti la aggiungo al risultato
                    if (linePoints.size() >= n) {
                        // Convert the line points to a set for comparison
                        Set<Point> lineSet = new HashSet<>(linePoints);

                        // Check if this line set has not been added before
                        if (!lineSets.contains(lineSet)) {
                            lines.add(linePoints);
                            lineSets.add(lineSet);
                        }
                    }
                }
            }

            return lines;
        }
}







