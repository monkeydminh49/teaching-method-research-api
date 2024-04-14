package com.minhdunk.research.entity;

import com.minhdunk.research.utils.HintType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HintHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="text")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "question_history_id", referencedColumnName = "id", nullable = true)
    private QuestionHistory questionHistory;
    @Enumerated(EnumType.STRING)
    private HintType type;
}
