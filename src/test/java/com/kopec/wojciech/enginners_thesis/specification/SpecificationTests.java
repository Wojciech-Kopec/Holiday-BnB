package com.kopec.wojciech.enginners_thesis.specification;

import com.kopec.wojciech.enginners_thesis.dto.AccommodationDto;
import com.kopec.wojciech.enginners_thesis.dto.LocalizationDto;
import com.kopec.wojciech.enginners_thesis.model.*;
import com.kopec.wojciech.enginners_thesis.repository.AccommodationRepository;
import com.kopec.wojciech.enginners_thesis.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*TODO with modifications in Criteria class
with Price and Accomodation Types please modify this class accordingly*/
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
        accommodationEntity = ModelProvider.createAccommodation_1(user);

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
                .accommodationTypes(Collections.singletonList(accommodationDto.getAccommodationType()))
                .requiredGuestCount(accommodationDto.getMaxGuests())
                .maxPricePerNight(accommodationDto.getPricePerNight())
                .amenities(accommodationDto.getAmenities().stream().map(
                        amenity -> amenity.getType().toString()).collect(Collectors.toList()))
                .localization(accommodationDto.getLocalization())
                .build();


        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void accommodationCriteriaTest() {
        AccommodationDto accommodation = AccommodationDto.toDto(accommodationEntity);

        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .name(accommodation.getName())
                .accommodationTypes(Collections.singletonList(accommodation.getAccommodationType()))
                .requiredGuestCount(accommodation.getMaxGuests())
                .maxPricePerNight(accommodation.getPricePerNight())
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
        AccommodationCriteria criteria1 = AccommodationCriteria.builder()
                .accommodationTypes(Collections.singletonList(accommodationEntity.getAccommodationType()))
                .build();

        assertResults(accommodationEntity, criteria1);

        AccommodationCriteria criteria2 = AccommodationCriteria.builder()
                .accommodationTypes(Lists.newArrayList(accommodationEntity.getAccommodationType(),
                        AccommodationType.CABIN, AccommodationType.SUMMER_HOUSE, AccommodationType.SUITE)
                )
                .build();

        assertResults(accommodationEntity, criteria2);
    }

    @Test
    public void singularCriteriaAmenityListTest() {
        List<Amenity> amenities = accommodationEntity.getAmenities();
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .amenities(amenities.stream().map(
                        amenity -> amenity.getType().toString()).limit(amenities.size() - 1).collect(Collectors.toList()))
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void defaultEmptyCriteriaTest() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaPriceTest1() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .minPricePerNight(accommodationEntity.getPricePerNight() - 1)
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaPriceTest2() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .minPricePerNight(accommodationEntity.getPricePerNight())
                .maxPricePerNight(accommodationEntity.getPricePerNight())
                .build();

        assertResults(accommodationEntity, criteria);
    }

    //FIXME These 2 below don't really work as Predicate is set as NULL in that conditions = all entities are fetched.
    @Test
    public void singularCriteriaPriceTest3() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .minPricePerNight(accommodationEntity.getPricePerNight())
                .maxPricePerNight(accommodationEntity.getPricePerNight() - 10)
                .build();

        assertResults(accommodationEntity, criteria);
    }

    @Test
    public void singularCriteriaPriceTest4() {
        AccommodationCriteria criteria = AccommodationCriteria.builder()
                .minPricePerNight(accommodationEntity.getPricePerNight() + 10)
                .maxPricePerNight(accommodationEntity.getPricePerNight())
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
