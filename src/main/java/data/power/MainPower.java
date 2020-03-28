package data.power;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainPower {

    @SerializedName("power")
    @Expose
    private List<Power> power = null;

    public List<Power> getPower() {
        return power;
    }

    public void setPower(List<Power> power) {
        this.power = power;
    }

}