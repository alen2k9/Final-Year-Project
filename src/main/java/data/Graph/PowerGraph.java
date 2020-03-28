package data.Graph;

import data.power.MainPower;
import data.power.Power;
import javafx.scene.Parent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PowerGraph {
    public Map<String, Double> powerMap;

    public PowerGraph(){
        Map<String, Double> powerMap = new HashMap<>();
    }

    public void doMapping(MainPower mainPower){
        for (Power power : mainPower.getPower() ) {
            // Europe/Dublin
            long secondsSinceEpoch = Long.parseLong(power.getTimeStamp());
            Instant instant = Instant.ofEpochSecond(secondsSinceEpoch);
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Dublin"));
            String month = ldt.getMonth().toString();

            Double powerValue = Double.valueOf(power.getPower());
            if(powerMap.containsKey(month)) {
                powerMap.computeIfPresent(month, (key, val) -> val = val + powerValue);
            }
            else {
                powerMap.put(month, powerValue);
            }

        }
    }

}
