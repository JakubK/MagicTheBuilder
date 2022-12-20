package magicthebuilder.cardservice.repository;

import io.magicthegathering.javasdk.resource.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import java.io.*;
import java.util.List;

@Repository
public class FileRepository {
    private final int bufferSize = 1024 * 1024;

    private final String fileLocation;

    @Autowired
    public FileRepository(@Value("${fileWithAllCardsLocation}") String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void save(List<Card> cards, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName), bufferSize))) {
            oos.writeObject(cards);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Card> getAllCards() {
        List<Card> cards;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileLocation), bufferSize))) {
            cards = (List<Card>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cards;
    }

}
