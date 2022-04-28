package application.data.articles;

import application.data.loadableResources.LoadableResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {
    @Column(name = "id" , unique = true , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
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
}