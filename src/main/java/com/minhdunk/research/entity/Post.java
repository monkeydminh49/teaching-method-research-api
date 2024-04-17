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
    @Column(columnDefinition="TEXT")
    private String title;
    @Column(columnDefinition="text")
    private String caption;
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "FAVOURITE_POSTS",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likedByUsers = new HashSet<>();
    private Integer numberOfLikes;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private User submitter;
    @Column(columnDefinition="text")
    private String teacherComment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counselling_id", referencedColumnName = "id")
    private Counselling counselling;



    public void addLikedUser(User user) {
        if(likedByUsers == null) {
            likedByUsers = new HashSet<>();
            numberOfLikes = 0;
        }

        this.likedByUsers.add(user);
        this.numberOfLikes = this.likedByUsers.size();
        user.addFavouritePost(this);
    }

    public void removeLikedUser(User user) {
        this.likedByUsers.remove(user);
        user.removeFavouritePost(this);
        this.numberOfLikes = this.likedByUsers.size();
    }
}
