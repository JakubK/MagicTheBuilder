package magicthebuilder.deckservice.rabbit;

import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {

    @Autowired
    UserService userService;

    @RabbitListener(queues = "#{userSyncQueue.name}")
    public void receive1(Long num) {
        System.out.println("Received user with ID : " + num);
        userService.add(new User(num));
    }


}
