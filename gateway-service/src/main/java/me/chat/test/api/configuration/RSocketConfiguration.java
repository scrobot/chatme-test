package me.chat.test.api.configuration;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class RSocketConfiguration {

    @Bean
    public RSocket rSocket(@Value("${rsocket.client.port}") int port) {
        return RSocketFactory
            .connect()
            .mimeType(MimeTypeUtils.ALL_VALUE, MimeTypeUtils.ALL_VALUE)
            .frameDecoder(PayloadDecoder.ZERO_COPY)
            .transport(TcpClientTransport.create(port))
            .start()
            .retry()
            .block();
    }

    @Bean
    public RSocketRequester rSocketRequester(RSocket rSocket, RSocketStrategies rSocketStrategies) {
        return RSocketRequester.wrap(
            rSocket,
            MimeTypeUtils.ALL,
            MimeTypeUtils.ALL,
            rSocketStrategies
        );
    }

}
