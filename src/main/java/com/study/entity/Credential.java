package com.study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "credential")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "series", nullable = false, length = 16, unique = true)
    private String series;

    @Column(name = "token", nullable = false, length = 32)
    private String token;

    @Column(name = "expired_date")
    private Date expiredDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "updated_date")
    private Date updatedDate;
}
