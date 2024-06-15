package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    public Boolean isTimeOutOfRange(LocalTime startTime, int arrivalTime, Integer range) {

        LocalTime maxTime = startTime.plusHours(range);

        boolean overMidnight = maxTime.toSecondOfDay() < startTime.toSecondOfDay();

        //removes elements from coll. if this method returns true
        if (overMidnight) {
            return arrivalTime <= startTime.toSecondOfDay() && arrivalTime >= maxTime.toSecondOfDay();
        } else {
            return arrivalTime <= startTime.toSecondOfDay() || arrivalTime >= maxTime.toSecondOfDay();
        }
    }

    public int getRelativeTime(Integer timeDuration) {
        return (Math.abs(timeDuration - LocalTime.now().toSecondOfDay()) / 60);
    }

    public String getAbsoluteTime(Integer timeDuration) {

        return LocalTime.ofSecondOfDay(timeDuration).format(formatter);
    }
}
