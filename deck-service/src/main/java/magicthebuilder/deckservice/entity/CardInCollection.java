package magicthebuilder.deckservice.entity;


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
@Table(name = "CardInCollection")
public class CardInCollection {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Card card;
    private int amount;

    public CardInCollection(Card card) {
        this.card = card;
        this.amount = 1;
    }

}
