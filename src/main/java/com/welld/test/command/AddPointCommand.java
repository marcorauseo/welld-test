package com.welld.test.command;

import com.welld.test.model.Point;
import com.welld.test.service.AddPointService;
import com.welld.test.util.CustomResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class AddPointCommand {


    private  List<Point> allPoints;
    private final AddPointService addPointService;

    public AddPointCommand(List<Point> allPoints, AddPointService pointService) {
        this.allPoints = allPoints;
        this.addPointService = pointService;
    }

    public void setAllPoints(List<Point> allPoints) {
        this.allPoints = allPoints;
    }

    public ResponseEntity<?> addPoint(Point point) {
        // Verifica se il punto esiste già nella lista
        if (addPointService.pointExists(allPoints, point)) {
            String errorMessage = "Il punto è già presente nello spazio";
            CustomResponse errorResponse = new CustomResponse(errorMessage);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        allPoints.add(point);
        return new ResponseEntity<>(allPoints, HttpStatus.OK);
    }
}

