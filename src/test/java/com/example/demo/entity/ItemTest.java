package com.example.demo.entity;

import com.example.demo.config.QueryDslConfig;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("status값이 해당 제약 조건이 동작하는지 (null false)")
    void testStatusNotNull() {

        User owner = new User("user","Abcd@naver.com","nickname1","Abcd1234");
        User user = new User("user","Qwer@naver.com","nickname2","Qwer1234");

        Item item = new Item("아이템", "설명", owner, user);
        item.setStatus(null);

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> itemRepository.save(item));

    }


}