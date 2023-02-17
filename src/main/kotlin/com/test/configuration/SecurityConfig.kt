package com.test.configuration

import com.test.presentation.rest.ApiVersion
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


private const val GUEST = "GUEST"
private const val ADMIN = "ADMIN"


private val API_REQUESTS = arrayOf(
    "/${ApiVersion.API_V1}/*",
)

private val ADMIN_REQUESTS = arrayOf(
    "/${ApiVersion.ADMIN_API_V1}/**",
)

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.cors().and().csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(*ADMIN_REQUESTS).hasAuthority(ADMIN)
            .requestMatchers(*API_REQUESTS).hasAnyAuthority(GUEST, ADMIN)
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .and()
            .httpBasic()
            .and()
            .userDetailsService(userDetailsManager())
            .build()
    }

    @Bean
    fun userDetailsManager(): InMemoryUserDetailsManager {
        val admin = org.springframework.security.core.userdetails.User.withUsername("admin")
            .password("{noop}admin")
            .authorities(ADMIN)
            .build()
        val guest = org.springframework.security.core.userdetails.User.withUsername("guest")
            .password("{noop}guest")
            .authorities(GUEST)
            .build()
        return InMemoryUserDetailsManager(admin, guest)
    }
}
