package com.company.complaintapp.controller;

import com.company.complaintapp.entity.Complaint;
import com.company.complaintapp.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    public ComplaintController(ComplaintService myComplaintServce) {
        complaintService = myComplaintServce;
    }

    @GetMapping("/")
    public String showWelcome() {
        return "list-complaints";
    }

    @GetMapping("/list")
    public String getComplaints(Model model, HttpServletRequest httpServletRequest) {

        List<Complaint> complaints = null;
        String username = httpServletRequest.getUserPrincipal().getName();
        boolean isAdmin = httpServletRequest.isUserInRole("MANAGER");
        if(isAdmin) {
            complaints = complaintService.findAll();
        } else {
            complaints = complaintService.findByUsername(username);
        }
        model.addAttribute("complaints", complaints);
        return "list-complaints";
    }

    @GetMapping("/showAddForm")
    public String showAddForm(Model model) {
        Complaint complaint = new Complaint();
        model.addAttribute("complaint", complaint);
        return "add-form";
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam("id") int id, Model model) {
        Complaint complaint = complaintService.findById(id);

        if(complaint == null ) {
            System.out.println("COMPLAINT IS NULL");
            model.addAttribute("id", id);
            return "error-page";
        }
        model.addAttribute("complaint", complaint);
        return "update-form";
    }

    @PostMapping("/saveComplaint")
    public String saveComplaint(@ModelAttribute("complaints") Complaint complaint) {
        complaintService.save(complaint);
        return "redirect:/complaints/list";
    }

    @GetMapping("/delete")
    public String deleteComplaint(@RequestParam("id") int id) {
        complaintService.deleteById(id);
        return "redirect:/complaints/list";
    }

    @PostMapping("/processFormAfterUpdate")
    public String processFormAfterUpdate(@Valid @ModelAttribute("complaint") Complaint complaint, BindingResult bindingResult)  {

       if(bindingResult.hasErrors()) {
           return "update-form";
       } else {
           complaintService.save(complaint);
       }
       return "redirect:/complaints/list";
    }

    @PostMapping("/processFormAfterAdd")
    public String processFormAfterAdd(@Valid @ModelAttribute("complaint") Complaint complaint, BindingResult bindingResult, HttpServletRequest httpServletRequest)  {
        String username = httpServletRequest.getUserPrincipal().getName();
        System.out.println(bindingResult);
        if(bindingResult.hasErrors()) {
            System.out.println("Form has errors");
            return "add-form";
        } else {
            complaint.setUsername(username);
            complaint.setStatus("Unresolved");
            complaintService.save(complaint);
        }
        return "redirect:/complaints/list";
    }



}
