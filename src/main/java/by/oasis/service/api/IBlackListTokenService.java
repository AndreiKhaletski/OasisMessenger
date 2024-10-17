package by.oasis.service.api;

public interface IBlackListTokenService {
    void add(String token);

    Boolean get(String token);
}
