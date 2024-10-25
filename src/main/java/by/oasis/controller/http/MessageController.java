//package by.oasis.controller.http;
//
//import by.oasis.core.dto.MessageDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/message")
//public class MessageController {
//
//    private final SimpMessagingTemplate template;
//
//    public MessageController(SimpMessagingTemplate template) {
//        this.template = template;
//    }
//
//    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto){
//        template.convertAndSend("/topic/messages", messageDto);
//        return ResponseEntity.ok().build();
//    }
//}
