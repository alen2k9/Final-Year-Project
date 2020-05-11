package data.mysql;

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
import java.util.*;

/**
 * Class to setup users
 *
 * */

public class User {

    // Choices List for drop down box
    public List<String> firstDropDownChoices;
    public Map<String, List<String>> dropDownMap;
    public List<Host> hosts;
    public Map<String, Server> serverMap;

    // User information from database
    public int userId;
    public String userName;
    public String password;
    public String name;

    private static final String RESTSERVICE = "http://192.168.67.4:8080/papillonserver/rest/";

    public User(int userId, String userName, String password, String name){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.firstDropDownChoices = new ArrayList<>();
        this.dropDownMap = new HashMap<>();
    }

    public User() {

    }

    private Map<String, Double> doMappingMonth(MainPower mainPower) {
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

    private Map<String, Double> doMappingDay(MainPower mainPower) {
        Map<String, Double> powerMap = new TreeMap<>();
        for (Power power : mainPower.getPower()) {
            // Europe/Dublin
            long secondsSinceEpoch = Long.parseLong(power.getTimeStamp());
            Instant instant = Instant.ofEpochSecond(secondsSinceEpoch);
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Dublin"));
            String day = ldt.getDayOfMonth() + " " +ldt.getMonth().toString() + " " + ldt.getYear();

            Double powerValue = Double.valueOf(power.getPower());
            if (powerMap.containsKey(day)) {
                powerMap.computeIfPresent(day, (key, val) -> val = val + powerValue);
            } else {
                powerMap.put(day, powerValue);
            }

        }
        return powerMap;
    }

    public Map<String, Double> getPowerData(String start, String end, Server server) throws IOException {

        URL url = new URL(RESTSERVICE + "datacenters/"+ server.datacenterId+"/floors/"+ server.floorId+"/racks/"+ server.rackId+"/hosts/"+ server.hostId+"/power?starttime="+start+"&endtime="+end);
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output = br.readLine();

        MainPower mainPower = new Gson().fromJson(output, MainPower.class);

        if(mainPower == null){
            return new HashMap<>();
        }else{
            Map<String, Double> powerGraph = doMappingMonth(mainPower);

            System.out.println("Output from Server .... \n");
            return powerGraph;
        }
    }

    public Map<String, Double> getPowerDataDay(String start, String end, Server server) throws IOException {

        URL url = new URL(RESTSERVICE + "datacenters/"+ server.datacenterId+"/floors/"+ server.floorId+"/racks/"+ server.rackId+"/hosts/"+ server.hostId+"/power?starttime="+start+"&endtime="+end);
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output = br.readLine();

        MainPower mainPower = new Gson().fromJson(output, MainPower.class);

        if(mainPower == null){
            return new HashMap<>();
        }else{
            Map<String, Double> powerGraph = doMappingDay(mainPower);

            System.out.println("Output from Server .... \n");
            return powerGraph;
        }
    }

    public void setUpUser() {

        // Get List of Hosts from table
        hosts = MYSQL.getServers(userId);

        // initialise lists and maps
        firstDropDownChoices = new ArrayList<>();
        dropDownMap = new HashMap<>();
        serverMap = new HashMap<>();

        // For each host get data
        for (Host host: hosts) {

            // Add the first value for choice
            if(!firstDropDownChoices.contains(host.school)) {
                firstDropDownChoices.add(host.school);
            }

            // Add key value for school to research group
            if (!dropDownMap.containsKey(host.school)) {
                dropDownMap.put(host.school, new ArrayList<>());
            }

            // check if the research group is already in the list, if not add it in
            if(!dropDownMap.get(host.school).contains(host.researchGroup)){
                dropDownMap.get(host.school).add(host.researchGroup);
            }

            // add key for research group if not in map
            if (!dropDownMap.containsKey(host.researchGroup)) {
                dropDownMap.put(host.researchGroup, new ArrayList<>());
            }

            // add value of it not already in the list
            if(!dropDownMap.get(host.researchGroup).contains(host.projectName)){
                dropDownMap.get(host.researchGroup).add(host.projectName);
            }

            // add project to map if not there
            if (!dropDownMap.containsKey(host.projectName)) {
                dropDownMap.put(host.projectName, new ArrayList<>());
            }

            // add server name to group if not in it already
            if(!dropDownMap.get(host.projectName).contains(host.serverName)){
                dropDownMap.get(host.projectName).add(host.serverName);
            }

            //initialise empty array
            dropDownMap.put("", new ArrayList<>());

            serverMap.put(host.serverName, new Server(host.datacenterId, host.floorId, host.rackId, host.hostId, host.annualBudget, host.carbonBudget));
        }

    }

}
