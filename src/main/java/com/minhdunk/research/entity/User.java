package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
//    @Column(unique = true)
//    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDate dateOfBirth;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "students")
    @JsonIgnore
    private Set<Classroom> classes = new HashSet<>();
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
