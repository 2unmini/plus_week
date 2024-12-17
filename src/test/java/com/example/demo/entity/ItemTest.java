package com.example.demo.entity;

import com.example.demo.repository.ItemRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
@SpringBootTest
class ItemTest {
    ItemRepository itemRepository;
    @Test
    @DisplayName("status값이 해당 제약 조건이 동작하는지 (null false)")
    void testStatusNotNull () {

            Item item = Item.builder()
                    .id(1L)
                    .name("이름")
                    .description("설명")
                    .status(null)
                    .build();

         Assertions.assertThrows(NullPointerException.class, ()->itemRepository.save(item));

    }

  
}