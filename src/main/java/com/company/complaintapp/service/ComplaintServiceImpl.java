package com.company.complaintapp.service;

import com.company.complaintapp.entity.Complaint;
import com.company.complaintapp.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService{

    private ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository myComplaintRepo) {
        complaintRepository = myComplaintRepo;
    }

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    public List<Complaint> findByUsername(String username) {
        return complaintRepository.findByUsernameOrderById(username);
    }

    @Override
    public Complaint findById(int id) {
        Optional<Complaint> result = complaintRepository.findById(id);
        Complaint complaint = null;
        if(result.isPresent()) {
            complaint = result.get();
        }
        return complaint;
    }

    @Override
    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }

    @Override
    public void deleteById(int id) {
        complaintRepository.deleteById(id);
    }
}
