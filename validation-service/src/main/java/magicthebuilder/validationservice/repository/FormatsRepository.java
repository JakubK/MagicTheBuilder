package magicthebuilder.validationservice.repository;

import lombok.Getter;
import magicthebuilder.validationservice.entity.Format;
import magicthebuilder.validationservice.entity.rules.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FormatsRepository {

    @Getter
    private final List<Format> formats = new ArrayList<>();

    public FormatsRepository() {
        addStandard();
        addCommander();
        addCustom();
        addModern();
    }

    private void addCommander() {
        var commander = new Format("Commander");
        commander.AddRule(new AllCardsLegalInFormat());
        commander.AddRule(new NoMoreThanNNonBasicLandCopies(1));
        commander.AddRule(new ColorIdentitySameAsCommander());
        commander.AddRule(new NoSideboard());
        commander.AddRule(new CommanderIsALegendaryCreature());
        commander.AddRule(new ExactlyNCards(100));
        formats.add(commander);
    }

    private void addStandard() {
        var standard = new Format("Standard");
        standard.AddRule(new AllCardsLegalInFormat());
        standard.AddRule(new MainDeckNoSmallerThanSixty());
        standard.AddRule(new NoMoreThanNNonBasicLandCopies(4));
        standard.AddRule(new SideboardNotLargerThanFifteen());
        standard.AddRule(new NoCommander());
        formats.add(standard);
    }

    private void addCustom() {
        var custom = new Format("Custom");
        formats.add(custom);
    }

    private void addModern() {
        var modern = new Format("Modern");
        modern.AddRule(new AllCardsLegalInFormat());
        modern.AddRule(new MainDeckNoSmallerThanSixty());
        modern.AddRule(new NoMoreThanNNonBasicLandCopies(4));
        modern.AddRule(new SideboardNotLargerThanFifteen());
        modern.AddRule(new NoCommander());
        formats.add(modern);
    }
}
