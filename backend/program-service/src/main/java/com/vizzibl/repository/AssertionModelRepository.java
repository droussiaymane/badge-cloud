package com.vizzibl.repository;


import com.vizzibl.entity.AssertionModel;
import com.vizzibl.entity.OpenBadgeMappingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssertionModelRepository extends JpaRepository<AssertionModel, Long> {

    @Query(value = "select  assert.openBadgeMappingModel from AssertionModel assert where  assert.assertionKey = :key")
    OpenBadgeMappingModel findByAssertionKey(String key);
}
