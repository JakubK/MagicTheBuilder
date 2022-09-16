package magicthebuilder.deckservice.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CollectionUpdateResponseDto {
    Long userId;
    String details;
}
