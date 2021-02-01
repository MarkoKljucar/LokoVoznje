package com.example.lokovoznje;

public class Ride {
    private int StartMilage;
    private String VehicleId;
    private String Date;
    private int EndMilage;
    private String Relation;
    private String Cause;

    public String getCause() { return Cause; }

    public void setCause(String cause) { Cause = cause; }

    public int getStartMilage() {
        return StartMilage;
    }

    public void setStartMilage(int startMilage) {
        StartMilage = startMilage;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) { VehicleId = vehicleId; }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getEndMilage() {
        return EndMilage;
    }

    public void setEndMilage(int endMilage) {
        EndMilage = endMilage;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }


}
