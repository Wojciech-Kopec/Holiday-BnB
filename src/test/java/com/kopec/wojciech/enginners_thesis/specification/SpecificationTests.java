package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import com.kopec.wojciech.enginners_thesis.model.ModelProvider;
import com.kopec.wojciech.enginners_thesis.model.User;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecificationTests {

    @Autowired
    static UserRepository userRepository;

    @Autowired
    static AccommodationRepository accommodationRepository;

    static Accommodation accommodationEntity;

    @BeforeClass
    public static void persistObjects() {
        User user = ModelProvider.createUser1();
        accommodationEntity = ModelProvider.createAccomodation1(user);

        userRepository.save(user);
        accommodationRepository.save(accommodationEntity);
    }

    @Test
    @Transactional
    public void fullCriteriaTest() {
        AccommodationDto accommodation = AccommodationDto.toDto(accommodationEntity);

        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .name(accommodation.getName())
                .accommodationType(accommodation.getAccommodationType())
                .requiredGuestCount(accommodation.getMaxGuests())
                .pricePerNight(accommodation.getPricePerNight())
                .localization(accommodation.getLocalization())
                .amenities(accommodation.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .build();


        List<Accommodation> filteredResults = (List<Accommodation>) accommodationRepository.findAll(
                AccommodationSpecification.withCriteria(criteria));

        assertThat(filteredResults.size(), is(1));
        Accommodation filterResult = filteredResults.get(0);
        assertThat(filterResult, equalTo(accommodation));
    }

    @Test
    public void accommodationCriteriaTest() {
        AccommodationDto accommodation = AccommodationDto.toDto(accommodationEntity);

        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .name(accommodation.getName())
                .accommodationType(accommodation.getAccommodationType())
                .requiredGuestCount(accommodation.getMaxGuests())
                .pricePerNight(accommodation.getPricePerNight())
                .amenities(accommodation.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .build();

        List<Accommodation> filteredResults = (List<Accommodation>) accommodationRepository.findAll(
                AccommodationSpecification.withCriteria(criteria));

        assertThat(filteredResults.size(), is(1));
        Accommodation filterResult = filteredResults.get(0);
        assertThat(filterResult, equalTo(accommodationEntity));
    }

    @Test
    public void localizationCriteriaTest() {
        LocalizationDto criteria = LocalizationDto.toDto(accommodationEntity.getLocalization());

        List<Accommodation> filteredResults = (List<Accommodation>) accommodationRepository.findAll(
                LocalizationSpecification.withCriteria(criteria));

        assertThat(filteredResults.size(), is(1));
        Accommodation filterResult = filteredResults.get(0);
        assertThat(filterResult, equalTo(accommodationEntity));
    }

    @Test
    public void singularCriteriaTest() {
        Accommodation accommodationEntity = ModelProvider.createAccomodation1(ModelProvider.createUser1());
        AccommodationDto accommodation = AccommodationDto.toDto(accommodationEntity);

        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .accommodationType(accommodation.getAccommodationType())
                .build();

        List<Accommodation> filteredResults = (List<Accommodation>) accommodationRepository.findAll(
                AccommodationSpecification.withCriteria(criteria));

        assertThat(filteredResults.size(), is(1));
        Accommodation filterResult = filteredResults.get(0);
        assertThat(filterResult, equalTo(accommodationEntity));
    }

    @Test
    public void selectedCriteriaTest() {

    }

//    @AfterClass
//    public static void clearDatabase() {
//        accommodationRepository.deleteAll();
//        userRepository.deleteAll();
//    }
}
