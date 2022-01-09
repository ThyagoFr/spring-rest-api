package github.thyago.spaceflightnewsintegration.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockServerConfig {

    @Value(value = "${space.flight.server.port}")
    private Integer spaceFlightServerPort;

    @Bean(initMethod = "start",  destroyMethod = "stop")
    public WireMockServer mockSpaceFlightAPIServer() {
        return new WireMockServer(this.spaceFlightServerPort);
    }

}
