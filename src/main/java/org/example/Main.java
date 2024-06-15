package org.example;

import org.onebusaway.csv_entities.exceptions.CsvEntityIOException;
import org.onebusaway.csv_entities.exceptions.MissingRequiredFieldException;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException {
        try {
            String stopId = args[0];
            System.out.println("Hello world!");

            GtfsReader reader = new GtfsReader();
            reader.setInputLocation(new File("src/gtfs/"));

            GtfsDaoImpl store = new GtfsDaoImpl();
            reader.setEntityStore(store);


            reader.run();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime currentTime = LocalTime.now();
            LocalTime maxTime = currentTime.plusHours(2);


            Collection<StopTime> stopTimes = store.getAllStopTimes();


            stopTimes.removeIf(p -> !Objects.equals(p.getStop().getId().getId(), stopId));

            if (!stopTimes.isEmpty()) {

                int limit = Integer.parseInt(args[1]);

                System.out.println(stopTimes.iterator().next().getStop().getName());

                stopTimes.removeIf(p -> !LocalTime.ofSecondOfDay(p.getArrivalTime()).isAfter(currentTime) || !LocalTime.ofSecondOfDay(p.getArrivalTime()).isBefore(maxTime));


                Map<String, List<LocalTime>> groupedResults = stopTimes.stream()
                        .map(stopTime -> new AbstractMap.SimpleEntry<>(stopTime.getTrip().getRoute().getShortName(), LocalTime.ofSecondOfDay(stopTime.getArrivalTime())))
                        .collect(Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                        ));

                for (Map.Entry<String, List<LocalTime>> entry : groupedResults.entrySet()) {

                    System.out.println("\n" + entry.getKey() + ":");

                    ListIterator<LocalTime> it = entry.getValue().listIterator();

                    while (it.hasNext()) {
                        if (it.nextIndex() < limit) {
                            System.out.print(it.next().format(formatter) + " ");
                        } else {
                            break;
                        }

                    }
                }
            }
        } catch (MissingRequiredFieldException | CsvEntityIOException e) {
            System.out.println(e.getMessage());
        }
    }


}