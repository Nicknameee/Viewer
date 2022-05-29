package application.data.articles.repository.tags;

import application.data.articles.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImplementation {
    private TagRepository tagRepository;

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<String> getDistinctTags() {
        return tagRepository.getDistinctTags();
    }
    public void deleteTags(Article article) {
        tagRepository.deleteTags(article);
    }
}
