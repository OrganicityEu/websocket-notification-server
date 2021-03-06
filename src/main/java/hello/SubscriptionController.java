package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class SubscriptionController {
    private SimpMessagingTemplate template;

    @Autowired
    public SubscriptionController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Asset greeting(SubscriptionMessage message) throws Exception {
        Thread.sleep(100); // simulated delay
        return new Asset("Hello, " + message.getName() + "!");
    }

    @RequestMapping("/notify")
    public String greeting2() throws Exception {
        this.template.convertAndSend("/topic/greetings",  new Asset("Hello !"));
        return "hello";
    }
}
