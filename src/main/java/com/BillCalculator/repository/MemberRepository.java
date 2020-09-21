package com.BillCalculator.repository;

import com.BillCalculator.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MemberEntity, Long> {



}
