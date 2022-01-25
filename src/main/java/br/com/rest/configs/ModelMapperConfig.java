package br.com.rest.configs;

import br.com.rest.dtos.UserCreateDTO;
import br.com.rest.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean("mapperConfig")
    public ModelMapper mapper(){
//        ModelMapper mapper = new ModelMapper();
//
//        mapper.typeMap(UserCreateDTO.class, User.class)
//                .addMappings(ctx -> ctx.skip(UserCreateDTO::getId, User::setId));

        return new ModelMapper();
    }

}
