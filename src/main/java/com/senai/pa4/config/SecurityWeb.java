//package com.senai.pa4.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityWeb {
//
//    @Autowired
//    private UserDetailsServiceCustom userDetailsServiceCustom;
//
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.disable())  // Desabilita CORS se não for necessário
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/login", "/public/**"))  // Ignora CSRF para login e endpoints públicos
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/login").permitAll()  // Permite acesso ao endpoint de login sem autenticação
//                        .requestMatchers(HttpMethod.POST, "/public/**").permitAll()  // Permite POST para endpoints públicos
//                        .anyRequest().authenticated()  // Exige autenticação para todas as outras requisições
//                )
//                .sessionManagement(session -> session
//                        .maximumSessions(1)  // Permite uma sessão por vez
//                        .expiredUrl("/login?expired")  // URL quando a sessão expirar
//                        .maxSessionsPreventsLogin(true)  // Previne login quando o número máximo de sessões for atingido
//                )
//                .userDetailsService(userDetailsServiceCustom)  // Configura o serviço customizado para carregar o usuário
//                .httpBasic(Customizer.withDefaults())  // Permite autenticação via HTTP Basic
//                .formLogin(form -> form.permitAll())  // Permite o login via formulário
//                .logout(logout -> logout.permitAll());  // Permite o logout sem autenticação
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Define o encoder de senha como BCrypt
//    }
//}
