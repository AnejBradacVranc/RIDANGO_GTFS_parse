package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PrintUtils{

    TimeUtils timeUtils = new TimeUtils();

    public void printGroupedResults(Map<String, List<Integer>> groupedResults, int limit, String timeFormat){
        if(groupedResults.entrySet().isEmpty()){
            System.out.print("Ni vnosov");
            return;
        }
        for (Map.Entry<String, List<Integer>> entry : groupedResults.entrySet()) {

            System.out.print(entry.getKey() + ": ");

            Collections.sort(entry.getValue());
            ListIterator<Integer> it = entry.getValue().listIterator();

            while (it.hasNext()) {
                if (it.nextIndex() < limit) {
                    if (Objects.equals(timeFormat, "relative")) {
                        System.out.print(timeUtils.getRelativeTime(it.next()) + " min ");

                    } else if (Objects.equals(timeFormat, "absolute")) {
                        System.out.print(timeUtils.getAbsoluteTime(it.next()) + " ");

                    }
                } else {
                    break;
                }
            }

            System.out.print("\n");

        }
    }
}
