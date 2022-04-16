package application.data.loadableResources;

import application.data.articles.Article;
import application.data.loadableResources.models.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "resources")
@AllArgsConstructor
@NoArgsConstructor
public class LoadableResource {
    @Id
    @Column(name = "filename" , nullable = false , unique = true)
    private String filename;

    @Column(name = "filetype" , nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType filetype;

    @Column(name = "size" , nullable = false)
    private Long size;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_name")
    private Article article;
}