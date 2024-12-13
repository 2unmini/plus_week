package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING("PENDING"),APPROVED("APPROVED"),CANCELED("CANCELED"),EXPIRED("EXPIRED");
    private String status;

    ReservationStatus(String status) {
        this.status = status;
    }
}
