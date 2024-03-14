package com.minhdunk.research.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.minhdunk.research.utils.TestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(
        name = "TEST"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Test {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(columnDefinition="text")
    private String title;
    @OneToMany(mappedBy = "test", cascade = { CascadeType.REMOVE})
    private List<Question> questions;
    @ManyToOne(fetch = FetchType.LAZY ,optional = true)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = true)
    private User author;
    @Enumerated(EnumType.STRING)
    private TestType type;
    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
    private Document document;
}
