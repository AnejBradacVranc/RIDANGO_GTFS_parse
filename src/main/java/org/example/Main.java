package org.example;

import org.onebusaway.csv_entities.exceptions.CsvEntityIOException;
import org.onebusaway.csv_entities.exceptions.MissingRequiredFieldException;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    static final int MAX_TIME_ELAPSED = 2;

    public static void main(String[] args) throws IOException {

        if (args.length != 3 || (!Objects.equals(args[2], "relative") && !Objects.equals(args[2], "absolute"))) {

            System.out.println("Manjakjoči argumenti. Uporaba: <id postaje> <st. naslednjih avtobusov> <relative|absolute>");
            return;

        }
        try {
            String stopId = args[0];

            GtfsReader reader = new GtfsReader();
            reader.setInputLocation(new File("src/gtfs/"));

            GtfsDaoImpl store = new GtfsDaoImpl();
            reader.setEntityStore(store);

            reader.run();

            Collection<StopTime> stopTimes = store.getAllStopTimes();

            if (!stopTimes.isEmpty()) {
                String timeFormat = args[2];
                int printLimit = Integer.parseInt(args[1]);

                TimeUtils timeUtils = new TimeUtils();
                PrintUtils printUtils = new PrintUtils();


                stopTimes.removeIf(p -> !Objects.equals(p.getStop().getId().getId(), stopId));

                if(!stopTimes.isEmpty()){
                    System.out.println(stopTimes.iterator().next().getStop().getName() + " " + stopId);

                    stopTimes.removeIf(p -> timeUtils.isTimeOutOfRange(LocalTime.now(), p.getArrivalTime(), MAX_TIME_ELAPSED));

                    //https://stackoverflow.com/questions/52984816/group-by-a-collection-attribute-using-java-streams
                    Map<String, List<Integer>> groupedResults = stopTimes.stream().map(stopTime -> new AbstractMap.SimpleEntry<>(stopTime.getTrip().getRoute().getShortName(), stopTime.getArrivalTime()))
                            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

                    printUtils.printGroupedResults(groupedResults, printLimit, timeFormat);
                }

            }
        } catch (MissingRequiredFieldException | CsvEntityIOException e) {
            System.out.println(e.getMessage());
        }
    }


}