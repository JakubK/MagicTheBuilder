package magicthebuilder.deckservice.entity;


import java.util.*;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.service.DeckService;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="decks")
public class Deck {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID uuid;
    private String name;

    @ManyToOne
    @JoinColumn(name="users_id")
    private User owner;
    private String gameMode;
    private DeckAccessLevelEnum accessLevel;
    @ManyToMany
    private List<Card> cards;
    @ManyToMany
    private List<Card> sideboard;
    private Date creationDate;
    private Date lastUpdateDate;

    @PrePersist
    protected void onCreate(){
        creationDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = new Date();
    }

    public Deck(String name, String gameMode,User owner, DeckAccessLevelEnum accessLevel, List<Card> cards)
    {
        setName(name);
        setGameMode(gameMode);
        setOwner(owner);
        setAccessLevel(accessLevel);
        setCards(cards);
    }
    public Deck(String name, String gameMode,User owner, DeckAccessLevelEnum accessLevel)
    {
        setName(name);
        setGameMode(gameMode);
        setOwner(owner);
        setAccessLevel(accessLevel);
        this.cards = new ArrayList<>();
    }

}


