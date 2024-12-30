package com.example.demo.repository;

import com.example.demo.entity.QReservation;
import com.example.demo.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements CustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Reservation> getReservationByUserIdOrItemId(Long userId, Long itemId) {
        QReservation reservation = QReservation.reservation;

        return jpaQueryFactory.select(reservation)
                .from(reservation)
                .where(userId!=null ?QReservation.reservation.user.id.eq(userId):null)
                .where(itemId!=null ? QReservation.reservation.item.id.eq(itemId):null)
                .fetch();
    }

}
