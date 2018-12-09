package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jws.WebParam;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccommodationRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    public void createUserEntityTest() {
        User user = ModelProvider.createOwnerUser();
        long oldId = user.getId();
        int prePersistEntityCount = userRepository.findAll().size();

        User persistedUser = userRepository.save(user);
        long newId = persistedUser.getId();

        assertThat(oldId, is(not(newId)));
        assertThat(userRepository.findAll().size(), is(greaterThan(prePersistEntityCount)));
        assertThat(user, equalTo(persistedUser));
    }

    @Test
    public void createEntityTest() {
        User user = userRepository.findByUsername(ModelProvider.createOwnerUser().getUsername());
        Accommodation accommodation = ModelProvider.createAccomodationObject1(user);

        long oldId = accommodation.getId();
        int prePersistEntityCount = accommodationRepository.findAll().size();

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);
        long newId = savedAccommodation.getId();
        assertThat(oldId, is(not(newId)));
        assertThat(accommodationRepository.findAll().size(), is(greaterThan(prePersistEntityCount)));
        assertThat(accommodation, equalTo(savedAccommodation));
    }

//    @Test
//    public void readEntityTest() {
//        Accommodation accommodation = accommodationRepository.findAll().get(0);
//
//        assertThat(accommodation, is(ModelProvider.createAccomodationObject1(ModelProvider.createOwnerUser())));
//    }
//
//    @Test
//    public void updateEntityTest() {
//        Accommodation accommodation = accommodationRepository.findAll().get(0);
//        accommodation.setName(accommodation.getName() + "_TEST");
//        accommodation.setDescription(accommodation.getDescription() + "_TEST");
//        accommodation.setAccommodationType(AccommodationType.FLAT.getType());
//        accommodation.setName(accommodation.getName() + "_TEST");
//        accommodation.setName(accommodation.getName() + "_TEST");
//        accommodation.setName(accommodation.getName() + "_TEST");
//        accommodation.setName(accommodation.getName() + "_TEST");
//        accommodation.setName(accommodation.getName() + "_TEST");
//    }
}
