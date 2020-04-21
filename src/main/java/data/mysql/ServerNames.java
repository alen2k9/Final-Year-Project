package data.mysql;

import javafx.beans.property.SimpleStringProperty;

public class ServerNames {
    private SimpleStringProperty school;
    private SimpleStringProperty researchGroup;
    private SimpleStringProperty project;
    private SimpleStringProperty serverName;
    private SimpleStringProperty annualBudget;

    public ServerNames(String school, String researchGroup, String project, String serverName, int annualBudegt){
        this.school = new SimpleStringProperty(school);
        this.researchGroup = new SimpleStringProperty(researchGroup);
        this.project = new SimpleStringProperty(project);
        this.serverName = new SimpleStringProperty(serverName);
        this.annualBudget = new SimpleStringProperty(String.valueOf(annualBudegt));
    }

    public String getSchool() {
        return school.get();
    }

    public SimpleStringProperty schoolProperty() {
        return school;
    }

    public void setSchool(String school) {
        this.school.set(school);
    }

    public String getResearchGroup() {
        return researchGroup.get();
    }

    public SimpleStringProperty researchGroupProperty() {
        return researchGroup;
    }

    public void setResearchGroup(String researchGroup) {
        this.researchGroup.set(researchGroup);
    }

    public String getProject() {
        return project.get();
    }

    public SimpleStringProperty projectProperty() {
        return project;
    }

    public void setProject(String project) {
        this.project.set(project);
    }

    public String getServerName() {
        return serverName.get();
    }

    public SimpleStringProperty serverNameProperty() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName.set(serverName);
    }

    public String getAnnualBudget() {
        return annualBudget.get();
    }

    public SimpleStringProperty annualBudgetProperty() {
        return annualBudget;
    }

    public void setAnnualBudget(String annualBudget) {
        this.annualBudget.set(annualBudget);
    }
}
