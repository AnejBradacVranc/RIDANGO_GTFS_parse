package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

public class PrintUtils{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public void printGroupedResults(Map<String, List<LocalTime>> groupedResults, int limit, String timeFormat){
        if(groupedResults.entrySet().isEmpty()){
            System.out.print("Ni vnosov");
            return;
        }
        for (Map.Entry<String, List<LocalTime>> entry : groupedResults.entrySet()) {

            System.out.print(entry.getKey() + ": ");

            ListIterator<LocalTime> it = entry.getValue().listIterator();

            while (it.hasNext()) {
                if (it.nextIndex() < limit) {
                    if (Objects.equals(timeFormat, "relative")) {
                        Integer res = (Math.abs(it.next().toSecondOfDay() - LocalTime.now().toSecondOfDay()) / 60);
                        System.out.print(res + " min ");
                    } else if (Objects.equals(timeFormat, "absolute")) {
                        System.out.print(it.next().format(formatter) + " ");

                    }
                } else {
                    break;
                }
            }

            System.out.print("\n");

        }
    }
}
