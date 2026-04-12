package com.ec7205.event_hub.event_service_api.repository;

import com.ec7205.event_hub.event_service_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndIsActiveTrue(Long id);

    boolean existsByNameIgnoreCaseAndIsActiveTrue(String name);

    boolean existsByNameIgnoreCaseAndIsActiveTrueAndIdNot(String name, Long id);

    List<Category> findAllByIsActiveTrueOrderByNameAsc();
}
