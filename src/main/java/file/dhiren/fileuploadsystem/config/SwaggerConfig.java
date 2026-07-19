package file.dhiren.fileuploadsystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI fileUploadOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("AI File Upload System API")
                        .description("""
                                REST APIs for AI File Upload System.
                                
                                Features:
                                - JWT Authentication
                                - File Upload
                                - Download Documents
                                - Document Management
                                - AI Processing (Coming Soon)
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Dhiren Patyal")
                                .email("patyaldhiren@gmail.com"))
                        .license(new License()
                                .name("MIT License")))
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Project Documentation")
                );
    }
}