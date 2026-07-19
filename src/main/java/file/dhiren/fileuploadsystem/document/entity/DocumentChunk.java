package file.dhiren.fileuploadsystem.document.entity;

import file.dhiren.fileuploadsystem.ai.embedding.entity.Embedding;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document_chunk")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "chunk_index",  nullable = false)
    private Integer  chunkIndex;

    @Column(name = "chunk_text" , columnDefinition = "TEXT" , nullable = false)
    private String chunkText;

    @Column(name = "token_count",  nullable = false)
    private Integer tokenCount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(
            mappedBy = "chunkId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Embedding> embeddings  = new ArrayList<>();


}
