package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class AccommodationRepositoryTests {

    @Autowired
    AccommodationRepository repository;
    @Test
    public void saveEntityTest() {
        User user = ModelProvider.createOwnerUser();
        Accommodation accommodation = ModelProvider.createAccomodationObject(user);
        long oldId = accommodation.getId();
        Accommodation savedAccommodation = repository.save(accommodation);
        long newId = savedAccommodation.getId();
        assertThat(oldId, is(newId));
    }
}
