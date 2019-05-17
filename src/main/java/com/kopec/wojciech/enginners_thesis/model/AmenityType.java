package com.kopec.wojciech.enginners_thesis.model;

public enum AmenityType {
    WIFI("WI-FI"), KITCHEN("Kitchen"), TV("TV"), HEATING("Heating"), BACKYARD("Backyard"), OTHER("Other");


    private String type;

    AmenityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static AmenityType parse(String type) {
        AmenityType amenityType = null;
        for (AmenityType value : AmenityType.values()) {
            if (value.getType().equals(type)) {
                amenityType = value;
                break;
            }
        }
        return amenityType;
    }
}
