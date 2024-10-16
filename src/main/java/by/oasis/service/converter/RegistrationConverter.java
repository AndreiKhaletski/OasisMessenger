package by.oasis.service.converter;

import by.oasis.core.dto.RegistrationDto;
import by.oasis.dao.entity.RegistrationEntity;
import org.springframework.stereotype.Component;

@Component
public class RegistrationConverter {

    public RegistrationDto convertToRegDto (RegistrationEntity item){
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUuid(item.getUuid());
        registrationDto.setEmail(item.getEmail());
        registrationDto.setPassword(item.getPassword());
        registrationDto.setRole(item.getRole());
        registrationDto.setName(item.getName());
        registrationDto.setSurname(item.getSurname());
        registrationDto.setNickname(item.getNickname());
        registrationDto.setStatus(item.getStatus());
        registrationDto.setBirth_day(item.getBirthDay());
        registrationDto.setDt_create(item.getDtCreate());
        registrationDto.setDt_update(item.getDtUpdate());
        registrationDto.setToken(item.getToken());
        return registrationDto;
    }

    public RegistrationEntity convertToRegEntity (RegistrationDto item){
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setUuid(item.getUuid());
        registrationEntity.setEmail(item.getEmail());
        registrationEntity.setRole(item.getRole());
        registrationEntity.setName(item.getName());
        registrationEntity.setSurname(item.getSurname());
        registrationEntity.setNickname(item.getNickname());
        registrationEntity.setStatus(item.getStatus());
        registrationEntity.setBirthDay(item.getBirth_day());
        registrationEntity.setDtCreate(item.getDt_create());
        registrationEntity.setDtUpdate(item.getDt_update());
        registrationEntity.setToken(item.getToken());
        return registrationEntity;
    }


}
