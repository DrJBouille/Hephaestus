package com.hephaestus.api.authentication.infrastructure.security

import com.hephaestus.api.authentication.domain.Authority
import com.hephaestus.api.authentication.infrastructure.configuration.JwtProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthenticationConverter: JwtAuthenticationConverter,
    private val pluginApiKeyFilter: PluginApiKeyFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userDetailsService: UserDetailsService, passwordEncoder: PasswordEncoder): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }

    @Bean
    fun authenticationManager(authenticationProvider: DaoAuthenticationProvider): AuthenticationManager = ProviderManager(authenticationProvider)

    @Bean
    fun jwtDecoder(properties: JwtProperties): JwtDecoder {
        val key = SecretKeySpec(
            properties.secret.toByteArray(),
            "HmacSHA256",
        )

        return NimbusJwtDecoder
            .withSecretKey(key)
            .macAlgorithm(MacAlgorithm.HS256)
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:4200")
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }

    @Bean
    fun securityFilerChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/actuator/health",
                        "/api/auth/login",
                        "/api/auth/register",
                    ).permitAll()

                    .requestMatchers(HttpMethod.GET, "/api/worlds/*")
                    .hasAuthority(Authority.WORLD_READ.name)

                    .requestMatchers(HttpMethod.POST, "/api/worlds")
                    .hasAuthority(Authority.WORLD_CREATE.name)

                    .requestMatchers(HttpMethod.POST, "/api/worlds/upload/*")
                    .hasAuthority(Authority.WORLD_UPLOAD.name)

                    .requestMatchers(HttpMethod.GET, "/api/worlds/download/*")
                    .hasAuthority(Authority.WORLD_DOWNLOAD.name)

                    .requestMatchers(HttpMethod.POST, "/api/worlds/archive/*")
                    .hasAuthority(Authority.WORLD_ARCHIVE.name)

                    .anyRequest()
                    .authenticated()
            }
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(
                        jwtAuthenticationConverter
                    )
                }
            }
            .addFilterBefore(
                pluginApiKeyFilter,
                BearerTokenAuthenticationFilter::class.java,
            )


        return http.build()
    }
}