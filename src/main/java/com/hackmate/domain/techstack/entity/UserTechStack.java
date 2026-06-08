package com.hackmate.domain.techstack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.hackmate.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tech_stacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserTechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="tech_stack_id", nullable = false)
    private TechStack techStack;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;
}
