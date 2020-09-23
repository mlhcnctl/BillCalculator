package com.BillCalculator.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Table(name = "users")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fullName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "mail_confirmed")
    private boolean confirmed;

    //@Column(name = "user_role")
    //@Builder.Default
    //private UserRoleEnum userRole = UserRoleEnum.USER;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private Date lastModifiedDate;

}
