package org.deslre.desk.convert;

import org.deslre.commons.entity.ArticleDetail;
import org.deslre.desk.entity.po.Article;
import org.deslre.desk.entity.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ClassName: ArticleConvert
 * Description: 文章实体类转换
 * Author: Deslrey
 * Date: 2025-04-10 9:51
 * Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleVO convertVO(Article article);

    Article convertArticleDetail(ArticleDetail articleDetail);

    List<ArticleVO> convertList(List<Article> articles);

}
