package com.task.ably.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return filterChain.doFilter(request, response)
        val (tokenType, token) = splitToTokenFormat(authorization)

        if (tokenType != BEARER) {
            throw IllegalArgumentException("로그인 정보가 정확하지 않습니다")
        }

        if (!isValidToken(token)) {
            throw IllegalArgumentException("로그인 정보가 정확하지 않습니다")
        }

        filterChain.doFilter(request, response)
    }

    fun createToken(payload: String): String {
        val claims: Claims = Jwts.claims().setSubject(payload)
        val now = Date()
        val expiration = Date(now.time + expirationInMilliseconds)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        return try {
            getClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun getClaimsJws(token: String) = Jwts.parserBuilder()
        .setSigningKey(signingKey.encoded)
        .build()
        .parseClaimsJws(token)

    private fun splitToTokenFormat(authorization: String): Pair<String, String> {
        return try {
            val tokenFormat = authorization.split(" ")
            tokenFormat[0] to tokenFormat[1]
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("로그인 정보가 정확하지 않습니다")
        }
    }
}

private val signingKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
private const val TWELVE_HOURS_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 12
private const val expirationInMilliseconds: Long = TWELVE_HOURS_IN_MILLISECONDS
private const val BEARER = "Bearer"
