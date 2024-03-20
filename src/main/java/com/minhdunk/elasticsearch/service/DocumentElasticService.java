package com.minhdunk.elasticsearch.service;

import com.minhdunk.research.document.DocumentElastic;
import com.minhdunk.research.repository.DocumentElasticRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentElasticService {
    private DocumentElasticRepository documentElasticRepository;

    public void saveDocumentElastic(final DocumentElastic documentElastic) {
        documentElasticRepository.save(documentElastic);
    }
}
