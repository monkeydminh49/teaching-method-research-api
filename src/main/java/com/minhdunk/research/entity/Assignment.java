package com.minhdunk.research.entity;

import com.minhdunk.research.utils.AssignmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    private Classroom classroom;
    @Column(columnDefinition="TEXT")
    private String title;
    @Column(columnDefinition="TEXT")
    private String content;
    private LocalDateTime assignedDateTime;
    private LocalDateTime dueDateTime;
    @Column(columnDefinition = "boolean default false")
    private Boolean isForGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_document_id", referencedColumnName = "id")
    private Document relatedDocument;
    @Enumerated(EnumType.STRING)
    private AssignmentType type = AssignmentType.OTHER;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_test_id", referencedColumnName = "id")
    private Test relatedTest;


}
