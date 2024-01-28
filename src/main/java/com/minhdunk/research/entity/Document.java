package com.minhdunk.research.entity;

import com.minhdunk.research.utils.DocumentType;
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
        name = "Documents"
)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private User author;
    private String title;
    private String description;
    private LocalDateTime postTime;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Media audio;
    private String notionPageId;
}
