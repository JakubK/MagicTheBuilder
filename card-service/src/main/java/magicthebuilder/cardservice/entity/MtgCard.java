package magicthebuilder.cardservice.entity;

import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.Legality;
import io.magicthegathering.javasdk.resource.Ruling;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document
public class MtgCard {
    public MtgCard(Card card) {
        this.id = card.getId();
        this.layout = card.getLayout();
        this.name = card.getName();
        this.names = card.getNames();
        this.manaCost = card.getManaCost();
        this.cmc = card.getCmc();
        this.colors = card.getColors();
        this.colorIdentity = card.getColorIdentity();
        this.type = card.getType();
        this.supertypes = card.getSupertypes();
        this.types = card.getTypes();
        this.subtypes = card.getSubtypes();
        this.rarity = card.getRarity();
        this.text = card.getText();
        this.originalText = card.getOriginalText();
        this.flavor = card.getFlavor();
        this.artist = card.getArtist();
        this.number = card.getNumber();
        this.power = card.getPower();
        this.toughness = card.getToughness();
        this.loyalty = card.getLoyalty();
        this.multiverseid = card.getMultiverseid();
        this.variations = card.getVariations();
        this.imageName = card.getImageName();
        this.watermark = card.getWatermark();
        this.border = card.getBorder();
        this.timeshifted = card.isTimeshifted();
        this.hand = card.getHand();
        this.life = card.getLife();
        this.reserved = card.isReserved();
        this.releaseDate = card.getReleaseDate();
        this.starter = card.isStarter();
        this.set = card.getSet();
        this.setName = card.getSetName();
        this.printings = card.getPrintings();
        this.imageUrl = card.getImageUrl();
        this.legalities = card.getLegalities();
        this.rulings = card.getRulings();
    }

    @Id
    private String id;
    private String layout;
    private String name;
    private String[] names;
    private String manaCost;
    private double cmc;
    private String[] colors;
    private String[] colorIdentity;
    private String type;
    private String[] supertypes;
    private String[] types;
    private String[] subtypes;
    private String rarity;
    private String text;
    private String originalText;
    private String flavor;
    private String artist;
    private String number;
    private String power;
    private String toughness;
    private String loyalty;
    private int multiverseid = -1;
    private String[] variations;
    private String imageName;
    private String watermark;
    private String border;
    private boolean timeshifted;
    private int hand;
    private int life;
    private boolean reserved;
    private String releaseDate;
    private boolean starter;
    private String set;
    private String setName;
    private String[] printings;
    private String imageUrl;
    private Legality[] legalities;
    private Ruling[] rulings;
}
