package com.kopec.wojciech.engineers_thesis.repository;

import com.kopec.wojciech.engineers_thesis.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface LocalizationRepository extends JpaRepository<Localization, Integer> {
}
