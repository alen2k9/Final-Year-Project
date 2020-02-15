package home.controllers.rest;

public class Papillon {

    // TODO: get method for all datacentres
    public void getAllDatacentres(){
        /**
         *  /datacenters
         *
         *  */
    }

    // TODO: get method datacentre with id
    public void getDatacentres(int datacenterId){
        /**
         * /datacenters/{datacenterId}
         *
         *  */
    }

    // TODO: Gets a datacenter’s power usage for a given time span(i.e. between starttime and endtime)
    public void getDatacentrePowerUsage(int datacenterId, int startTime, int endTime){
        /**
         * /datacenters/{datacenterId}/power?starttime={startTime}&endtime={endTime}
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a datacenter’s inactive hosts given the time offset (in minutes). For
    // TODO: example, if the limit is set to 30 it means it will return all hosts that has
    // TODO: reported (recorded activity) to the master the last 30 minutes
    public void getDatacentreHeartbeat(int datacenterId, int offset){
        /**
         * /datacenters/{datacenterId}/heartbeat?offset={offset}
         *
         * Look at API reference
         *  */
    }


}
