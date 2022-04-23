package application.data.articles;

import application.data.loadableResources.LoadableResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

// @TODO: Add tags functionality

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "name" , unique = true , nullable = false)
    private String name;

    @Column(name = "text")
    private String text;

    @JsonIgnoreProperties("article")
    @JsonProperty("resources")
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "article" , orphanRemoval = true , cascade = CascadeType.ALL)
    private List<LoadableResource> resources;
}