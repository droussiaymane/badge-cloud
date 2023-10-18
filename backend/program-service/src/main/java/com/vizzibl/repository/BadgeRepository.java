package com.vizzibl.repository;


import com.vizzibl.entity.BadgesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<BadgesModel, Long> {

    Page<BadgesModel> findAllByTenantId(String tenantId, Pageable pageable);

    @Query(value = "select badge from BadgesModel badge where badge.tenantId = :tenantId and badge.id not in " +
            " (select pb.badgesModel.id from ProgramBadge pb where pb.badgesModel.tenantId = :tenantId)")
    List<BadgesModel> findAllUnmappedEntries(String tenantId);

    @Query(value = "SELECT badge FROM BadgesModel badge WHERE badge.tenantId = :tenantId AND badge.badgeName LIKE CONCAT('%', :badgeName, '%')")
    List<BadgesModel> findByTenantIdAndBadgeName(String tenantId, String badgeName);


    Optional<BadgesModel> findByTenantIdAndId(String tenantId, Long id);
}
