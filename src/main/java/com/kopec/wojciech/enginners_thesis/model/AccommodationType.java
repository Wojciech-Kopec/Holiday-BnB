package com.kopec.wojciech.enginners_thesis.model;

public enum AccommodationType {
    FLAT("Flat"), HOUSE("House"), SUMMER_HOUSE("Summer House"), CABIN("Cabin"), SUITE("Suite"), ROOM("Room");

    private String type;

    AccommodationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static AccommodationType parse(String type) {
        AccommodationType accommodationType = null;
        for (AccommodationType value : AccommodationType.values()) {
            if (value.getType().equals(type)) {
                accommodationType = value;
                break;
            }
        }
        return accommodationType;
    }
}
