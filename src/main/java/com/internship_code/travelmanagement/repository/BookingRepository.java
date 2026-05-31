package com.internship_code.travelmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.internship_code.travelmanagement.entity.Booking;

public interface BookingRepository
extends JpaRepository<Booking,Long>{

    List<Booking> findByUserId(
            Long userId);

    @Query(
    "SELECT SUM(b.totalAmount) FROM Booking b WHERE b.status='Pending'"
    )
    Double totalRevenue();

}