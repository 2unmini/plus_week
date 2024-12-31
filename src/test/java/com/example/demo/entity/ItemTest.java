package com.example.demo.entity;

import com.example.demo.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class ItemTest {

    @Autowired
    private ItemRepository itemRepository;


    @Test
    @DisplayName("status값이 해당 제약 조건이 동작하는지 (null false)")
    void testStatusNotNull() {

        User owner = new User("user", "Abcd@naver.com", "nickname1", "Abcd1234");
        User user = new User("user", "Qwer@naver.com", "nickname2", "Qwer1234");

        Item item = new Item("아이템", "설명", owner, user);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> itemRepository.save(item));
// @Dynamic insert를 지우면 잘동작되는 테스트
    }


}