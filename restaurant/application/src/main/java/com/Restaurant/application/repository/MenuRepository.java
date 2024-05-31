package com.Restaurant.application.repository;


import com.Restaurant.application.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Integer> {

    Optional<MenuItem> findByName(String name);

}

