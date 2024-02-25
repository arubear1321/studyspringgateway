import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {
    private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        logger.info("Configuring SecurityWebFilterChain")

        http
            .csrf { it.disable() }
            .authorizeExchange {
                it.anyExchange().permitAll()
            }
            .formLogin{ it.disable() }
            .httpBasic{ it.disable() }

        return http.build()

    }
}