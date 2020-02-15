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

    /**
     *
     *
     *
     *
     *              Floor
     *
     *
     *
     *
     *
     *
     * */

    // TODO: Gets a datacenter’s all floors with hierarchical data applied
    public void getAllFloors(int datacenterId){
        /**
         *  /datacenters/{datacenterId}/allfloors
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a datacenter’s all floors with hierarchical data and power usage applied
    public void getAllFloorsPowerUsage(int datacenterId, int startTime, int endTime){
        /**
         *  /datacenters/{datacenterId}/allfloors/powers?starttime={startTime}&endtime={endTime}
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a datacenter’s all floors
    public void getFloors(int datacenterId){
        /**
         *  /datacenters/{datacenterId}/floors
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a floor
    public void getFloors(int datacenterId, int floorId){
        /**
         *  /datacenters/{datacenterId}/floors/{floorId}
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a floors’s power usage for a given time span
    public void getFloorPowerUsage(int datacenterId, int floorId, int startTime, int endTime){
        /**
         *  /datacenters/{datacenterId}/floors/{floorId}/power?starttime={startTime}&endtime={endTime}
         *
         * Look at API reference
         *  */
    }


    /**
     *
     *
     *
     *
     *              Racks
     *
     *
     *
     *
     *
     *
     * */

    // TODO: Gets a datacenter’s all racks with hierarchical data applied
    public void getAllRacks(int datacenterId){
        /**
         *  /datacenters/{datacenterId}/allracks
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a datacenter’s all racks with hearchical data and power usage applied
    public void getAllRacksPowerUsage(int datacenterId, int startTime, int endTime){
        /**
         *  /datacenters/{datacenterId}/allracks/powers?starttime={startTime}&endtime={endTime}
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a rack belonging to a floor/datacenter
    public void getRack(int datacenterId, int floorId, int rackId){
        /**
         *  /datacenters/{datacenterId}/floors/{floorId}/racks /{rackId}
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets all racks belonging to a floor/datacenter
    public void getRacks(int datacenterId, int floorId){
        /**
         *  /datacenters/{datacenterId}/floors/{floorId}/racks/
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a rack’s power usage for a given time span
    public void getFloorPowerUsage(int datacenterId, int floorId, int rackId,int startTime, int endTime){
        /**
         *  /datacenters/{datacenterId}/floors/{floorId}/racks/{rackId}/power?starttime={startTime}&endtime={endTime}
         *
         * Look at API reference
         *  */
    }

    /**
     *
     *
     *
     *
     *              Hosts
     *
     *
     *
     *
     *
     *
     * */

    // TODO: Gets a datacenter’s all hosts with hierarchical data applied
    public void getAllHosts(int datacenterId){
        /**
         *  /datacenters/{datacenterId}/allhosts
         *
         * Look at API reference
         *  */
    }

    // TODO: Gets a datacenter’s all hosts with hearchical data and power usage applied
    public void getAllHostsPowerUsage(int datacenterId, int startTime, int endTime){
        /**
         *  /datacenters/{datacenterId}/allhosts/powers?starttime={startTime}&endtime={endTime}
         *
         * Look at API reference
         * */
    }


}
