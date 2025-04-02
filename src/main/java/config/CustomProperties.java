package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix= "appweb.apiurl")
public class CustomProperties {

    private String apiUrl;
}
