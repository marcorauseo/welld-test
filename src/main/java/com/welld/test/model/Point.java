package com.welld.test.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Marco Rauseo 08/09/2023
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Point {


    private double x;
    private double y;
}
