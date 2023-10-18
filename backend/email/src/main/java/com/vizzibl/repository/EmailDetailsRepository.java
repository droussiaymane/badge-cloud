package com.vizzibl.repository;

import com.vizzibl.entity.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailDetailsRepository extends JpaRepository<EmailDetails, Long> {

    List<EmailDetails> findByStatusAndType(String pending, String typeBadge);
}
