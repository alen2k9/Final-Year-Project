package data.power;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Power {

    @SerializedName("power")
    @Expose
    private String power;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}