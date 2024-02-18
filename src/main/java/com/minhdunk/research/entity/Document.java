package com.minhdunk.research.entity;

import com.minhdunk.research.utils.DocumentType;
import com.minhdunk.research.utils.DocumentUserKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Column(columnDefinition="text")
    private String title;
    @Column(columnDefinition="text")
    private String veryFirstText;
    private LocalDateTime postTime;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Media audio;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Media thumbnail;
    private String notionPageId;
    private String topic;

    @OneToMany(mappedBy = "document")
    private Set<DocumentUser> likedByUsers = new HashSet<>();
    private Integer numberOfLikes;



}
