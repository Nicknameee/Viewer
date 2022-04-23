package application.web.responses.manager;

import application.data.articles.Article;
import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ArticleResponse extends ApplicationWebResponse {
    private Article article;
}