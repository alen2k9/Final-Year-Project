package data.mysql;

public class Host {

    public int serverId;

    public String school;
    public String researchGroup;
    public String projectName;
    public String serverName;

    public int datacenterId;
    public int floorId;
    public int rackId;
    public int hostId;

    public Host(int serverId, String school, String researchGroup, String projectName, String serverName, int datacenterId, int floorId, int rackId, int hostId) {
        this.serverId = serverId;
        this.school = school;
        this.researchGroup = researchGroup;
        this.projectName = projectName;
        this.serverName = serverName;
        this.datacenterId = datacenterId;
        this.floorId = floorId;
        this.rackId = rackId;
        this.hostId = hostId;
    }

}
