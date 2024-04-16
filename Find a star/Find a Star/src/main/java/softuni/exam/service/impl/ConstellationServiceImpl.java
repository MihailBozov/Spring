package softuni.exam.service.impl;

// TODO: Implement all methods

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonDtos.ConstellationDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    static final String FILE_PATH = "src/main/resources/files/json/constellations.json";
    
    ConstellationRepository constellationRepository;
    
    Gson gson;
    ValidationUtil validationUtil;
    ModelMapper modelMapper;
    
    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }
    
    @Override
    public String readConstellationsFromFile() throws IOException {
        Path path = Path.of(FILE_PATH);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }
    
    @Override
    public String importConstellations() throws IOException {
        StringBuilder builder = new StringBuilder();
        
        File file = new File(FILE_PATH);
        FileReader fileReader = new FileReader(file);
        ConstellationDto[] constellationDtos = this.gson.fromJson(fileReader, ConstellationDto[].class);
        
        for (ConstellationDto constellationDto : constellationDtos) {
            String exceptionMessages = this.validationUtil.validate(constellationDto);
            Optional<Constellation> constellationOptional = this.constellationRepository.findByName(constellationDto.getName());
            
            if (exceptionMessages != null || constellationOptional.isPresent()) {
                builder.append("Invalid constellation\n");
            }
            else {
                Constellation constellation = this.modelMapper.map(constellationDto, Constellation.class);
                this.constellationRepository.saveAndFlush(constellation);
                
                builder.append(String.format("Successfully imported constellation %s - %s\n", constellation.getName(), constellation.getDescription()));
            }
        }
        
        return builder.toString();
    }
}














