package org.deslre.desk.repository;

import org.deslre.desk.entity.es.ArticleES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName: ArticleRepository
 * Description: 文章ESRepository
 * Author: Deslrey
 * Date: 2025-06-08 14:32
 * Version: 1.0
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<ArticleES, Integer> {
}

