package by.oasis.service.api;

import org.springframework.transaction.annotation.Transactional;

public interface IVerificationService {
    void create(String email, String title, String text);

    String get(String email);

    @Transactional
    void detele(String email);
}
