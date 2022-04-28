package application.data.loadableResources;

import application.data.articles.Article;
import application.data.loadableResources.models.ResourceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "id" , unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "filename" , nullable = false , unique = true)
    private String filename;

    @Column(name = "filetype" , nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType filetype;

    @Column(name = "size" , nullable = false)
    private Long size;

    @JsonIgnoreProperties("resources")
    @JsonProperty("article")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_name")
    private Article article;
}