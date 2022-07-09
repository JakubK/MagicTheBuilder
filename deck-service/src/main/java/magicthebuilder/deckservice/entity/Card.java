package magicthebuilder.deckservice.entity;


import java.util.*;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="cards")
public class Card {
    @Id
    public int id;

    public String toString()
    {
        return String.valueOf(id);
    }
}
