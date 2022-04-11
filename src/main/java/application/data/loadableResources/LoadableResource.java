package application.data.loadableResources;

import application.data.loadableResources.models.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "resources")
@AllArgsConstructor
@NoArgsConstructor
public class LoadableResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id" , nullable = false)
    private Integer articleId;

    @Column(name = "filename" , nullable = false , unique = true)
    private String filename;

    @Column(name = "filetype" , nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType filetype;

    @Column(name = "size" , nullable = false)
    private Long size;
}