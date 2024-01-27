package com.minhdunk.research.entity;

import com.minhdunk.research.utils.PostOrientation;
import com.minhdunk.research.utils.PostType;
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
        name = "POSTS"
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authorId;
    private String title;
    private String caption;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", referencedColumnName = "id", nullable = false)
    private Assignment assignment;
    @Enumerated(EnumType.STRING)
    private PostOrientation orientation;
    @Enumerated(EnumType.STRING)
    private PostType type;
    private LocalDateTime postTime;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "MEDIAS_POSTS",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "media_id") }
    )
    private Set<Media> medias = new HashSet<>();
}
