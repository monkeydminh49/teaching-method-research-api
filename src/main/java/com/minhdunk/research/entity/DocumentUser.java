package com.minhdunk.research.entity;

import com.minhdunk.research.utils.DocumentUserKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FAVOURITE")
public class DocumentUser {

    @EmbeddedId
    private DocumentUserKey id;

    @ManyToOne
    @MapsId("documentId")
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime likedAt;
}
