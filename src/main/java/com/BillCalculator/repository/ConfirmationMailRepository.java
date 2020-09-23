package com.BillCalculator.repository;

import com.BillCalculator.entity.ConfirmationMailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationMailRepository extends JpaRepository<ConfirmationMailEntity, Long> {

    Optional<ConfirmationMailEntity> findConfirmationMailEntitiesByConfirmationToken(String token);

}
