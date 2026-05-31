package com.internship_code.travelmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.internship_code.travelmanagement.entity.TourPackage;

public interface PackageRepository
extends JpaRepository<TourPackage, Long>{

    @Query("""

    SELECT DISTINCT p
    FROM TourPackage p

    LEFT JOIN PackageAttraction pa
    ON p.packageId = pa.packageId

    LEFT JOIN Attraction a
    ON pa.attractionId = a.attractionId

    WHERE

    LOWER(p.packageName)
    LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(p.destination)
    LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(a.attractionName)
    LIKE LOWER(CONCAT('%', :keyword, '%'))

    """)

    List<TourPackage> searchPackages(
            @Param("keyword")
            String keyword);

}