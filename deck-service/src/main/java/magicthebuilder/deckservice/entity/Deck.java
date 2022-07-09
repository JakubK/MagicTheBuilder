package magicthebuilder.deckservice.entity;


import java.util.*;

import lombok.*;

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
    public UUID uuid;
    public String name;
    public Boolean isPrivate;
    public String gameMode;
    @OneToMany(fetch = FetchType.EAGER)
    public List<Card> cards;

}


