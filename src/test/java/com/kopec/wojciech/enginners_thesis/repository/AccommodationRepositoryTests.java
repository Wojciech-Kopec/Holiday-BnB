package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.kopec.wojciech.enginners_thesis.model.ModelProvider.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccommodationRepositoryTests implements TestableRepository<Accommodation, AccommodationRepository> {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryTests userRepositoryTests;

    private User user1 = createUser_1_noId();
    private User user2 = createUser_2_noId();
    private Accommodation accommodation = createAccommodation_1_noId(user1);
    private Accommodation accommodation2 = createAccommodation_2_noId(user2);


    /*TODO states of inner object should be validated as well f.e if Ids of Localization, Amenities etc. are set */
    @Test
    public void createAccommodationEntityTest() {
        userRepositoryTests.createEntityTest(user1, userRepository);
        createEntityTest(accommodation, accommodationRepository);
    }

    @Test
    @Transactional
    public void readAccommodationEntityTest() {
        userRepositoryTests.createEntityTest(user1, userRepository);
        readEntityTest(accommodation, accommodationRepository);
    }

    @Test
    public void updateAccommodationEntityTest() {
        userRepositoryTests.createEntityTest(user1, userRepository);
        userRepositoryTests.createEntityTest(user2, userRepository);

        Accommodation updated = accommodation2;
        updateEntityTest(accommodation, updated, accommodationRepository);
    }

    @Test
    public void deleteAccommodationEntityTest() {
        userRepositoryTests.createEntityTest(user1, userRepository);
        deleteEntityTest(accommodation, accommodationRepository);
    }

    @After
    @Override
    public void wipe() {
        accommodationRepository.deleteAll();
        userRepository.deleteAll();
    }
}
