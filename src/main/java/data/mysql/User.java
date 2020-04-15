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
 * Class to pass values for drop down box
 *
 * */

public class User {

    // Choices List for drop down box
    public List<String> firstDropDownChoices;
    public Map<String, List<String>> dropDownMap;
    public List<Host> hosts;
    public Map<String, Server> serverMap;

    public int userId;
    public String userName;
    public String password;
    public String name;


    // database information
    public String datacenterId;
    public String floorId;
    public String rackId;
    public String hostId;
    public static final String RESTSERVICE = "http://192.168.67.4:8080/papillonserver/rest/";

    // constructor
    /*public User(List<String> firstDropDownChoices, Map<String, List<String>> dropDownMap) {
        this.firstDropDownChoices = firstDropDownChoices;
        this.dropDownMap = dropDownMap;
    }*/

    public User(int userId, String userName, String password, String name){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.firstDropDownChoices = new ArrayList<>();
        this.dropDownMap = new HashMap<>();
    }

    /*public User( String userName, String password, String name){
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.firstDropDownChoices = new ArrayList<>();
        this.dropDownMap = new HashMap<>();
    }*/

    public User() {

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

    public Map<String, Double> getPowerData(String start, String end, Server server) throws IOException {

        URL url = new URL(RESTSERVICE + "datacenters/"+ server.datacenterId+"/floors/"+ server.floorId+"/racks/"+ server.rackId+"/hosts/"+ server.hostId+"/power?starttime="+start+"&endtime="+end);
       // URL url = new URL("http://192.168.67.4:8080/papillonserver/rest/datacenters/266/floors/290/racks/293/hosts/286/power?starttime=0&endtime=1585427363");
                            //http://192.168.67.4:8080/papillonserver/rest/datacenters/266/floors/290/racks/293/hosts/286/power?starttime=1585785600&endtime=1587081600
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        //        if (connection.getResponseCode() != 200) {
        //            throw new RuntimeException("Failed : HTTP error code : "
        //                    + connection.getResponseCode());
        //        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output = br.readLine();

        MainPower mainPower = new Gson().fromJson(output, MainPower.class);

        if(mainPower == null){
            return new HashMap<String, Double>();
        }else{
            Map<String, Double> powerGraph = doMapping(mainPower);

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

            serverMap.put(host.serverName, new Server(host.datacenterId, host.floorId, host.rackId, host.hostId));
        }

    }

}
