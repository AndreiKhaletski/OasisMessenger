package by.oasis.service.api;

public interface IVerificationService {
    void create(String email, String title, String text);

    String get(String email);

    void detele(String email);

}
