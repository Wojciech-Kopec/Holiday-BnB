package com.kopec.wojciech.engineers_thesis.repository;

import com.kopec.wojciech.engineers_thesis.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/*
* TODO
* Add these Test Cases:
* 1. User with 2 Accommodations - deletion of Accommodation & User
* 2. Same as above but with Bookings - User/Booking & Accommodation/Booking
* 3. Add assertions with Localization & Amenities entities in TCs with Accommodation
* */
abstract public class AbstractRepositoryTests<T extends AbstractEntity, S extends JpaRepository<T, Integer>> {

    protected void createEntityTest(T objToPersist, S repository) {
        Integer oldId = objToPersist.getId();
        int prePersistEntityCount = (int) repository.count();

        T persistedObj = repository.save(objToPersist);
        Integer newId = persistedObj.getId();

        assertThat(oldId, is(not(newId)));
        assertThat((int) repository.count(), is(greaterThan(prePersistEntityCount)));
        assertThat(objToPersist, equalTo(persistedObj));
    }

    protected void readEntityTest(T instance, S repository) {
        createEntityTest(instance, repository);

        T fetched = repository.findById(instance.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(instance, equalTo(fetched));
    }

    protected void updateEntityTest(T original, T updated, S repository) {
        createEntityTest(original, repository);

        updated.setId(original.getId());

        T updatedPersisted = repository.save(updated);

        assertThat(original.getId(), is(updatedPersisted.getId()));
        assertThat(updatedPersisted, equalTo(updated));
        assertThat(repository.count(), is(1L));
    }

    protected void deleteEntityTest(T instance, S repository) {
        createEntityTest(instance, repository);

        T t = repository.findById(instance.getId()).orElseThrow(EntityNotFoundException::new);
        repository.delete(t);

        assertThat(repository.count(), is(0L));
        assertThat(repository.findById(t.getId()).orElse(null), is(nullValue()));
    }

    @SuppressWarnings("unused")
    abstract void wipe();
}
