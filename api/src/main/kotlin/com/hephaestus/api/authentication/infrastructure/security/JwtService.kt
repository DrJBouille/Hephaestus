package com.hephaestus.api.authentication.infrastructure.security

import com.hephaestus.api.authentication.infrastructure.configuration.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.Instant
import java.util.UUID
import javax.crypto.SecretKey

@Service
class JwtService(properties: JwtProperties) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(properties.secret.toByteArray())
    private val expirationSeconds = properties.expiration

    fun generate(authentication: Authentication): String {
        val user = authentication.principal as HephaestusUserDetails
        val now: Instant = Instant.now()

        return Jwts.builder()
            .subject(user.userId.toString())
            .claim(
                "authorities",
                user.authorities.map { it.authority }
            )
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(expirationSeconds)))
            .signWith(secretKey)
            .compact()
    }

    fun extractUserId(token: String): UUID = parseClaims(token).subject.let(UUID::fromString)

    fun isValid(token: String): Boolean = try {
        parseClaims(token)
        true
    } catch (_: Exception) {
        false
    }

    private fun parseClaims(token: String): Claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .payload
}