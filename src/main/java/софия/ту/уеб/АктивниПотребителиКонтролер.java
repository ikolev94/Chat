package софия.ту.уеб;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import софия.ту.сървис.СървисАктивниПотребители;

@Controller
public class АктивниПотребителиКонтролер {
  
  private СървисАктивниПотребители activeUserService;

  @Inject
  public АктивниПотребителиКонтролер(СървисАктивниПотребители activeUserService) {
    this.activeUserService = activeUserService;
  }
  
  @MessageMapping("/activeUsers")
  public void activeUsers(Message<Object> message) {
    Principal user = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
    activeUserService.маркирай(user.getName());
  }

}