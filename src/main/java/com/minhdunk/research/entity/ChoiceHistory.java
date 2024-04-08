package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
@Entity
@Table(
        name = "CHOICE_HISTORY"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ChoiceHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(columnDefinition="text")
    private String content;
    private Boolean isAnswer;
    private Boolean isPicked;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="question_id", referencedColumnName = "id", nullable=false)
    private QuestionHistory question;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="test_id", referencedColumnName = "id", nullable=false)
    private TestHistory test;
}