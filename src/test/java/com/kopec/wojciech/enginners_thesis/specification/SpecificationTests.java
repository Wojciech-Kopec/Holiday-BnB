package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepositoryTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecificationTests {

    @Autowired
    AccommodationRepositoryTests accommodationRepositoryTests;

    @Autowired
    AccommodationRepository repository;


    @Test
    public void accommodationCriteriaTest() {
        accommodationRepositoryTests.createAccommodationEntityTest();
        Accommodation accommodation = ModelProvider.createAccomodation1(ModelProvider.createUser1());

        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .name(accommodation.getName())
                .accommodationType(accommodation.getAccommodationType())
                .requiredGuestCount(accommodation.getMaxGuests())
                .pricePerNight(accommodation.getPricePerNight())
                .localization(accommodation.getLocalization())
                .amenities(accommodation.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .build();

        //TODO Localization is not used in filtering

        AccommodationSpecification specification = new AccommodationSpecification(criteria);

        List<Accommodation> filteredResults = (List<Accommodation>) repository.findAll(specification.withCriteria());
        assertThat(filteredResults.size(), is(1));

        Accommodation filterResult = filteredResults.get(0);
        //TODO HSQLDB Expected Localization is not set (?)
        assertThat(filterResult, equalTo(accommodation));
    }

    @Test
    public void localizationCriteriaTest() {

    }

    @Test
    public void singularCriteriaTest() {

    }

    @Test
    public void selectedCriteriaTest() {

    }

    @Test
    public void fullCriteriaTest() {

    }
}
