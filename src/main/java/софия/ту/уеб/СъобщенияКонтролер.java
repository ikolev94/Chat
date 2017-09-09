package софия.ту.уеб;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import софия.ту.модел.ЧатСъобщение;
import софия.ту.сървис.СървисСъобщения;

@RestController
public class СъобщенияКонтролер {

    @Autowired
    private SimpMessagingTemplate шаблон;

    @Autowired
    private СървисСъобщения сървисСъобщения;

    @MessageMapping("/chat")
    public void greeting(Message<Object> съобщение, @Payload ЧатСъобщение чатСъобщение)
	    throws Exception {
	Principal принсипал = съобщение.getHeaders()
		.get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
	String authedSender = принсипал.getName();
	чатСъобщение.запазиИзпращач(authedSender);
	String получател = чатСъобщение.вземиПолучател();
	this.сървисСъобщения.save(чатСъобщение);
	if (!authedSender.equals(получател)) {
	    шаблон.convertAndSendToUser(authedSender, "/queue/messages", чатСъобщение);
	}

	шаблон.convertAndSendToUser(получател, "/queue/messages", чатСъобщение);
    }

    @RequestMapping("messagesBetween/{изпращач}/{получател}")
    public List<ЧатСъобщение> намериКореспонденция(@PathVariable String изпращач, @PathVariable String получател) {
	return сървисСъобщения.намериСъобщенияМеждуДвама(изпращач, получател);
    }

}
