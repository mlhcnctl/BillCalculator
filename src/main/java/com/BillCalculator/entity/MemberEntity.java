package com.BillCalculator.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Getter
@Table(name = "member")
@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column
    private boolean active;

}
