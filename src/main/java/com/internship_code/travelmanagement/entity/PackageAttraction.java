package com.internship_code.travelmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PackageAttraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageAttractionId;

    private Long packageId;

    private Long attractionId;

    public Long getPackageAttractionId() {
        return packageAttractionId;
    }

    public void setPackageAttractionId(Long packageAttractionId) {
        this.packageAttractionId = packageAttractionId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
    }
}