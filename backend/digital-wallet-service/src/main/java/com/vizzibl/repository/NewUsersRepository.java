package com.vizzibl.repository;

import com.vizzibl.entity.NewUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewUsersRepository extends JpaRepository<NewUsers, Long> {

    List<NewUsers> findAllByEmailStatusAndRegistrationStatus(Boolean emailStatus, Boolean registrationStatus);
}
