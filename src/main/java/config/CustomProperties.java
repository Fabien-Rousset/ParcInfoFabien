package config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fr.afpa.pompey.cda17") // Corrected prefix
public class CustomProperties {
    private String apiUrl;
}