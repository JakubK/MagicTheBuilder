package magicthebuilder.cardservice.model;

import org.springframework.data.domain.Sort;

import java.util.List;

public class CardsRequestModelBuilder {
    private List<String> ids;
    private String phrase;
    private List<String> colors;
    private List<String> types;
    private List<String> sets;
    private List<String> formats;
    private Sort sortBy = Sort.unsorted();
    private Integer page = 0;
    private Integer size = 100;

    public CardsRequestModelBuilder setIds(List<String> ids) {
        if (ids != null && ids.isEmpty())
            ids = null;

        this.ids = ids;
        return this;
    }

    public CardsRequestModelBuilder setPhrase(String phrase) {
        if (phrase != null && phrase.isEmpty())
            phrase = null;

        this.phrase = phrase;
        return this;
    }

    public CardsRequestModelBuilder setColors(List<String> colors) {
        if (colors != null && colors.isEmpty())
            colors = null;

        this.colors = colors;
        return this;
    }

    public CardsRequestModelBuilder setTypes(List<String> types) {
        if (types != null && types.isEmpty())
            types = null;

        this.types = types;
        return this;
    }

    public CardsRequestModelBuilder setSets(List<String> sets) {
        if (sets != null && sets.isEmpty())
            sets = null;

        this.sets = sets;
        return this;
    }

    public CardsRequestModelBuilder setFormats(List<String> formats) {
        if (formats != null && formats.isEmpty())
            formats = null;

        this.formats = formats;
        return this;
    }

    public CardsRequestModelBuilder setSortBy(String sortBy) {
        this.sortBy = sortBy != null && !sortBy.isEmpty() ? Sort.by(sortBy) : Sort.unsorted();
        return this;
    }

    public CardsRequestModelBuilder setPage(Integer page) {
        if (page == null || page < 0)
            return this;
        this.page = page;
        return this;
    }

    public CardsRequestModelBuilder setSize(Integer size) {
        if (size == null || size < 1 || size > 100)
            return this;
        this.size = size;
        return this;
    }

    public CardsRequestModel build() {
        return new CardsRequestModel(ids, phrase, colors, types, sets, formats, sortBy, page, size);
    }
}
