package com.kopec.wojciech.enginners_thesis.repository;

import com.kopec.wojciech.enginners_thesis.model.AbstractEntity;
import com.kopec.wojciech.enginners_thesis.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public interface TestableRepository<T extends AbstractEntity, S extends JpaRepository<T, Long>> {

    default void createEntityTest(T objToPersist, S repository) {
        long oldId = objToPersist.getId();
        int prePersistEntityCount = (int) repository.count();

        T persistedObj = repository.save(objToPersist);
        long newId = persistedObj.getId();

//        assertThat(oldId, is(not(newId)));
        assertThat((int) repository.count(), is(greaterThan(prePersistEntityCount)));
        assertThat(objToPersist, equalTo(persistedObj));
    }

    default void readEntityTest(T instance, S repository) {
        createEntityTest(instance, repository);

        T fetched = repository.findById(instance.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(instance, equalTo(fetched));
    }

    default void updateEntityTest(T original, T updated, S repository) {
        createEntityTest(original, repository);

        updated.setId(original.getId());

        T updatedPersisted = repository.save(updated);

        assertThat(original.getId(), is(updatedPersisted.getId()));
        assertThat(updatedPersisted, equalTo(updated));
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
