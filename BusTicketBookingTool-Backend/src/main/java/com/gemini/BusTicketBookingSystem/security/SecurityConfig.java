package com.gemini.BusTicketBookingSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

/**
 * ════════════════════════════════════════════════════════════════════════════
 *  SecurityConfig — matched to YOUR EXACT controller endpoints
 * ════════════════════════════════════════════════════════════════════════════
 *
 *  VIGNESH  (Booking + Payment)
 *  ├─ POST   /api/v1/trips/{tripId}/bookings
 *  ├─ GET    /api/v1/customers/{customerId}/bookings
 *  ├─ GET    /api/v1/bookings/{bookingId}
 *  ├─ PATCH  /api/v1/bookings/{bookingId}/cancel
 *  ├─ POST   /api/v1/payments
 *  ├─ GET    /api/v1/payments/{paymentId}
 *  ├─ GET    /api/v1/payments/customers/{customerId}/payments
 *  ├─ GET    /api/v1/payments/bookings/{bookingId}/payment
 *  └─ PATCH  /api/v1/payments/{paymentId}/status
 *
 *  NITHISH  (Bus + Driver)
 *  ├─ POST   /api/v1/offices/{officeId}/buses
 *  ├─ GET    /api/v1/offices/{officeId}/buses
 *  ├─ GET    /api/v1/buses/{busId}
 *  ├─ PUT    /api/v1/buses/{busId}
 *  ├─ DELETE /api/v1/buses/{busId}
 *  ├─ POST   /api/v1/offices/{officeId}/drivers
 *  ├─ GET    /api/v1/offices/{officeId}/drivers
 *  ├─ GET    /api/v1/drivers/{driverId}
 *  ├─ PUT    /api/v1/drivers/{driverId}
 *  └─ DELETE /api/v1/drivers/{driverId}
 *
 *  AKSHA    (Agency + AgencyOffice + Address)
 *  ├─ POST   /api/v1/agencies
 *  ├─ GET    /api/v1/agencies
 *  ├─ GET    /api/v1/agencies/{agencyId}
 *  ├─ PUT    /api/v1/agencies/{agencyId}
 *  ├─ DELETE /api/v1/agencies/{agencyId}
 *  ├─ POST   /api/v1/agencies/{agencyId}/offices
 *  ├─ GET    /api/v1/agencies/{agencyId}/offices
 *  ├─ GET    /api/v1/offices/{officeId}
 *  ├─ PUT    /api/v1/offices/{officeId}
 *  ├─ DELETE /api/v1/offices/{officeId}
 *  ├─ POST   /api/v1/addresses
 *  └─ GET    /api/v1/addresses/{id}
 *
 *  AJITHA   (Customer + Route)
 *  ├─ POST   /api/v1/customers
 *  ├─ GET    /api/v1/customers
 *  ├─ GET    /api/v1/customers/{customerId}
 *  ├─ PUT    /api/v1/customers/{customerId}
 *  ├─ POST   /api/v1/routes
 *  ├─ GET    /api/v1/routes
 *  ├─ GET    /api/v1/routes/{routeId}
 *  ├─ PUT    /api/v1/routes/{routeId}
 *  └─ DELETE /api/v1/routes/{routeId}
 *
 *  PRIYADHARSHINI  (Trip + Review)
 *  ├─ POST   /api/v1/trips
 *  ├─ GET    /api/v1/trips
 *  ├─ GET    /api/v1/trips/{tripId}
 *  ├─ GET    /api/v1/trips/search
 *  ├─ PUT    /api/v1/trips/{tripId}
 *  ├─ PATCH  /api/v1/trips/{tripId}/close
 *  ├─ GET    /api/v1/trips/{tripId}/seats
 *  ├─ GET    /api/v1/trips/{tripId}/seats/booked
 *  ├─ GET    /api/v1/trips/{tripId}/seats/available
 *  ├─ POST   /api/v1/trips/{tripId}/reviews
 *  ├─ GET    /api/v1/trips/{tripId}/reviews
 *  ├─ GET    /api/v1/customers/{customerId}/reviews
 *  └─ DELETE /api/v1/reviews/{reviewId}
 *
 * ════════════════════════════════════════════════════════════════════════════
 *  ⚠️  TRICKY OVERLAP CASES — handled by ORDERING rules top to bottom:
 *
 *  /api/v1/customers/{id}/bookings  → VIGNESH  (not Ajitha's /customers/**)
 *  /api/v1/customers/{id}/reviews   → PRIYADHARSHINI (not Ajitha's /customers/**)
 *  /api/v1/trips/{id}/bookings      → VIGNESH  (not Priyadharshini's /trips/**)
 *  /api/v1/trips/{id}/reviews       → PRIYADHARSHINI (same /trips/** → fine)
 *  /api/v1/offices/{id}/buses       → NITHISH  (not Aksha's /offices/**)
 *  /api/v1/offices/{id}/drivers     → NITHISH  (not Aksha's /offices/**)
 *  /api/v1/agencies/{id}/offices    → AKSHA    (under /agencies/**)
 *  /api/v1/payments/customers/{id}  → VIGNESH  (under /payments/**)
 *  /api/v1/payments/bookings/{id}   → VIGNESH  (under /payments/**)
 *
 *  Spring Security reads rules TOP TO BOTTOM — first match wins.
 *  Specific rules must come BEFORE wildcard rules.
 * ════════════════════════════════════════════════════════════════════════════
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

                        // VIGNESH — booking endpoints nested under /trips/ and /customers/
                        // Must come BEFORE Priyadharshini's /api/v1/trips/** wildcard
                        // and BEFORE Ajitha's /api/v1/customers/** wildcard
                        .requestMatchers("/api/v1/trips/*/bookings").hasRole("VIGNESH")
                        .requestMatchers("/api/v1/customers/*/bookings").hasRole("VIGNESH")

                        // PRIYADHARSHINI — review endpoints nested under /customers/
                        // Must come BEFORE Ajitha's /api/v1/customers/** wildcard
                        .requestMatchers("/api/v1/customers/*/reviews").hasRole("PRIYA")

                        // NITHISH — bus and driver endpoints nested under /offices/
                        // Must come BEFORE Aksha's /api/v1/offices/** wildcard
                        .requestMatchers("/api/v1/offices/*/buses").hasRole("NITHISH")
                        .requestMatchers("/api/v1/offices/*/drivers").hasRole("NITHISH")

                        // AKSHA — office endpoints nested under /agencies/
                        // Must come BEFORE Aksha's own /api/v1/agencies/** (compatible, but explicit)
                        .requestMatchers("/api/v1/agencies/*/offices").hasRole("AKSHA")

                        // ════════════════════════════════════════════════════════════
                        // WILDCARD rules AFTER specific rules
                        // ════════════════════════════════════════════════════════════

                        // ── VIGNESH: Booking + Payment ───────────────────────────────
                        // /api/v1/bookings/**       covers: GET /bookings/{id}, PATCH /bookings/{id}/cancel
                        // /api/v1/payments/**       covers: POST, GET /{id}, GET /customers/{id}/payments,
                        //                                   GET /bookings/{id}/payment, PATCH /{id}/status
                        .requestMatchers("/api/v1/bookings/**").hasRole("VIGNESH")
                        .requestMatchers("/api/v1/payments/**").hasRole("VIGNESH")

                        // ── NITHISH: Bus + Driver ────────────────────────────────────
                        // /api/v1/buses/**          covers: GET /{id}, PUT /{id}, DELETE /{id}
                        // /api/v1/drivers/**        covers: GET /{id}, PUT /{id}, DELETE /{id}
                        .requestMatchers("/api/v1/buses/**").hasRole("NITHISH")
                        .requestMatchers("/api/v1/drivers/**").hasRole("NITHISH")

                        // ── AKSHA: Agency + AgencyOffice + Address ───────────────────
                        // /api/v1/agencies/**       covers: POST, GET, GET/{id}, PUT/{id}, DELETE/{id}
                        //                                   AND POST /agencies/{id}/offices
                        //                                   AND GET  /agencies/{id}/offices
                        // /api/v1/offices/**        covers: GET /{id}, PUT /{id}, DELETE /{id}
                        //                           (buses/drivers already claimed above by NITHISH)
                        // /api/v1/addresses/**      covers: POST, GET /{id}
                        .requestMatchers("/api/v1/agencies/**").hasRole("AKSHA")
                        .requestMatchers("/api/v1/offices/**").hasRole("AKSHA")
                        .requestMatchers("/api/v1/addresses/**").hasRole("AKSHA")

                        // ── AJITHA: Customer + Route ─────────────────────────────────
                        // /api/v1/customers/**      covers: POST, GET, GET/{id}, PUT/{id}
                        //                           (bookings and reviews already claimed above)
                        // /api/v1/routes/**         covers: POST, GET, GET/{id}, PUT/{id}, DELETE/{id}
                        .requestMatchers("/api/v1/customers/**").hasRole("AJITHA")
                        .requestMatchers("/api/v1/routes/**").hasRole("AJITHA")

                        // PRIYADHARSHINI: Trip + Review
                        // Seat endpoints need to be accessible by VIGNESH (to book) and PRIYA (to manage)
                        .requestMatchers(
                                "/api/v1/trips/*/seats",
                                "/api/v1/trips/*/seats/available",
                                "/api/v1/trips/*/seats/booked"
                        ).hasAnyRole("VIGNESH", "PRIYA")

                        // ── PRIYADHARSHINI: Trip + Review ────────────────────────────
                        // /api/v1/trips/**          covers: POST, GET, GET/{id}, GET/search,
                        //                                   PUT/{id}, PATCH/{id}/close,
                        //                                   GET/{id}/seats, GET/{id}/seats/booked,
                        //                                   GET/{id}/seats/available,
                        //                                   POST/{id}/reviews, GET/{id}/reviews
                        //                           (bookings under /trips/ already claimed above)
                        // /api/v1/reviews/**        covers: DELETE /reviews/{reviewId}
                        // Add BEFORE the .requestMatchers("/api/v1/trips/**").hasRole("PRIYA") line:
                        .requestMatchers(
                                "/api/v1/trips/*/seats",
                                "/api/v1/trips/*/seats/available",
                                "/api/v1/trips/*/seats/booked"
                        ).hasAnyRole("VIGNESH", "PRIYA")
                        .requestMatchers("/api/v1/trips/**").hasRole("PRIYA")
                        .requestMatchers("/api/v1/reviews/**").hasRole("PRIYA")

                        // Anything else → must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.realmName("BusTicketBookingSystem"));

        return http.build();
    }
}