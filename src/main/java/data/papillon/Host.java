package data.papillon;

public class Host {

    private int id;
    private int rackId;
    private int modelGroupId;
    private String name;
    private String description;
    private String hostType;
    private String IPAddress;
    private int processorCount;
    private int VMCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRackId() {
        return rackId;
    }

    public void setRackId(int rackId) {
        this.rackId = rackId;
    }

    public int getModelGroupId() {
        return modelGroupId;
    }

    public void setModelGroupId(int modelGroupId) {
        this.modelGroupId = modelGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getProcessorCount() {
        return processorCount;
    }

    public void setProcessorCount(int processorCount) {
        this.processorCount = processorCount;
    }

    public int getVMCount() {
        return VMCount;
    }

    public void setVMCount(int VMCount) {
        this.VMCount = VMCount;
    }
}
