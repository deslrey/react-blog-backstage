package org.deslre.desk.service.impl;

import org.deslre.result.Results;
import org.deslre.utils.StringUtils;
import org.deslre.desk.entity.dto.ArticleSearchDTO;
import org.deslre.desk.entity.es.ArticleES;
import org.deslre.desk.service.ArticleSearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

/**
 * ClassName: ArticleSearchServiceImpl
 * Description: 文章内容搜索
 * Author: Deslrey
 * Date: 2025-06-08 15:16
 * Version: 1.0
 */
//@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {
//    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 根据关键字搜索文章内容，返回带高亮摘要的 DTO 列表
     *
     * @param keyword 搜索关键词
     * @param page    分页页码，从0开始
     * @param size    每页大小
     * @return 文章摘要列表
     */
    public Results<List<ArticleSearchDTO>> searchArticles(String keyword, int page, int size) {
        if (StringUtils.isEmpty(keyword)) {
            return Results.fail("搜索值不能为空");
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("content")
                .preTags("<b>")
                .postTags("</b>")
                .fragmentSize(80)
                .numOfFragments(1);
//                .boundaryScannerType(HighlightBuilder.BoundaryScannerType.SENTENCE);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("content", keyword).analyzer("ik_max_word"))
                .withHighlightBuilder(highlightBuilder)
                .withPageable(PageRequest.of(page, size))
                .withSort(Sort.by(Sort.Order.desc("updateTime")))
                .build();


        SearchHits<ArticleES> searchHits = elasticsearchRestTemplate.search(searchQuery, ArticleES.class);
        List<ArticleSearchDTO> list = searchHits.stream()
                .map(hit -> {
                    ArticleES article = hit.getContent();
                    List<String> highlightSnippets = hit.getHighlightFields().get("content");
                    String snippet = (highlightSnippets != null && !highlightSnippets.isEmpty())
                            ? highlightSnippets.get(0)
                            : (article.getContent().length() > 150 ? article.getContent().substring(0, 150) + "..." : article.getContent());

                    return new ArticleSearchDTO(
                            article.getId(),
                            article.getTitle(),
                            article.getAuthor(),
                            article.getUpdateTime(),
                            snippet
                    );
                }).toList();
        return Results.ok(list);
    }
}
