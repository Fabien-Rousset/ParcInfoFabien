package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix= "fr.afpa.pompey.cda17.appweb")
@Data
public class CustomProperties {

    private String apiUrl;
}
