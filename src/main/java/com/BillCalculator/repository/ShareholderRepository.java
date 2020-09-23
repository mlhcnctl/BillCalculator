package com.BillCalculator.repository;

import com.BillCalculator.entity.ShareholderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareholderRepository extends JpaRepository<ShareholderEntity, Long> {


}
