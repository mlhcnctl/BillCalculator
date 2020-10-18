package com.BillCalculator.repository;

import com.BillCalculator.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findFirstByOrderByCreatedDateDesc();

    UserEntity findByUserName(String username);

}
