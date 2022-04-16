package application.data.articles;

import application.data.loadableResources.LoadableResource;
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

    @OneToMany(fetch = FetchType.EAGER , mappedBy = "article" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<LoadableResource> resources;
}