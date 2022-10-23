package magicTheBuilder.userservice.entity;


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
@Table(name = "users")
public class User {
    @TableGenerator(name="UserIdGenerator", initialValue = 1000000)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "UserIdGenerator")
    private Long id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private boolean allowDataProcessing;
    private boolean allowNotifications;
}
