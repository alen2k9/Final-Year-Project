package data.mysql;

public class Server {
    public int datacenterId;
    public int floorId;
    public int rackId;
    public int hostId;
    public int annualBudget;
    public int carbonBudget;

    public Server(int datacenterId, int floorId, int rackId, int hostId, int annualBudget, int carbonBudget) {
        this.datacenterId = datacenterId;
        this.floorId = floorId;
        this.rackId = rackId;
        this.hostId = hostId;
        this.annualBudget = annualBudget;
        this.carbonBudget = carbonBudget;
    }
}
