package magicthebuilder.validationservice.dto;

import lombok.Getter;
import lombok.Setter;

import magicthebuilder.validationservice.entity.Format;

import java.util.*;

@Setter
@Getter
public class DeckLegalityCheckDto {
    private String format;
    private List<MultipleCardDto> deck;
    private List<MultipleCardDto> sideBoard;
    private String commander=null;
}
