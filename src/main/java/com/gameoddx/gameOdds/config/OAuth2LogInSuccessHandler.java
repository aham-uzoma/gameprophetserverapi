package com.gameoddx.gameOdds.config;

import com.gameoddx.gameOdds.model.RegistrationSource;
import com.gameoddx.gameOdds.model.User;
import com.gameoddx.gameOdds.model.UserRole;
import com.gameoddx.gameOdds.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class OAuth2LogInSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {

    if(authentication instanceof OAuth2AuthenticationToken){
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        //Check for google as authorized client
        if("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())){
            OAuth2User principle = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principle.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();

            //Find a user by email or create a new user
            User existingUser = userService.findByEmail(email);
            if (existingUser != null){
                //Updating authentication context with user roles
                DefaultOAuth2User newUser = new DefaultOAuth2User(
                        List.of(new SimpleGrantedAuthority(existingUser.getRole().name())),attributes,"id");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(existingUser.getRole().name())),
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            } else {
                User user = new User();
                user.setRole(UserRole.ROLE_USER);
                user.setEmail(email);
                user.setName(name);
                user.setRegistrationSource(RegistrationSource.GOOGLE);
                userService.createNewUser(user);
                //Updating authentication context with user roles
                DefaultOAuth2User newUser = new DefaultOAuth2User(
                        List.of(new SimpleGrantedAuthority(user.getRole().name())),attributes,"id");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);

            }
        }
    }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
