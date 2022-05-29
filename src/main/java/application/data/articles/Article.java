package application.data.articles;

import application.data.articles.attributes.Tag;
import application.data.loadableResources.LoadableResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "id" , unique = true , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , unique = true , nullable = false)
    private String name;

    @Column(name = "text" , nullable = false)
    private String text;

    @Column(name = "secret" , nullable = false , updatable = false)
    private String secret;

    @JsonIgnoreProperties("article")
    @JsonProperty("resources")
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "article" , orphanRemoval = true , cascade = CascadeType.ALL)
    private List<LoadableResource> resources;

    @JsonIgnoreProperties("article")
    @JsonProperty("tags")
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "article" , cascade = CascadeType.ALL)
    private List<Tag> tags;

    @Column(name = "folder_name")
    private String folderName;

    @Column(name = "folder_id")
    private String folderId;
}