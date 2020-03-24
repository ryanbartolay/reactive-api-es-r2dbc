package com.peplink.ecommerce.workflow.reactive.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.peplink.ecommerce.workflow.reactive.api.util.Constants;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private ApplicationContext m_applicationContext;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {

//        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(authenticationManager,
//                m_applicationContext);

        return http.cors()
                .and()
                .csrf()
                .disable() // We don't need CSRF for JWT based authentication
                .securityContextRepository(securityContextRepository)
//                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()

                .pathMatchers("/**")
                .hasAuthority(Authorities.ACCESS_MAIN_API_CELLULAR_MODULE.getCode())
                .pathMatchers("/" + Constants.API_PATH_CELLULAR_MODULES + "/**")
                .hasAuthority(Authorities.ACCESS_MAIN_API_CELLULAR_MODULE.getCode())
                .pathMatchers("/" + Constants.API_PATH_SFA_MEMBERSHIP + "/**")
                .hasAuthority(Authorities.ACCESS_MAIN_API_SFA_MEMBERSHIP.getCode())

                .anyExchange()
                .authenticated()

                .and()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
