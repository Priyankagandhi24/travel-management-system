package com.internship_code.travelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.internship_code.travelmanagement.entity.Booking;
import com.internship_code.travelmanagement.entity.TourPackage;
import com.internship_code.travelmanagement.entity.User;
import com.internship_code.travelmanagement.repository.BookingRepository;
import com.internship_code.travelmanagement.repository.PackageRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {

    @Autowired
    BookingRepository bookingRepo;

    @Autowired
    PackageRepository packageRepo;


    @GetMapping("/book/{id}")
    public String bookingPage(
            @PathVariable Long id,
            Model model,
            HttpSession session)
    {

        TourPackage p =
                packageRepo.findById(id)
                .orElse(null);

        User u =
                (User) session.getAttribute(
                        "loggedUser");

        if(u == null)
        {
            return "redirect:/login";
        }

        model.addAttribute(
                "package",
                p);

        model.addAttribute(
                "user",
                u);

        return "bookingForm";
    }


    @PostMapping("/saveBooking")
    public String saveBooking(
            Booking b)
    {

        TourPackage p =
                packageRepo.findById(
                        b.getPackageId())
                .orElse(null);

        if(p == null)
        {
            return "redirect:/packages";
        }

        if(p.getAvailableSeats()
           < b.getNumberOfPeople())
        {
            return "redirect:/package/"
                    + b.getPackageId()
                    + "?noseats";
        }

        p.setAvailableSeats(

                p.getAvailableSeats()
                - b.getNumberOfPeople()

        );

        packageRepo.save(p);

        b.setStatus("Pending");

        bookingRepo.save(b);

        return "redirect:/myBookings";
    }


    @GetMapping("/myBookings")
    public String myBookings(
            HttpSession session,
            Model model)
    {

        User u =
            (User) session.getAttribute(
                    "loggedUser");

        if(u == null)
        {
            return "redirect:/login";
        }

        model.addAttribute(
                "bookings",
                bookingRepo.findByUserId(
                        u.getUserId()));

        return "myBookings";
    }


    @GetMapping("/cancelBooking/{id}")
    public String cancelBooking(
            @PathVariable Long id)
    {

        Booking b =
                bookingRepo.findById(id)
                .orElse(null);

        if(b != null)
        {

            TourPackage p =
                    packageRepo.findById(
                            b.getPackageId())
                    .orElse(null);

            if(p != null)
            {
                p.setAvailableSeats(

                        p.getAvailableSeats()
                        + b.getNumberOfPeople()

                );

                packageRepo.save(p);
            }

            b.setStatus(
                    "Cancelled");

            bookingRepo.save(b);
        }

        return "redirect:/myBookings";
    }


    @GetMapping("/admin/bookings")
    public String viewBookings(
            Model model,
            HttpSession session)
    {

        String role =
        (String) session.getAttribute("role");

        if(role == null ||
           !role.equals("ADMIN"))
        {
            return "redirect:/";
        }

        model.addAttribute(
                "bookings",
                bookingRepo.findAll());

        return "bookings";
    }

}