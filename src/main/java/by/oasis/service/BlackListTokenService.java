package by.oasis.service;

import by.oasis.dao.api.IBlackListTokenResource;
import by.oasis.dao.entity.BlackListTokenEntity;
import by.oasis.service.api.IBlackListTokenService;
import by.oasis.service.converter.BlackListTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BlackListTokenService implements IBlackListTokenService {

    private final BlackListTokenConverter converterBlackToken;
    private final IBlackListTokenResource blackListTokenResource;

    public BlackListTokenService(BlackListTokenConverter converterBlackToken,
                                 IBlackListTokenResource blackListTokenResource) {
        this.converterBlackToken = converterBlackToken;
        this.blackListTokenResource = blackListTokenResource;
    }

    @Override
    @Transactional
    public void add(String token) {
        BlackListTokenEntity blackListTokenEntity = new BlackListTokenEntity();
        blackListTokenEntity.setUuid(UUID.randomUUID());
        blackListTokenEntity.setToken(token);

        blackListTokenResource.saveAndFlush(blackListTokenEntity);
    }

    @Override
    public boolean get(String token) {
        return blackListTokenResource.existsByToken(token);
    }

    @Override
    public void validateTokenNonBlock(String token) {

    }
}
