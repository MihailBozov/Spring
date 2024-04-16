package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.models.dto.xmlDtos.AstronomerRootDto;
import softuni.exam.models.enums.StarType;
import softuni.exam.util.StarTypeAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

// TODO:
@Configuration
public class ApplicationBeanConfiguration {
    
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(StarType.class, new StarTypeAdapter())
                .create();
    }
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(AstronomerRootDto.class);
    }
}
