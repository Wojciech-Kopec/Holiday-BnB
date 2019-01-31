package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests extends AbstractRepositoryTests<User, UserRepository> {

    @Autowired
    UserRepository userRepository;

    private User user = ModelProvider.createUser_1_noId();

    @Test
    public void createBasicUserEntityTest() {
        createEntityTest(user, userRepository);
    }

    @Test
    @Transactional
    public void readBasicUserEntityTest() {
        readEntityTest(user, userRepository);
    }

    @Test
    public void updateBasicUserEntityTest() {
        User updated = ModelProvider.createUser_2_noId();
        updateEntityTest(user, updated, userRepository);
    }

    @Test
    public void deleteUserEntityTest() {
        deleteEntityTest(user, userRepository);
    }

    @After
    @Override
    public void wipe() {
        userRepository.deleteAll();
    }
}
