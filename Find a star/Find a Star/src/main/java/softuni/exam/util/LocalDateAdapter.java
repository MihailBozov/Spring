package softuni.exam.util;

import org.springframework.cglib.core.Local;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public LocalDate unmarshal(String s) throws Exception {
        if (s == null) {
            return null;
        }
        
        LocalDate localDate = LocalDate.parse(s, inputFormatter);
        LocalDate outputDate = LocalDate.parse(localDate.format(outputFormatter));
        return outputDate;
    }
    
    @Override
    public String marshal(LocalDate date) throws Exception {
        return date.toString();
    }
}
