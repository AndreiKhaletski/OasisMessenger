package by.oasis.service.detailesservice;

import by.oasis.core.enums.EnumStatusRegistration;
import by.oasis.dao.entity.RegistrationEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final RegistrationEntity registrationEntity;

    public CustomUserDetails(RegistrationEntity registrationEntity) {
        this.registrationEntity = registrationEntity;
    }

    public RegistrationEntity getRegistrationEntity() {
        return registrationEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(registrationEntity.getRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return registrationEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return registrationEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return registrationEntity.getStatus() == EnumStatusRegistration.ACTIVATED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return registrationEntity.getStatus() == EnumStatusRegistration.ACTIVATED;
    }
}