package com.internship_code.travelmanagement.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TourPackage {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long packageId;

private String packageName;

private String destination;

private Integer duration;

private Double price;

private Integer availableSeats;

@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
private LocalDateTime startDateTime;

private String description;

private String imageUrl;

@Transient
private List<String> attractions;

}