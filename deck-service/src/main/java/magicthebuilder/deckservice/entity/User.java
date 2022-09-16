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
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    public Long id;

}
