package com.BillCalculator.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Table(name = "log")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log_id", nullable = false)
    private String logId;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "class_name")
    private String className;

    @Column(name = "log_code")
    private int logCode;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private Date createdDate;
}
