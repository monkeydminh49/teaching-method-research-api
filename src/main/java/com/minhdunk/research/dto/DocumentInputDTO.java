package com.minhdunk.research.dto;


import com.minhdunk.research.utils.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInputDTO {
    private String title;
    private String veryFirstText;
    private DocumentType type;
    private String notionPageId;
    private String topic;
}
