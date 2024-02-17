package com.minhdunk.research.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUserKey implements Serializable{
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "user_id")
    private Long userId;


}
