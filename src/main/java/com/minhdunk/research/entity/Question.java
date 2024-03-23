package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.minhdunk.research.utils.QuestionType;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.hibernate.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "QUESTION"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Question {
    public Question(){
        this.type = QuestionType.SINGLE_CHOICE;
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(columnDefinition="text")
    private String question;
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name="test_id", referencedColumnName = "id", nullable=false)
    private Test test;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            mappedBy = "question",
            cascade = { CascadeType.REMOVE }
    )
    private List<Choice> choices;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            mappedBy = "question",
            cascade = { CascadeType.REMOVE }
    )
    @SQLRestriction(
            "type = 'HINT_REGULAR'"
    )
    private List<Hint> hints;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            mappedBy = "question",
            cascade = { CascadeType.REMOVE }
    )
    @SQLRestriction(
            "type = 'HINT_ANSWER'"
    )
    private List<Hint> answerHints;
    public QuestionType getType() {
        return type != null ? type : QuestionType.SINGLE_CHOICE;
    }
}