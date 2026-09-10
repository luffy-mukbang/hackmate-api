package com.hackmate.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.hackmate.domain.user.enums.ProviderType;
import com.hackmate.domain.user.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="password")
    private String password;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="nickname", unique = true)
    private String nickname;

    @Column(name="avatar")
    private String avatar;

    @Column(name="bio")
    private String bio;

    @Column(name="provider")
    private ProviderType provider;

    @Column(name="provider_id")
    private String providerId;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
