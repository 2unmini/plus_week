package com.example.demo.service;

import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.entity.*;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RentalLogService rentalLogService;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    void createReservationSuccess() {
        //given
        ReservationRequestDto requestDto = new ReservationRequestDto(1L,
                1L
                , LocalDateTime.now()
                , LocalDateTime.now());
        User owner = new User(1L, "Abcd@naver.com", "천준민", "비밀번호", UserStatus.NORMAL, Role.USER);
        User manager = new User(2L, "Qwer@naver.com", "관리자", "비밀번호1", UserStatus.NORMAL, Role.USER);
        Item item = new Item(1L, "아이템명", "설명", owner, manager, ItemType.PENDING);
        Reservation reservation = new Reservation(1L, item, owner, LocalDateTime.now(), LocalDateTime.now().plusHours(1), ReservationStatus.PENDING);
        given(userRepository.findById(any())).willReturn(Optional.of(owner));
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        given(reservationRepository.save(any())).willReturn(reservation);

        ReservationResponseDto responseDto = reservationService.createReservation(requestDto.getItemId(), requestDto.getUserId(), requestDto.getStartAt(), requestDto.getEndAt());
        assertEquals("같은가요", responseDto.getItemName(), reservation.getItem().getName());
    }

    @Test
    @DisplayName("QueryDSL 테스트")
    void getReservations() {
        //given
        User owner = new User(1L, "Abcd@naver.com", "천준민", "비밀번호", UserStatus.NORMAL, Role.USER);
        User manager = new User(2L, "Qwer@naver.com", "관리자", "비밀번호1", UserStatus.NORMAL, Role.USER);
        Item item = new Item(1L, "1아이템명", "설명", owner, manager, ItemType.PENDING);
        Reservation reservation1 = new Reservation(1L, item, owner, LocalDateTime.now(), LocalDateTime.now().plusHours(1), ReservationStatus.PENDING);
        Reservation reservation2 = new Reservation(2L, item, owner, LocalDateTime.now(), LocalDateTime.now().plusHours(1), ReservationStatus.PENDING);

        List<Reservation> reservations = List.of(reservation1, reservation2);
        given(reservationRepository.getReservationByUserIdOrItemId(1L, 1L)).willReturn(reservations);
        List<ReservationResponseDto> reservationResponseDtos = reservationService.searchAndConvertReservations(1L, 1L);
        assertEquals("리스트 크기가 같다", reservations.size(), reservationResponseDtos.size());


        //일단 아이디 1번이 생성한 아이템 1라고 가정
        // 아이디 2번이 생성한 아이템 2라고 가정해보고 짜보자


    }

    @Test
    @DisplayName("PENDDING 상태가 같은 상태로 변경이 안되는지 테스트")
    void updateReservationStatus() {
        User owner = new User(1L, "Abcd@naver.com", "천준민", "비밀번호", UserStatus.NORMAL, Role.USER);
        User manager = new User(2L, "Qwer@naver.com", "관리자", "비밀번호1", UserStatus.NORMAL, Role.USER);
        Item item = new Item(1L, "아이템명", "설명", owner, manager, ItemType.PENDING);
        Reservation reservation = new Reservation(1L, item, owner, LocalDateTime.now(), LocalDateTime.now().plusHours(1), ReservationStatus.PENDING);
        given(reservationRepository.findById(any())).willReturn(Optional.of(reservation));
        // PENDING, APPROVED, CANCELED, EXPIRED);
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.updateReservationStatus(reservation.getId(), "PENDING"));
    }
}