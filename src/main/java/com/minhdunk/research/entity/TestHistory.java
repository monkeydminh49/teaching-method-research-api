package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.minhdunk.research.utils.TestType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Builder
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "TEST_HISTORY"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TestHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(columnDefinition="text")
    private String title;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "test", cascade = { CascadeType.REMOVE})
    private List<QuestionHistory> questions;
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(name = "submitter_id", referencedColumnName = "id", nullable = false)
    private User submitter;
    @Enumerated(EnumType.STRING)
    private TestType type;
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)

    private Test test;
    private Double totalScore;
    private LocalDateTime submitAt;
//    private Double durationInMinutes;
}
