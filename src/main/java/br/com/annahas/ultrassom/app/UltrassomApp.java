package br.com.annahas.ultrassom.app;

import static java.util.logging.Logger.getLogger;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiKeyAuthDefinition.ApiKeyLocation;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("api/v1")
@SwaggerDefinition(
        info = @Info(
                description = "Ultrassom",
                version = "1.0.0",
                title = "Ultrassom API",
                contact = @Contact(
                   name = "Amir Leonardo Kessler Annahas", 
                   email = "leo.amir@gmail.com", 
                   url = "http://www.annahas.com.br"
                )
        ),
        tags = {
        		@Tag(name = "Ultrassom", description = "Serviços gerais da API.")
        }, 
        securityDefinition =  
    		@SecurityDefinition(
				apiKeyAuthDefinitions = {
        				@ApiKeyAuthDefinition(
        						description = "Cole abaixo o token de autenticação <span style=\"color: #cc1122;\">precedido do identificador <strong>JWT</strong></span>",
        						in = ApiKeyLocation.HEADER,
        						name = "Authorization",
        						key = "Token"
        				)
				}		
    		)
)
public class UltrassomApp extends Application {

	@SuppressWarnings("unused")
	private static final Logger LOG = getLogger(UltrassomApp.class.getName());
	
	public UltrassomApp() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
        beanConfig.setBasePath("/extensao-backend/api/v1");
        beanConfig.setResourcePackage("br.com.annahas.ultrassom");
        beanConfig.setScan(true);
	}
}
