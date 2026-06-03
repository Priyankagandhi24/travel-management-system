package com.internship_code.travelmanagement.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.internship_code.travelmanagement.repository.PackageAttractionRepository;
import com.internship_code.travelmanagement.repository.PackageRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PackageController {

    @Autowired
    PackageRepository packageRepo;

    @Autowired
    AttractionRepository attractionRepo;

    @Autowired
    PackageAttractionRepository packageAttractionRepo;


    @GetMapping("/")
    public String home(
            Model model,
            HttpSession session)
    {

        model.addAttribute(
                "packages",
                packageRepo.findAll());

        model.addAttribute(
                "attractions",
                attractionRepo.findAll());

        model.addAttribute(
                "user",
                session.getAttribute(
                        "loggedUser"));

        return "index";
    }

@GetMapping("/package/{id}")
public String packageDetails(
        @PathVariable Long id,
        Model model)
{
    TourPackage p =
            packageRepo.findById(id)
            .orElse(null);

    model.addAttribute("package", p);

    List<PackageAttraction> links =
            packageAttractionRepo.findByPackageId(id);

    List<Attraction> attractions =
            new ArrayList<>();

    for(PackageAttraction pa : links)
    {
        Attraction a =
                attractionRepo.findById(
                        pa.getAttractionId())
                .orElse(null);

        if(a != null)
        {
            attractions.add(a);
        }
    }

    model.addAttribute(
            "attractions",
            attractions);

    return "packageDetails";
}

    @GetMapping("/packages")
    public String showPackages(
            Model model,
            String keyword)
    {

        List<TourPackage> packages;

        if(keyword != null &&
           !keyword.isEmpty())
        {
            packages =
                    packageRepo.searchPackages(
                            keyword);
        }
        else
        {
            packages =
                    packageRepo.findAll();
        }

        for(TourPackage p : packages)
        {
            List<PackageAttraction> links =
                    packageAttractionRepo
                    .findByPackageId(
                            p.getPackageId());

            List<String> attractionNames =
                    new ArrayList<>();

            for(PackageAttraction pa : links)
            {
                Attraction a =
                        attractionRepo
                        .findByAttractionId(
                                pa.getAttractionId());

                if(a != null)
                {
                    attractionNames.add(
                            a.getAttractionName());
                }
            }

            p.setAttractions(
                    attractionNames);
        }

        model.addAttribute(
                "packages",
                packages);

        model.addAttribute(
                "keyword",
                keyword);

        return "packages";
    }


    @GetMapping("/attraction/{id}")
public String attractionDetails(
        @PathVariable Long id,
        Long packageId,
        Model model)
{

    Attraction attraction =
            attractionRepo
            .findById(id)
            .orElse(null);

    model.addAttribute(
            "attraction",
            attraction);

    model.addAttribute(
            "packageId",
            packageId);

    return "attractionDetails";
}

    @PostMapping("/packages/delete/{id}")
public String deletePackage(
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

    packageRepo.deleteById(id);

    return "redirect:/packages";
}

}