package magicthebuilder.deckservice.ResponseBuilder;

import magicthebuilder.deckservice.dto.CollectionUpdateResponseDto;

public class CollectionUpdateResponseBuilder {
    final String COLLECTION_UPDATED_CORRECTLY_MESSAGE = "Collection updated";
    final String INVALID_CARD_ID_MESSAGE = "Card with the provided id does not exist. Please use correct one";
    final String INVALID_USER_ID_MESSAGE = "User with the provided id does not exist. Please use correct one";

    public CollectionUpdateResponseDto prepareValidCollectionUpdateResponse(Long userId) {
        return CollectionUpdateResponseDto.builder()
                .userId(userId)
                .details(COLLECTION_UPDATED_CORRECTLY_MESSAGE)
                .build();
    }

    public CollectionUpdateResponseDto prepareInvalidCardIdErrorCollectionUpdateResponse(Long userId) {
        return CollectionUpdateResponseDto.builder()
                .userId(userId)
                .details(INVALID_CARD_ID_MESSAGE)
                .build();
    }

    public CollectionUpdateResponseDto prepareInvalidUserIdErrorCollectionUpdateResponse(Long userId) {
        return CollectionUpdateResponseDto.builder()
                .userId(userId)
                .details(INVALID_USER_ID_MESSAGE)
                .build();
    }
}
