package com.kopec.wojciech.engineers_thesis.repository;

import com.kopec.wojciech.engineers_thesis.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "amenities", collectionResourceRel = "amenities")
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
}
