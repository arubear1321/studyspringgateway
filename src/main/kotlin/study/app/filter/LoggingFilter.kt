package study.app.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class LoggingFilter : AbstractGatewayFilterFactory<LoggingFilter.Config>(Config::class.java) {
    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)

    override fun apply(config: Config?): GatewayFilter? {
        return OrderedGatewayFilter({ exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            val response = exchange.response
            logger.info("requestID==============={}", request.id)
            chain.filter(exchange)
                .then<Void>(Mono.fromRunnable<Void> {
                    logger.info("responseID==============={}", request.id)
                    logger.info("responseStatus==============={}", response.statusCode)
                })
        }, Ordered.HIGHEST_PRECEDENCE)
    }

    class Config

}