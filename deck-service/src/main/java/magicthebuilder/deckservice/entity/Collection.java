package magicthebuilder.deckservice.entity;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "collections")
public class Collection {

    @Id
    public Long userId;

    @Enumerated(EnumType.STRING)
    public CollectionAccessLevelEnum accessLevel;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<CardInCollection> cards;
    public Date lastUpdateDate;

    public Collection(Long id, CollectionAccessLevelEnum accessLevel) {
        this.userId = id;
        this.accessLevel = accessLevel;
        this.cards = Collections.emptyList();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = new Date();
    }

}
