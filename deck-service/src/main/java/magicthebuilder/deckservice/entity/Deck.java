package magicthebuilder.deckservice.entity;


import java.util.*;

import lombok.*;
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
//    public User user;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    public UUID uuid;
    public String name;
    public String gameMode;
    public DeckAccessLevelEnum accessLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<String> cards;

    public Date creationDate;
    public Date lastUpdateDate;


    @PrePersist
    protected void onCreate(){
        creationDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdateDate = new Date();
    }

    public Deck(String name, String gameMode, DeckAccessLevelEnum accessLevel, List<String> cards)
    {
        setName(name);
        setGameMode(gameMode);
        setAccessLevel(accessLevel);
        setCards(cards);
    }

}


