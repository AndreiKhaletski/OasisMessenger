package by.oasis.service.api;

public interface IBlackListTokenService {
    void add(String token);

    boolean get(String token);

    void validateTokenNonBlock(String token);
}
