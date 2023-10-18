package com.vizzibl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class UserCategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String tenantId;

    @JsonIgnore
    @OneToMany(mappedBy = "userCategoryModel")
    private List<UsersModel> usersModel;

    public UserCategoryModel(Long categoryId) {

        this.id = categoryId;
    }

    public UserCategoryModel() {

    }
}

