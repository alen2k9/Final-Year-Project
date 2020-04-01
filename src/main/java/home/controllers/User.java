package home.controllers;

import com.google.gson.Gson;
import data.power.MainPower;
import data.power.Power;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to pass values for drop down box
 *
 * */

class User {

    // Choices List for drop down box
    List<String> choice1;
    Map<String, List<String>> map;

    // User details
    String firstName;
    String lastName;
    String userName;
    String password;

    // database information
    String datacenterId;
    String floorId;
    String rackId;
    String hostId;
    String restService;

    // constructor
    User(List<String> choice1, Map<String, List<String>> map) {
        this.choice1 = choice1;
        this.map = map;
    }

    private Map<String, Double> doMapping(MainPower mainPower) {
        Map<String, Double> powerMap = new TreeMap<>();
        for (Power power : mainPower.getPower()) {
            // Europe/Dublin
            long secondsSinceEpoch = Long.parseLong(power.getTimeStamp());
            Instant instant = Instant.ofEpochSecond(secondsSinceEpoch);
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Dublin"));
            String month = ldt.getMonth().toString() + " " + ldt.getYear();

            Double powerValue = Double.valueOf(power.getPower());
            if (powerMap.containsKey(month)) {
                powerMap.computeIfPresent(month, (key, val) -> val = val + powerValue);
            } else {
                powerMap.put(month, powerValue);
            }

        }
        return powerMap;
    }

    Map<String, Double> getPowerData() throws IOException {
        URL url = new URL(restService + "datacenters/"+ datacenterId+"/floors/"+ floorId+"/racks/"+ rackId+"/hosts/"+ hostId+"/power?starttime=0&endtime=1585698460");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output = br.readLine();

        Gson gson = new Gson();

        MainPower mainPower = gson.fromJson(output, MainPower.class);

        Map<String, Double> powerGraph = doMapping(mainPower);

        System.out.println("Output from Server .... \n");
        return powerGraph;
    }
}
