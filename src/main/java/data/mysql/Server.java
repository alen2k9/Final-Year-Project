package data.mysql;

public class Server {
    public int datacenterId;
    public int floorId;
    public int rackId;
    public int hostId;

    public Server(int datacenterId, int floorId, int rackId, int hostId) {
        this.datacenterId = datacenterId;
        this.floorId = floorId;
        this.rackId = rackId;
        this.hostId = hostId;
    }
}
