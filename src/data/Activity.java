package data;

public class Activity {

    private int id;
    private int hostId;
    private double power;
    private double stat1;
    private double stat2;
    private double stat3;
    private int timeStamp;
    private double allApps;
    private String powerMode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getStat1() {
        return stat1;
    }

    public void setStat1(double stat1) {
        this.stat1 = stat1;
    }

    public double getStat2() {
        return stat2;
    }

    public void setStat2(double stat2) {
        this.stat2 = stat2;
    }

    public double getStat3() {
        return stat3;
    }

    public void setStat3(double stat3) {
        this.stat3 = stat3;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getAllApps() {
        return allApps;
    }

    public void setAllApps(double allApps) {
        this.allApps = allApps;
    }

    public String getPowerMode() {
        return powerMode;
    }

    public void setPowerMode(String powerMode) {
        this.powerMode = powerMode;
    }
}
