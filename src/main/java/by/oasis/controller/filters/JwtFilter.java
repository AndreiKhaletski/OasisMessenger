package by.oasis.controller.filters;

import by.oasis.dao.entity.RegistrationEntity;
import by.oasis.service.api.IBlackListTokenService;
import by.oasis.service.api.IUserService;
import by.oasis.service.detailesservice.CustomUserDetails;
import by.oasis.service.jwt.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtHandler;
    private final IUserService userService;
    private final IBlackListTokenService blackListTokenService;

    public JwtFilter(JwtTokenHandler jwtHandler,
                     IUserService userService,
                     IBlackListTokenService blackListTokenService) {
        this.jwtHandler = jwtHandler;
        this.userService = userService;
        this.blackListTokenService = blackListTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
             return;
        }

        //Получение jwt токена и валидация
        final String token = header.split(" ")[1].trim();
        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        //Проверка токена на блокировку
        if (blackListTokenService.get("Bearer " + token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Ваш токен аннулирован!");
        }

        RegistrationEntity registrationEntity = userService.findByEmail(jwtHandler.getUsername(token));
        UserDetails userDetails = new CustomUserDetails(registrationEntity);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}