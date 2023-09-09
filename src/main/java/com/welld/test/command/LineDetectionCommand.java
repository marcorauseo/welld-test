package com.welld.test.command;

import com.welld.test.model.Point;
import com.welld.test.service.LineDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LineDetectionCommand {

    private final LineDetectionService lineDetectionService;

    @Autowired
    public LineDetectionCommand(LineDetectionService lineDetectionService) {
        this.lineDetectionService = lineDetectionService;
    }

    public List<List<Point>> detectLines(List<Point> points, int n) {
        return lineDetectionService.detectLines(points, n);
    }
}

