package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.AbstractEntity;
import com.kopec.wojciech.enginners_thesis.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public interface TestableRepository<T extends AbstractEntity, S extends JpaRepository<T, Long>> {

    default void createEntityTest(T objToPersist, S repository) {
        long oldId = objToPersist.getId();
        int prePersistEntityCount = (int) repository.count();

        T persistedObj = repository.save(objToPersist);
        long newId = persistedObj.getId();

        assertThat(oldId, is(not(newId)));
        assertThat((int) repository.count(), is(greaterThan(prePersistEntityCount)));
        assertThat(objToPersist, equalTo(persistedObj));
    }

    default void readEntityTest(T instance, S repository) {
        createEntityTest(instance, repository);

        T fetched = repository.findById(instance.getId()).orElseThrow(EntityNotFoundException::new);
        if (instance instanceof Accommodation) {

            assertThat(((Accommodation) instance).getId(), is(((Accommodation) fetched).getId()));
            assertThat(((Accommodation) instance).getName(), is(((Accommodation) fetched).getName()));
            assertThat(((Accommodation) instance).getDescription(), is(((Accommodation) fetched).getDescription()));
            assertThat(((Accommodation) instance).getAccommodationType(), is(((Accommodation) fetched).getAccommodationType()));
            assertThat(((Accommodation) instance).getPricePerNight(), is(((Accommodation) fetched).getPricePerNight()));
            assertThat(((Accommodation) instance).getMaxGuests(), is(((Accommodation) fetched).getMaxGuests()));
            assertThat(((Accommodation) instance).getCreatedDate(), is(((Accommodation) fetched).getCreatedDate()));

            assertThat(((Accommodation) instance).getLocalization(), is(((Accommodation) fetched).getLocalization()));
            assertThat(((Accommodation) instance).getAmenities(), containsInAnyOrder(((Accommodation) fetched).getAmenities().toArray()));
            assertThat(((Accommodation) instance).getUser(), is(((Accommodation) fetched).getUser()));

            assertThat(((Accommodation) instance).equals((Accommodation) fetched), is(true));

        }
        assertThat(instance.equals(fetched), is(true));
        assertThat(instance, equalTo(fetched));
    }

    default void updateEntityTest(T original, T updated, S repository) {
        createEntityTest(original, repository);

        updated.setId(original.getId());

        T updatedPersisted = repository.save(updated);

        assertThat(original.getId(), is(updatedPersisted.getId()));
        assertThat(updatedPersisted, samePropertyValuesAs(updated));
    }

    default void deleteEntityTest(T instance, S repository) {
        createEntityTest(instance, repository);

        T t = repository.findById(instance.getId()).orElseThrow(EntityNotFoundException::new);
        repository.delete(t);

        assertThat(repository.count(), is(0L));
        assertThat(repository.findById(t.getId()).orElse(null), is(nullValue()));
    }

    void wipe();
}
