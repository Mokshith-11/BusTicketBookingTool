package com.gemini.BusTicketBookingSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;

/*
 * Security rules are grouped by module owner.
 * Keep specific nested paths above wildcard paths because Spring checks them top to bottom.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ── PASSWORD ENCODER ─────────────────────────────────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ── IN-MEMORY USERS ──────────────────────────────────────────────────────
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();

        UserDetails vignesh = User.builder()
                .username("vignesh")
                .password(encoder.encode("vignesh123"))
                .roles("VIGNESH")
                .build();

        UserDetails nithish = User.builder()
                .username("nithish")
                .password(encoder.encode("nithish123"))
                .roles("NITHISH")
                .build();

        UserDetails aksha = User.builder()
                .username("aksha")
                .password(encoder.encode("aksha123"))
                .roles("AKSHA")
                .build();

        UserDetails ajitha = User.builder()
                .username("ajitha")
                .password(encoder.encode("ajitha123"))
                .roles("AJITHA")
                .build();

        UserDetails priyadharshini = User.builder()
                .username("priyadharshini")
                .password(encoder.encode("priya123"))
                .roles("PRIYA")
                .build();

        return new InMemoryUserDetailsManager(
                vignesh, nithish, aksha, ajitha, priyadharshini
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // ── SECURITY FILTER CHAIN ────────────────────────────────────────────────
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // ── PUBLIC ───────────────────────────────────────────────────
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // ════════════════════════════════════════════════════════════
                        // ⚠️  SPECIFIC (overlapping) rules FIRST — before wildcards
                        // ════════════════════════════════════════════════════════════

                        // Booking endpoints under trips/customers must stay above wildcard rules.
                        .requestMatchers("/api/v1/trips/*/bookings").hasRole("VIGNESH")
                        .requestMatchers("/api/v1/customers/*/bookings").hasRole("VIGNESH")

                        // Customer reviews must stay above /api/v1/customers/**.
                        .requestMatchers("/api/v1/customers/*/reviews").hasRole("PRIYA")

                        // Office bus/driver paths must stay above /api/v1/offices/**.
                        .requestMatchers("/api/v1/offices/*/buses").hasRole("NITHISH")
                        .requestMatchers("/api/v1/offices/*/drivers").hasRole("NITHISH")

                        // Agency office paths are kept explicit before agency wildcard rules.
                        .requestMatchers("/api/v1/agencies/*/offices").hasRole("AKSHA")

                        // Shared dropdowns only need read access.
                        .requestMatchers(HttpMethod.GET, "/api/v1/trips").hasAnyRole("VIGNESH", "PRIYA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/routes").hasAnyRole("AJITHA", "PRIYA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers").hasAnyRole("AJITHA", "VIGNESH", "PRIYA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/buses").hasAnyRole("NITHISH", "PRIYA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/drivers").hasAnyRole("NITHISH", "PRIYA")
                        .requestMatchers(HttpMethod.GET, "/api/v1/offices").hasAnyRole("AKSHA", "NITHISH")
                        .requestMatchers(HttpMethod.GET, "/api/v1/addresses").hasAnyRole("AKSHA", "AJITHA", "NITHISH", "PRIYA")

                        // ════════════════════════════════════════════════════════════
                        // WILDCARD rules AFTER specific rules
                        // ════════════════════════════════════════════════════════════

                        // Booking and payment APIs
                        .requestMatchers("/api/v1/bookings/**").hasRole("VIGNESH")
                        .requestMatchers("/api/v1/payments/**").hasRole("VIGNESH")

                        // Bus and driver APIs
                        .requestMatchers("/api/v1/buses/**").hasRole("NITHISH")
                        .requestMatchers("/api/v1/drivers/**").hasRole("NITHISH")

                        // Agency, office, and address APIs
                        .requestMatchers("/api/v1/agencies/**").hasRole("AKSHA")
                        .requestMatchers("/api/v1/offices/**").hasRole("AKSHA")
                        .requestMatchers("/api/v1/addresses/**").hasRole("AKSHA")

                        // Customer and route APIs
                        .requestMatchers("/api/v1/customers/**").hasRole("AJITHA")
                        .requestMatchers("/api/v1/routes/**").hasRole("AJITHA")

                        // Seat APIs are shared by booking and trip modules.
                        .requestMatchers(
                                "/api/v1/trips/*/seats",
                                "/api/v1/trips/*/seats/available",
                                "/api/v1/trips/*/seats/booked"
                        ).hasAnyRole("VIGNESH", "PRIYA")

                        // Trip and review APIs
                        .requestMatchers("/api/v1/trips/**").hasRole("PRIYA")
                        .requestMatchers("/api/v1/reviews/**").hasRole("PRIYA")

                        // Anything else → must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.realmName("BusTicketBookingSystem"));

        return http.build();
    }
}
