package com.hephaestus.api.authentication.infrastructure.security

import com.hephaestus.api.authentication.domain.Authority
import com.hephaestus.api.authentication.infrastructure.configuration.HephaestusApiProperties
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class PluginApiKeyFilter(private val properties: HephaestusApiProperties) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val apiKey = request.getHeader("X-API-Key")

        if (apiKey == properties.pluginKey) {
            val authorities = listOf(
                Authority.WORLD_CREATE.name,
                Authority.WORLD_READ.name,
                Authority.WORLD_UPLOAD.name,
                Authority.WORLD_DOWNLOAD.name,
            ).map(::SimpleGrantedAuthority)

            val authentication = UsernamePasswordAuthenticationToken(
                "minecraft-server",
                null,
                authorities
            )

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}