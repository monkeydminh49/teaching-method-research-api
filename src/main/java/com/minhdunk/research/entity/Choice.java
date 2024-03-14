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
        name = "CHOICE"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Choice {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(columnDefinition="text")
    private String content;
    private Boolean isAnswer;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="question_id", referencedColumnName = "id", nullable=false)
    private Question question;

    public String toString() {
        return "Choice(id=" + this.getId() + ", content=" + this.getContent() + ", isCorrect=" + this.getIsAnswer() + ")";
    }


}
