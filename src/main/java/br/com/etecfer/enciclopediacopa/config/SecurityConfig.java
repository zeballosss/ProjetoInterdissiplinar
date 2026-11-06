package br.com.etecfer.enciclopediacopa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(autorizacao -> autorizacao
                .requestMatchers("/", "/jogadores", "/jogadores/**", "/login", "/cadastro",
                        "/acesso-negado", "/css/**", "/images/**", "/uploads/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/favoritos/**", "/interacoes/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(formulario -> formulario
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("senha")
                .defaultSuccessUrl("/", false)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?saiu")
                .permitAll()
            )
            .exceptionHandling(excecoes -> excecoes
                .accessDeniedPage("/acesso-negado")
            );

        return http.build();
    }
}
