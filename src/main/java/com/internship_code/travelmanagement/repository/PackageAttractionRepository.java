package com.internship_code.travelmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship_code.travelmanagement.entity.PackageAttraction;

public interface PackageAttractionRepository
extends JpaRepository<PackageAttraction, Long>{

    List<PackageAttraction>
    findByPackageId(Long packageId);

}