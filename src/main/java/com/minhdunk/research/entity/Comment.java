package com.minhdunk.research.entity;

import com.minhdunk.research.utils.CommentType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "COMMENTS"
)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    private Long destinationId;
    @Column(columnDefinition="TEXT")
    private String content;
    private LocalDateTime postTime;
    @Enumerated(EnumType.STRING)
    private CommentType type;
}
