package com.company.complaintapp.service;

import com.company.complaintapp.entity.Complaint;

import java.util.List;

public interface ComplaintService {
    public List<Complaint> findAll(); //Only for admin
    public List<Complaint> findByUsername(String username); //Only for user
    public Complaint findById(int id);
    public void save(Complaint complaint);
    public void deleteById(int id);
}
