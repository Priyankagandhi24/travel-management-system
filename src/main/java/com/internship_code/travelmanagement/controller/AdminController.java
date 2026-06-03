package com.internship_code.travelmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.internship_code.travelmanagement.entity.Attraction;
import com.internship_code.travelmanagement.entity.PackageAttraction;
import com.internship_code.travelmanagement.entity.TourPackage;
import com.internship_code.travelmanagement.repository.AttractionRepository;
import com.internship_code.travelmanagement.repository.BookingRepository;
import com.internship_code.travelmanagement.repository.PackageAttractionRepository;
import com.internship_code.travelmanagement.repository.PackageRepository;
import com.internship_code.travelmanagement.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    PackageRepository repo;

    @Autowired
    AttractionRepository attractionRepo;

    @Autowired
    PackageAttractionRepository packageAttractionRepo;

    @Autowired
    BookingRepository bookingRepo;

    @Autowired
    UserRepository userRepo;


    @GetMapping("/admin/add")
    public String addPackagePage(
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
                "attractions",
                attractionRepo.findAll());

        return "addPackage";
    }


    @PostMapping("/admin/save")
    public String savePackage(
            TourPackage p,
            Long[] selectedAttractions,
            HttpSession session)
    {

        String role =
        (String) session.getAttribute("role");

        if(role == null ||
           !role.equals("ADMIN"))
        {
            return "redirect:/";
        }

        if(p.getStartDateTime().isBefore(
        java.time.LocalDateTime.now()))
{
    return "redirect:/admin/add?invalidDate";
}

        TourPackage savedPackage =
                repo.save(p);

        if(selectedAttractions != null)
        {
            for(Long attractionId : selectedAttractions)
            {
                PackageAttraction pa =
                        new PackageAttraction();

                pa.setPackageId(
                        savedPackage.getPackageId());

                pa.setAttractionId(
                        attractionId);

                packageAttractionRepo.save(pa);
            }
        }

        return "redirect:/packages";
    }


    @GetMapping("/admin/addAttraction")
    public String attractionPage(
            HttpSession session)
    {

        String role =
        (String) session.getAttribute("role");

        if(role == null ||
           !role.equals("ADMIN"))
        {
            return "redirect:/";
        }

        return "addAttraction";
    }


    @PostMapping("/admin/saveAttraction")
    public String saveAttraction(
            Attraction a,
            HttpSession session)
    {

        String role =
        (String) session.getAttribute("role");

        if(role == null ||
           !role.equals("ADMIN"))
        {
            return "redirect:/";
        }

        attractionRepo.save(a);

        return "redirect:/attractions?saved";    
}


    @GetMapping("/attractions")
public String viewAttractions(
        Model model,
        String keyword)
{

    if(keyword != null &&
       !keyword.isEmpty())
    {
        model.addAttribute(
                "attractions",

                attractionRepo
                .findByAttractionNameContainingIgnoreCaseOrLocationContainingIgnoreCase(
                        keyword,
                        keyword)
        );
    }
    else
    {
        model.addAttribute(
                "attractions",
                attractionRepo.findAll());
    }

    model.addAttribute(
            "keyword",
            keyword);

    return "attractions";
}

    @GetMapping("/admin/dashboard")
    public String dashboard(
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
                "packageCount",
                repo.count());

        model.addAttribute(
                "attractionCount",
                attractionRepo.count());

        model.addAttribute(
                "userCount",
                userRepo.count());

        model.addAttribute(
                "bookingCount",
                bookingRepo.count());

        model.addAttribute(
                "revenue",
                bookingRepo.totalRevenue());

        return "dashboard";
    }

@GetMapping("/admin/users")
public String viewUsers(
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
            "users",
            userRepo.findAll());

    return "users";
}

@GetMapping("/admin/editAttraction/{id}")
public String editAttractionPage(
        @PathVariable Long id,
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

    Attraction a =
            attractionRepo.findById(id)
            .orElse(null);

    model.addAttribute(
            "attraction",
            a);

    return "editAttraction";
}

@PostMapping("/admin/updateAttraction")
public String updateAttraction(
        Attraction a,
        HttpSession session)
{

    String role =
    (String) session.getAttribute("role");

    if(role == null ||
       !role.equals("ADMIN"))
    {
        return "redirect:/";
    }

    attractionRepo.save(a);

    return "redirect:/attractions";
}

@GetMapping("/admin/deleteAttraction/{id}")
public String deleteAttraction(
        @PathVariable Long id,
        HttpSession session)
{

    String role =
    (String) session.getAttribute("role");

    if(role == null ||
       !role.equals("ADMIN"))
    {
        return "redirect:/";
    }

    attractionRepo.deleteById(id);

    return "redirect:/attractions";
}

}