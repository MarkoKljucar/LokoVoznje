package com.example.lokovoznje;

public class Vehicle {
    public static String VEHICLE_ID="vehicle_id";
    private String Registration;
    private String OwnerId;
    private String EngineType;
    private String CarName;
    private Float AverageFuel;

    public Vehicle() {
    }

    public Vehicle(String registration, String ownerId, String engineType, String carName, Float averageFuel) {
        Registration = registration;
        OwnerId = ownerId;
        EngineType = engineType;
        AverageFuel = averageFuel;
        CarName = carName;

    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }


    public String getRegistration() {
        return Registration;
    }

    public void setRegistration(String registration) {
        Registration = registration;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getEngineType() {
        return EngineType;
    }

    public void setEngineType(String engineType) {
        EngineType = engineType;
    }

    public Float getAverageFuel() {
        return AverageFuel;
    }

    public void setAverageFuel(Float averageFuel) {
        AverageFuel = averageFuel;
    }
}
