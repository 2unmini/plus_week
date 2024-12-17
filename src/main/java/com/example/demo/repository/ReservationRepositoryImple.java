package com.example.demo.repository;

import com.example.demo.entity.QItem;
import com.example.demo.entity.QReservation;
import com.example.demo.entity.QUser;
import com.example.demo.entity.Reservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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

    @Override
    public List<Reservation> getReservationByUserIdOrItemId(Long userId, Long itemId) {
        QReservation reservation=QReservation.reservation;
//        QUser user=QUser.user;
//        QItem item=QItem.item;

        return jpaQueryFactory.select(reservation)
                .from(reservation)
                .innerJoin(reservation.user).fetchJoin()
                .innerJoin(reservation.item).fetchJoin()
                .where(reservation.user.id.eq(userId).and(reservation.item.id.eq(itemId)))
                .fetch();
    }

    private BooleanExpression isUserEq(Long userId) {
        QReservation reservation=QReservation.reservation;
        return userId!=null ?reservation.user.id.eq(userId):null;
    }


    private BooleanExpression isItemEq(Long itemId) {
        QReservation reservation=QReservation.reservation;
        return itemId!=null ?reservation.item.id.eq(itemId):null;
    }

    private BooleanBuilder isContain(Long userId,Long itemId) {
        return new BooleanBuilder().and(isUserEq(userId)).and(isItemEq(itemId));
        /*return isUserEq(userId).and(isItemEq(itemId));*/
    }
}
