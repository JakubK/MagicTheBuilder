package magicthebuilder.deckservice.entity;


import lombok.*;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "decks")
public class Deck {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID uuid;
    private String name;

    @ManyToOne
    private User owner;
    private GameMode gameMode;
    @Enumerated(EnumType.STRING)
    private DeckAccessLevelEnum accessLevel;
    @ManyToMany
    private List<Card> cards;
    @ManyToMany
    private List<Card> sideboard;
    private Date creationDate;
    private Date lastUpdateDate;
    @ManyToOne
    private Card commander = null;
    private boolean isValid = false;

    public Deck(String name, GameMode gameMode, User owner, DeckAccessLevelEnum accessLevel, List<Card> cards, List<Card> sideboard) {
        setName(name);
        setGameMode(gameMode);
        setOwner(owner);
        setAccessLevel(accessLevel);
        setCards(cards);
        setSideboard(sideboard);
    }


    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = new Date();
    }

}


