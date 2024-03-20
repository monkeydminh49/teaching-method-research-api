package com.minhdunk.research.repository;

import com.minhdunk.research.document.DocumentElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DocumentElasticRepository extends ElasticsearchRepository<DocumentElastic, String> {
}
