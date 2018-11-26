package com.kopec.wojciech.enginners_thesis.model;

//TODO is this class needed? To analyze
public enum BookingStatus {
    VERIFIED(""), SUBMITTED(""), REJECTED("");

    private String type;

    BookingStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static BookingStatus parse(String type) {
        BookingStatus bookingStatus = null;
        for (BookingStatus value : BookingStatus.values()) {
            if (value.getType().equals(type)) {
                bookingStatus = value;
                break;
            }
        }
        return bookingStatus;
    }
}
