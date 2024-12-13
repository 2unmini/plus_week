package com.example.demo.repository;

import com.example.demo.entity.QReservation;
import com.example.demo.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImple implements CustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Reservation> getReservationByUserId(Long id){
        QReservation reservation = QReservation.reservation;
        return jpaQueryFactory.select(reservation)
                .from(reservation)
                .where(reservation.user.id.eq(id))
                .fetch();
    }

    @Override
    public List<Reservation> getReservationByItemId(Long id) {
        QReservation reservation=QReservation.reservation;
        return jpaQueryFactory.select(reservation)
                .from(reservation)
                .where(reservation.item.id.eq(id))
                .fetch();
    }
}
