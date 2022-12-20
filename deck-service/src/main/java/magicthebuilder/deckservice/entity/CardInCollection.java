package magicthebuilder.deckservice.entity;


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
@Table(name = "CardInCollection")
public class CardInCollection {

    @Id
    @GeneratedValue(generator = "randomStringFromUUid")
    @GenericGenerator(name = "randomStringFromUUid", strategy = "uuid2")
    private String id;
    @ManyToOne
    private Card card;
    private int amount;

    public CardInCollection(Card card) {
        this.card = card;
        this.amount = 1;
    }

}
