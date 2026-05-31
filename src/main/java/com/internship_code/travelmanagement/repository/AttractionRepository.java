package com.internship_code.travelmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship_code.travelmanagement.entity.Attraction;

public interface AttractionRepository
extends JpaRepository<Attraction, Long>{

    Attraction findByAttractionId(
            Long attractionId);

    List<Attraction>
    findByAttractionNameContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String attractionName,
            String location);

}