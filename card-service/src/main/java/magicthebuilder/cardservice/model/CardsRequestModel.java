package magicthebuilder.cardservice.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.List;

@EqualsAndHashCode
@Getter
public class CardsRequestModel {
    final private List<String> ids;
    final private String phrase;
    final private List<String> colors;
    final private List<String> types;
    final private List<String> sets;
    final private List<String> formats;
    final private Sort sortBy;
    final private Integer page;
    final private Integer size;

    protected CardsRequestModel(List<String> ids, String phrase, List<String> colors, List<String> types, List<String> sets, List<String> formats, Sort sortBy, Integer page, Integer size) {
        this.ids = ids;
        this.phrase = phrase;
        this.colors = colors;
        this.types = types;
        this.sets = sets;
        this.formats = formats;
        this.sortBy = sortBy;
        this.page = page;
        this.size = size;
    }
}



