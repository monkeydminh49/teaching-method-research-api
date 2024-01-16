package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        name = "CLASSES"
)
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private User teacher;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "CLASSES_STUDENTS",
            joinColumns = { @JoinColumn(name = "class_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") }
    )
    private Set<User> students = new HashSet<>();
    private int numberOfStudents;

    public void addStudent(User student) {
        this.students.add(student);
        student.getClasses().add(this);
    }

    public void removeStudent(User student) {
        this.students.remove(student);
    }

    public int getNumberOfStudents() {
        if (this.students == null) {
            this.students = new HashSet<>();
            this.numberOfStudents = 0;
        } else {
            this.numberOfStudents = this.students.size();
        }
        return this.numberOfStudents;
    }


}
