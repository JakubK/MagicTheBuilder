package magicthebuilder.deckservice.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {
    public String name;
    public UUID uuid;
    private String email;
    //private password
    public List<Deck> decks;


}
