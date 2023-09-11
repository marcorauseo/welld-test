package com.welld.test.controller;

import com.welld.test.command.AddPointCommand;
import com.welld.test.command.DetectLinesCommand;
import com.welld.test.model.Point;
import com.welld.test.service.DetectLinesService;
import com.welld.test.service.DrawLinesService;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Marco Rauseo 08/09/2023
 */

@RestController
public class LineDetectionController extends JPanel {


    @Autowired
    private DetectLinesService detectLinesService;

    @Autowired
    private DrawLinesService drawLinesService;

    private final List<Point> allPoints = new ArrayList<>();

    private final DetectLinesCommand detectLinesCommand;
    private final AddPointCommand addPointCommand ;

    @Autowired
    public LineDetectionController(DetectLinesCommand detectLinesCommand,AddPointCommand addPointCommand) {
        this.detectLinesCommand = detectLinesCommand;
        this.addPointCommand = addPointCommand;
    }


    /**
     *Aggiunge un punto nello spazio
     */
    @PostMapping("/point")
    public ResponseEntity<?> addPoint (@RequestBody Point point){
        return addPointCommand.addPoint(point);
        }


    /**
    *Prende tutti i punti nello spazio
     */
    @GetMapping("/space")
    public ResponseEntity<?> getPoints (){
        List<Point> points = addPointCommand.getAllPoints();
        if(points.isEmpty()) {
            String errorMessage = "Non ci sono punti nello spazio, prova ad aggiungere altri punti ";
            CustomResponse errorResponse = new CustomResponse(errorMessage);
            return ResponseEntity.badRequest().body(errorResponse);

        }

        return new ResponseEntity<>(addPointCommand.getAllPoints(), HttpStatus.OK);
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

        List<List<Point>> lines = detectLinesCommand.detectLines(addPointCommand.getAllPoints(), n);
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
        addPointCommand.getAllPoints().clear();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tutti i punti sono stati cancellati");
    }

    //TODO da sistemare
    @GetMapping("/draw")
    public ResponseEntity<byte[]> generateLineImage() {
        int width = 400;
        int height = 400;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        List<Point> points = addPointCommand.getAllPoints();
        g2d.setColor(Color.white); // Imposta il colore di sfondo
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.blue); // Imposta il colore della linea



        for (int i = 0; i < points.size() - 1; i++) {
            Point point1 = points.get(i);
            Point point2 = points.get(i + 1);


            double x1 = point1.getX();
            double y1 = point1.getY();
            double x2 = point2.getX();
            double y2 = point2.getY();

            //fattore di scala per migliorare la resa grafica
            int s = 100;
            Line2D line = new Line2D.Double(s*x1, s*y1, s*x2, s*y2);
            g2d.draw(line); // Disegna la retta
            // Disegna l'asse X
            g2d.setColor(Color.black);
            g2d.drawLine(0, 0, width, 0);
            g2d.drawString("X", width - 10, -10);


            // Disegna l'asse Y
            g2d.drawLine(0, 0, 0, height);
            g2d.drawString("Y", 10, -height + 20);
        }

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] imageBytes = baos.toByteArray();
        return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_PNG).body(imageBytes);
    }








}
