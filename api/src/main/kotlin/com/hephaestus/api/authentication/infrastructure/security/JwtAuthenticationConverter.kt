package com.hephaestus.api.authentication.infrastructure.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationConverter : Converter<Jwt, AbstractAuthenticationToken> {
    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val subject = jwt.subject ?: throw IllegalArgumentException("JWT subject is missing")

        val authorities = jwt.getClaimAsStringList("authorities")?.map(::SimpleGrantedAuthority) ?: emptyList()

        return UsernamePasswordAuthenticationToken(subject, jwt, authorities)
    }

}