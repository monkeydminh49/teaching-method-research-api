package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.minhdunk.research.utils.UserGender;
import com.minhdunk.research.utils.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        name = "USERS"
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})}
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Boolean enabled;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private UserGender gender;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Media avatar;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "students")
    @JsonIgnore
    private Set<Classroom> classes = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "students")
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<DocumentUser> favouriteDocuments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "likedByUsers")
    @JsonIgnore
    private Set<Post> favouritePosts = new HashSet<>();



    public void addFavouriteDocument(Document document) {
//        if (favouriteDocuments == null) {
//            favouriteDocuments = new HashSet<>();
//        }
//        this.favouriteDocuments.add(document);
    }

    public void removeFavouriteDocument(Document document) {
//        this.favouriteDocuments.remove(document);
    }
    public void addFavouritePost(Post post) {
        if (favouritePosts == null) {
            favouritePosts = new HashSet<>();
        }
        this.favouritePosts.add(post);
    }

    public void removeFavouritePost(Post post) {
        this.favouritePosts.remove(post);
    }

    //    @OneToMany(mappedBy = "teacher")
//    @JsonIgnore
//    private Set<Classroom> ownedClasses = new HashSet<>();
//    @OneToMany(mappedBy = "author")
//    @JsonIgnore
//    private Set<Notification> notifications = new HashSet<>();
    public void joinClass(Classroom classroom) {
        this.classes.add(classroom);
    }

    public void leaveClass(Classroom classroom) {
        this.classes.remove(classroom);
    }

//    public void joinOwnedClass(Classroom classroom) {
//        this.ownedClasses.add(classroom);
//    }


}
