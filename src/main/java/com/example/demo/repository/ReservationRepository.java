package com.example.demo.repository;

import com.example.demo.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>,CustomRepository {


    List<Reservation> findByUserIdAndItemId(Long userId, Long itemId);

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByItemId(Long itemId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.item.id = :id " +
            "AND NOT (r.endAt <= :startAt OR r.startAt >= :endAt) " +
            "AND r.status = 'APPROVED'")
    List<Reservation> findConflictingReservations(
            @Param("id") Long id,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt
    );

    /**
     * 모든 예약을 조회하는 기능입니다. 사용자와 물건에 대한 정보를 가져오기 위해 별도로 접근하고 있습니다.
     * 현재 : 모든 예약을 조회할 때 연관된 테이블에 있는 정보를 가져오면서 N+1 문제가 발생합니다.
     * 개선 : 동일한 데이터를 가져올 때 N+1 문제가 발생하지 않게 수정합니다. 일단 해결방법 : entityGraph, FETCH JOIN
     * 연관된거 아이템, 유저
     */
    @Query("SELECT r FROM Reservation r " +
    "join fetch r.item i "
    +"join fetch r.user u")

    List<Reservation> findAllWithitemIdAndUserId();



}
