package com.internship_code.travelmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship_code.travelmanagement.repository.PackageRepository;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }
}