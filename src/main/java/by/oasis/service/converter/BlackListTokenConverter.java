package by.oasis.service.converter;

import by.oasis.core.dto.BlackListTokenDto;
import by.oasis.dao.entity.BlackListTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class BlackListTokenConverter {

    public BlackListTokenDto blackListTokenDto (BlackListTokenEntity item){
        BlackListTokenDto blackListTokenDto = new BlackListTokenDto();
        blackListTokenDto.setUuid(item.getUuid());
        blackListTokenDto.setToken(item.getToken());
        return blackListTokenDto;
    }

    public BlackListTokenEntity blackListTokenEntity (BlackListTokenDto item){
        BlackListTokenEntity blackListTokenEntity = new BlackListTokenEntity();
        blackListTokenEntity.setUuid(item.getUuid());
        blackListTokenEntity.setToken(item.getToken());
        blackListTokenEntity.setBlocktime_at(item.getBlocktime_at());
        return blackListTokenEntity;
    }
}
