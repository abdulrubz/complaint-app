package com.company.complaintapp.repository;

import com.company.complaintapp.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findByUsernameOrderById(String username);
    
}
