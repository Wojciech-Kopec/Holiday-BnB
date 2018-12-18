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
    UserRepository userRepository;

    @Autowired
    AccommodationRepository accommodationRepository;

    private Accommodation accommodationEntity;

    @Before
    public void persistObjects() {
        User user = ModelProvider.createUser_1();
        accommodationEntity = ModelProvider.createAccomodation_1(user);

        userRepository.save(user);
        accommodationRepository.save(accommodationEntity);

        assertThat(userRepository.count(), is(1L));
        assertThat(accommodationRepository.count(), is(1L));
    }

    @Test
    @Transactional
    public void fullCriteriaTest() {
        AccommodationDto accommodationDto = AccommodationDto.toDto(accommodationEntity);

        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .name(accommodationDto.getName())
                .accommodationType(accommodationDto.getAccommodationType())
                .requiredGuestCount(accommodationDto.getMaxGuests())
                .pricePerNight(accommodationDto.getPricePerNight())
                .localization(accommodationDto.getLocalization())
                .amenities(accommodationDto.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .build();


        assertResults(accommodationEntity, criteria);
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

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void localizationCriteriaTest() {
        LocalizationDto criteria = LocalizationDto.toDto(accommodationEntity.getLocalization());

        List<Accommodation> filteredResults = (List<Accommodation>) accommodationRepository.findAll(
                LocalizationSpecification.withCriteria(criteria));

        assertThat(filteredResults.size(), is(1));
        Accommodation filterResult = filteredResults.get(0);
        assertThat(AccommodationDto.toDto(filterResult), equalTo(AccommodationDto.toDto(accommodationEntity)));
    }

    @Test
    public void singularCriteriaTypeTest() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .accommodationType(accommodationEntity.getAccommodationType())
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaAmenityListTest() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .amenities(accommodationEntity.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaDefaultPriceEquals0Test() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .pricePerNight(0) //if not specified
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaPriceTest() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .pricePerNight(accommodationEntity.getPricePerNight())
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaNameContainsTest() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .name(accommodationEntity.getName().substring(0, accommodationEntity.getName().length() - 1))
                .build();

        assertResults(accommodationEntity, criteria);
    }

    private void assertResults(Accommodation accommodationEntity, AccommodationCriteria criteria) {
        List<Accommodation> filteredResults = (List<Accommodation>) accommodationRepository.findAll(
                AccommodationSpecification.withCriteria(criteria));

        assertThat(filteredResults.size(), is(1));
        Accommodation filterResult = filteredResults.get(0);
        assertThat(AccommodationDto.toDto(filterResult), equalTo(AccommodationDto.toDto(accommodationEntity)));
    }

    @After
    public void clearDatabase() {
        accommodationRepository.deleteAll();
        userRepository.deleteAll();
    }
}