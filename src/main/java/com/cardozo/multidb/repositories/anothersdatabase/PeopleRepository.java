package com.cardozo.multidb.repositories.anothersdatabase;

import com.cardozo.multidb.models.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Long> {
}
