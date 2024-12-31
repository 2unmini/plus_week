package com.example.demo.controller;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.dto.Authentication;
import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.dto.ReservationResponseDto;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemType;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ReservationService reservationService;
    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    Authentication validUser = new Authentication(1L, Role.USER);

    @Test
    void createReservation() throws Exception {
        // given

        User user = new User("user", "Abcd@naver.com", "nickname1", "Abcd1234");
        User manager = new User("user", "Qwer@naver.com", "nickname2", "Qwer1234");
        Item item = new Item(1L, "이름", "설명", user, manager, ItemType.PENDING);


        ReservationResponseDto reservationResponseDto = new ReservationResponseDto(1L, user.getNickname(), item.getName(), LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        given(reservationService.createReservation(item.getId(), user.getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(2))).willReturn(reservationResponseDto);
        ReservationRequestDto requestDto = new ReservationRequestDto(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        // when
        // then
        mockMvc.perform(post("/reservations")
                        .sessionAttr(GlobalConstants.USER_AUTH, validUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isCreated());
    }
    // todo 컨트롤러 테스트 미구현 된 부분 구현

    @Test
    void updateReservation() {
    }

    @Test
    void findAll() {
    }

    @Test
    void searchAll() {
    }
}