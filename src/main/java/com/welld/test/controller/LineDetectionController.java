package com.welld.test.controller;

import com.welld.test.command.LineDetectionCommand;
import com.welld.test.model.Point;
import com.welld.test.service.LineDetectionService;
import com.welld.test.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Marco Rauseo 08/09/2023
 */

@RestController
public class LineDetectionController {


    @Autowired
    private LineDetectionService lineDetectionService;

    private final List<Point> allPoints = new ArrayList<>();

    private final LineDetectionCommand lineDetectionCommand;

    @Autowired
    public LineDetectionController(LineDetectionCommand lineDetectionCommand) {
        this.lineDetectionCommand = lineDetectionCommand;
    }


    /**
     *Aggiunge un punto nello spazio
     */
    @PostMapping("/point")
    public ResponseEntity<?> addPoint (@RequestBody Point point){
        for (Point existingPoint : allPoints) {
            if (existingPoint.getX() == point.getX() && existingPoint.getY() == point.getY()) {
                String errorMessage = "Il punto è già presente nello spazio";
                CustomResponse errorResponse = new CustomResponse(errorMessage);
                return ResponseEntity.badRequest().body(errorResponse);
            }
        }
        allPoints.add(point);
        return new ResponseEntity<>(allPoints, HttpStatus.OK);
    }

    /**
    *Prende tutti i punti nello spazio
     */
    @GetMapping("/space")
    public ResponseEntity<List<Point>> getPoints (){
        return new ResponseEntity<>(allPoints, HttpStatus.OK);
    }

    /**
     *Calcola tutte le rette che passano attraverso almeno N punti
     */
    @GetMapping("/lines/{n}")
    public ResponseEntity<?> detectLines(@PathVariable int n) {
        if (n < 2) {
            String errorMessage = "Il valore di 'n' non è valido. Deve essere > 1";
            CustomResponse errorResponse = new CustomResponse(errorMessage);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        List<List<Point>> lines = lineDetectionCommand.detectLines(allPoints, n);
        if(lines.isEmpty()){
            String errorMessage = "Non è stata trovata nessuna retta, prova ad aggiungere altri punti o a diminuire il valore di n";
            CustomResponse errorResponse = new CustomResponse(errorMessage);
            return ResponseEntity.badRequest().body(errorResponse);

        }
        return ResponseEntity.ok(lines);
    }

    /**
     *Cancella tutti i punti dello spazio
     */
    @DeleteMapping("/space")
    public ResponseEntity<String> deleteAllPoints(){
        allPoints.clear();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tutti i punti sono stati cancellati");
    }







}
