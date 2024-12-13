package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("PENDING"){
        @Override
        public boolean canChangeTo(ReservationStatus status) {
            switch (status) {
                case APPROVED, CANCELED, EXPIRED -> {
                    return true;
                }

            }
            return false;
        }
    }
    ,APPROVED("APPROVED"){
        @Override
        public boolean canChangeTo(ReservationStatus status) {
            return status.equals(APPROVED);
        }
    }
    ,CANCELED("CANCELED"){
        @Override
        public boolean canChangeTo(ReservationStatus status) {
            return status.equals(CANCELED);
        }
    }
    ,EXPIRED("EXPIRED") {
        @Override
        public boolean canChangeTo(ReservationStatus status) {
            return status.equals(EXPIRED);
        }
    };

    private String status;

    ReservationStatus(String status) {
        this.status = status;
    }
    public abstract boolean canChangeTo(ReservationStatus nextStatus);

    public ReservationStatus changeTo(ReservationStatus status) {
        if(canChangeTo(status)){
            return status;
        }
        else {
            throw new IllegalArgumentException("올바르지 않은 상태: " + status);
        }
    }
}
