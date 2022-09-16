package magicthebuilder.deckservice.entity;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;

import javax.persistence.*;
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
@Table(name="collections")
public class Collection {

    @Id
    public Long userId;

    public CollectionAccessLevelEnum accessLevel;

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Card> cards;
    public Date lastUpdateDate;


    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = new Date();
    }

    public Collection(Long id, CollectionAccessLevelEnum accessLevel, List<Card> cards) {
        this.userId = id;
        this.accessLevel = accessLevel;
        this.cards = cards;
    }

}
