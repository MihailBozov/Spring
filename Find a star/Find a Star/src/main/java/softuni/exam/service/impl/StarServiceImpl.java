package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonDtos.StarDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Implement all methods
@Service
public class StarServiceImpl implements StarService {
    private static final String FILE_PATH = "src/main/resources/files/json/stars.json";
    
    StarRepository starRepository;
    ConstellationRepository constellationRepository;
    
    Gson gson;
    ValidationUtil validationUtil;
    ModelMapper modelMapper;
    
    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }
    
    @Override
    public String readStarsFileContent() throws IOException {
        Path path = Path.of(FILE_PATH);
        byte[] bytes = Files.readAllBytes(path);
        
        return new String(bytes);
    }
    
    @Override
    public String importStars() throws IOException {
        StringBuilder builder = new StringBuilder();
        
        File file = new File(FILE_PATH);
        FileReader fileReader = new FileReader(file);
        StarDto[] starDtos = this.gson.fromJson(fileReader, StarDto[].class);
        
        for (StarDto starDto : starDtos) {
            String exceptionMessages = this.validationUtil.validate(starDto);
            Optional<Star> starOptional = this.starRepository.findByName(starDto.getName());
            Optional<Constellation> constellationOptional = this.constellationRepository.findById(starDto.getConstellation());
            
            if (exceptionMessages != null || starOptional.isPresent() || constellationOptional.isEmpty()) {
                builder.append("Invalid star\n");
            }
            else {
                Star star = this.modelMapper.map(starDto, Star.class);
                star.setConstellation(constellationOptional.get());
                this.starRepository.saveAndFlush(star);
                
                builder.append(String.format("Successfully imported star %s - %.2f light years\n", star.getName(), star.getLightYears()));
            }
        }
        
        return builder.toString();
    }
    
    @Override
    public String exportStars() {
        List<Star> stars = this.starRepository.exportStars();
        
        return stars
                .stream()
                .map(star -> String.format(
                         "Star: %s\n" +
                         "   *Distance: %.2f light years\n" +
                         "   **Description: %s\n" +
                         "   ***Constellation: %s\n",
                        star.getName(),
                        star.getLightYears(),
                        star.getDescription(),
                        star.getConstellation().getName()
                )).collect(Collectors.joining());
    }
}
