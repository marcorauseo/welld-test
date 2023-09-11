package com.welld.test.command;

import com.welld.test.model.Point;
import com.welld.test.service.DetectLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetectLinesCommand {

    private final DetectLinesService detectLinesService;

    @Autowired
    public DetectLinesCommand(DetectLinesService detectLinesService) {
        this.detectLinesService = detectLinesService;
    }

    public List<List<Point>> detectLines(List<Point> points, int n) {
        return detectLinesService.detectLines(points, n);
    }
}

