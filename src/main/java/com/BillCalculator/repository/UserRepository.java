package com.BillCalculator.repository;

import com.BillCalculator.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findFirstByOrderByCreatedDateDesc();

    Optional<UserEntity> findByUserName(String username);

}
