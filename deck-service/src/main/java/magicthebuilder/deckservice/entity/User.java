package magicthebuilder.deckservice.entity;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="users")
public class User {
    @Id
    public Long id;

    @OneToOne
    private Collection collection;

    public User(Long id, Collection coll) {
        this.id = id;
        this.collection = coll;
    }


}
