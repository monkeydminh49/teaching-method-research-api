package com.minhdunk.research.entity;

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
        name = "Counselling"
)
public class Counselling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String title;
    @Column(columnDefinition="TEXT")
    private String content;
    private LocalDateTime createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;
}
