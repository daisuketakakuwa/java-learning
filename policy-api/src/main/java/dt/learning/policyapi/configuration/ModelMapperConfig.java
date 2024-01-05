package dt.learning.policyapi.configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPreferNestedProperties(false);
        // String to LocalDateTime
        mapper.addConverter(
                new AbstractConverter<String, LocalDateTime>() {
                    @Override
                    protected LocalDateTime convert(String source) {
                        if (source == null) {
                            return null;
                        }
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        return LocalDateTime.parse(source, format);
                    }
                });
        // LocalDateTime to String
        mapper.addConverter(
                new AbstractConverter<LocalDateTime, String>() {
                    @Override
                    protected String convert(LocalDateTime source) {
                        if (source == null) {
                            return null;
                        }
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        return source.format(format);
                    }
                });
        return mapper;
    }
}
