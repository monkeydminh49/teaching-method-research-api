package com.minhdunk.research.entity;

import com.minhdunk.research.utils.DocumentType;
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
    private String title;
    private String veryFirstText;
    private LocalDateTime postTime;
    @Enumerated(EnumType.STRING)
    private DocumentType type;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Media audio;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Media thumbnail;
    private String notionPageId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "FAVOURITE",
            joinColumns = { @JoinColumn(name = "document_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likedByUsers = new HashSet<>();
    private Integer numberOfLikes;
    public void addLikedUser(User user) {
        if(likedByUsers == null) {
            likedByUsers = new HashSet<>();
            numberOfLikes = 0;
        }

        this.likedByUsers.add(user);
        this.numberOfLikes = this.likedByUsers.size();
        user.addFavouriteDocument(this);
    }

    public void removeLikedUser(User user) {
        this.likedByUsers.remove(user);
        user.removeFavouriteDocument(this);
        this.numberOfLikes = this.likedByUsers.size();
    }



}
