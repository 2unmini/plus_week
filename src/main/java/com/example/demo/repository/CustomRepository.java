package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository {
     List<Reservation> getReservationByUserId(Long id);

    List<Reservation> getReservationByItemId(Long id);

    List<Reservation> getReservationByUserIdOrItemId(Long userId,Long itemId);
}
