package logonedigital.webappapi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "LOGONEDIGITAL IT SOLUTIONS",
                        email = "contact@logonedigital.com",
                        url = "https://logonedigital.com"
                ),
                title = "NEW LOGONEDIGITAL WEBSITE APIs",
                description = "Those APIs created by LOGONEDIGITAL IT SOLUTIONS software developers",
                termsOfService = "&copy; LOGONEDIGITAL",
                version = "v1"
        )
)
public class OpenApiConfig {
}
