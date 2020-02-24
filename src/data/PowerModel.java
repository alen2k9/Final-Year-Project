package data;

public class PowerModel {

    private int id;
    private String name;
    private String description;
    private String modelType;
    private int modelVersion;
    private String powerMode;
    private int modelGroupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public int getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(int modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getPowerMode() {
        return powerMode;
    }

    public void setPowerMode(String powerMode) {
        this.powerMode = powerMode;
    }

    public int getModelGroupId() {
        return modelGroupId;
    }

    public void setModelGroupId(int modelGroupId) {
        this.modelGroupId = modelGroupId;
    }
}
