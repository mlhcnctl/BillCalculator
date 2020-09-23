package com.BillCalculator.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Getter
@Table(name = "confirmation_mail")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ConfirmationMailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name="user_id")
    private UserEntity userEntity;

    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;

}
