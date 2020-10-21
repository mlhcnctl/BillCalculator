package com.BillCalculator.repository;

import com.BillCalculator.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, String> {
}
