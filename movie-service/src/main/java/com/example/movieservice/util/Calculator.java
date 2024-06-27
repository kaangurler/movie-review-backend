package com.example.movieservice.util;

import com.example.movieservice.entity.Rating;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Calculator {
    public static Double getAverageRating(List<Rating> ratings) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        return Double.parseDouble(decimalFormat.format(ratings.stream()
                .collect(Collectors.averagingDouble(Rating::getValue))));
    }
}
