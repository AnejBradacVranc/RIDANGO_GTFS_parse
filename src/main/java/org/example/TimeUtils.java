package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils{

    public Boolean isTimeInRange(int arrivalTime, Integer range) {

        LocalTime currentTime = LocalTime.now();
        LocalTime maxTime = currentTime.plusHours(range);

        boolean overMidnight = maxTime.toSecondOfDay() < currentTime.toSecondOfDay();

        if (overMidnight) {
            return !(arrivalTime > currentTime.toSecondOfDay() || arrivalTime < maxTime.toSecondOfDay());
        } else {
            return arrivalTime <= currentTime.toSecondOfDay() || arrivalTime >= maxTime.toSecondOfDay();
        }
    }
}
