package soul.smi.pfe.ChatComservice.service.sockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import soul.smi.pfe.ChatComservice.dao.entities.Message;
import soul.smi.pfe.ChatComservice.dao.entities.Notification;

@Controller
public class WebSocketsController {


    @MessageMapping("/comment-notification")
    public void sendCommentNotification(Notification notification) {

    }
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        // Process the incoming message
        // Add your custom logic here
        return message;
    }
}
