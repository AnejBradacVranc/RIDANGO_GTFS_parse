package org.example;

import org.onebusaway.csv_entities.exceptions.CsvEntityIOException;
import org.onebusaway.csv_entities.exceptions.MissingRequiredFieldException;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        GtfsReader reader = new GtfsReader();
        reader.setInputLocation(new File("src/gtfs/"));

        GtfsDaoImpl store = new GtfsDaoImpl();
        reader.setEntityStore(store);

        try {
            reader.run();



            /*for (Route route : store.getAllRoutes()) {
                System.out.println("route: " + route.getShortName());
            }*/

            /*Map<Route, Trip> tripsById = store.getEntitiesByIdForEntityType(Route.class, Trip.class);


            for (Route route : store.getAllRoutes()) {
                System.out.println("route: " + tripsById.get(route)); // Pa to tut ???
            }

            for(Trip trip: tripsById.values()){
               System.out.println(trip.getRoute().getShortName()); //To tut gre????
           }*/


            /*for (StopTime st : store.getAllStopTimes()){
                //System.out.println(st.getTrip().getRoute().getShortName());
                System.out.println("Linija " + st.getTrip().getRoute().getShortName());
                System.out.println("Postajalisce " + st.getStop().getName() + st.getStop().getId());
            }*/

            /*store.getStopForId()

           /* for(StopTime stopTime: store.getAllStopTimes()){
                //System.out.println("route: " + stopTime.getId());

            }*/


            //System.out.println(stopTimeById.get(selectedStop).getTrip().getRoute().getShortName());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime currentTime = LocalTime.now();
            LocalTime maxTime = currentTime.plusHours(2);


            Collection<StopTime> stopTimes = store.getAllStopTimes();


            stopTimes.removeIf(p -> !Objects.equals(p.getStop().getId().getId(), "3"));

            stopTimes.removeIf(p -> !LocalTime.ofSecondOfDay(p.getArrivalTime()).isAfter(currentTime) ||  !LocalTime.ofSecondOfDay(p.getArrivalTime()).isBefore(maxTime));


            for (StopTime stopTime : stopTimes) {

                Stop stop = stopTime.getStop();
                Trip trip = stopTime.getTrip();
                Route route = trip.getRoute();
                LocalTime timeOfArrival = LocalTime.ofSecondOfDay(stopTime.getArrivalTime());

                System.out.println("Postajalisce " + stop.getName() + " id:" + stop.getId().getId());
                System.out.println("Linija " + route.getShortName());
                System.out.println("Cas prihoda " + timeOfArrival.format(formatter) + "\n");

            }


        } catch (MissingRequiredFieldException | CsvEntityIOException e) {
            System.out.println(e.getMessage());
        }


    }


}