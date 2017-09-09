package софия.ту;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import софия.ту.сървис.СървисАктивниПотребители;

/**
 * Класът позволява да се активира и конфигурира Опростен (или стрийминг)
 * протокол за текстово ориентирани съобщения (Simple (or Streaming) Text
 * Oriented Message Protocol - STOMP). Конфигурирането става посредством замяна
 * (override) на методи по подразбиране.
 * 
 * @author Иван Колев
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class УебСокетКонфигуратор implements WebSocketMessageBrokerConfigurer {

    /**
     * Помага за регистрирането на посредник при размяната на съобщения. В случая
     * регистрираме обикновен посредник за точките за достъп (endpoints) с префикс:
     * topic - теми - понеже може да има повече от един получател за едно съобщение
     * и queue - опашка - понеже има комуникация между отделните потребители.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
	config.enableSimpleBroker("/queue", "/topic");
	config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Регистрира точки за достъп (endpoints) през които ще се осъществява STOMP и
     * Уеб сокет комуникация.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
	registry.addEndpoint("/chat", "/activeUsers").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration channelRegistration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {
	return true;
    }

    @Bean
    public СървисАктивниПотребители activeUserService() {
	return new СървисАктивниПотребители();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration arg0) {
	// TODO Auto-generated method stub

    }

}