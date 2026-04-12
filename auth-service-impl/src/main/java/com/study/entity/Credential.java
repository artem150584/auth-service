package com.study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credential")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "series", length = 16, unique = true)
    private String series;

    @NotNull
    @Column(name = "token", length = 32)
    private String token;

    @NotNull
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @NotNull
    @Column(name = "active")
    private Boolean active;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
