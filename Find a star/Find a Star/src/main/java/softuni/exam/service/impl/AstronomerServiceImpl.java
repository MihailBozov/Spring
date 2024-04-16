package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmlDtos.AstronomerDto;
import softuni.exam.models.dto.xmlDtos.AstronomerRootDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// TODO: Implement all methods
@Service
public class AstronomerServiceImpl implements AstronomerService {
    static final String FILE_PATH = "src/main/resources/files/xml/astronomers.xml";
    
    AstronomerRepository astronomerRepository;
    StarRepository starRepository;
    
    JAXBContext jaxbContext;
    ValidationUtil validationUtil;
    ModelMapper modelMapper;
    
    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository, JAXBContext jaxbContext, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.jaxbContext = jaxbContext;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }
    
    @Override
    public String readAstronomersFromFile() throws IOException {
        Path path = Path.of(FILE_PATH);
        byte[] bytes = Files.readAllBytes(path);
        
        return new String(bytes);
    }
    
    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        File file = new File(FILE_PATH);
        AstronomerRootDto astronomerRootDto = (AstronomerRootDto) unmarshaller.unmarshal(file);
        
        for (AstronomerDto astronomerDto : astronomerRootDto.getAstronomers()) {
            String exceptionMessages = this.validationUtil.validate(astronomerDto);
            Optional<Astronomer> astronomerOptional = this.astronomerRepository.findByFirstNameAndLastName(astronomerDto.getFirstName(), astronomerDto.getLastName());
            Optional<Star> starOptional = this.starRepository.findById(astronomerDto.getObservingStarId());
            
            if (exceptionMessages != null || astronomerOptional.isPresent() || starOptional.isEmpty()) {
                builder.append("Invalid astronomer\n");
            }
            else {
                Astronomer astronomer = this.modelMapper.map(astronomerDto, Astronomer.class);
                astronomer.setObservingStar(starOptional.get());
                this.astronomerRepository.saveAndFlush(astronomer);
                
                builder.append(String.format("Successfully imported astronomer %s %s - %.2f\n", astronomer.getFirstName(), astronomer.getLastName(), astronomer.getAverageObservationHours()));
            }
        }
        return builder.toString();
    }
}
