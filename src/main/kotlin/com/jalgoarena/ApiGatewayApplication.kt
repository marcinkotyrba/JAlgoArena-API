package com.jalgoarena

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.boot.CommandLineRunner
import org.springframework.cloud.netflix.zuul.filters.RouteLocator
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@Configuration
open class JAlgoArenaApiGatewayApplication {

    private val LOG = LoggerFactory.getLogger(this.javaClass)

    @Bean
    open fun commandLineRunner(routeLocator: RouteLocator): CommandLineRunner =
            CommandLineRunner { args ->
                routeLocator.routes
                        .forEach { route ->
                            LOG.info("${route.id} (${route.location}) ${route.fullPath}")
                        }
            }

    @Bean
    open fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("OPTIONS")
            addAllowedMethod("HEAD")
            addAllowedMethod("GET")
            addAllowedMethod("PUT")
            addAllowedMethod("POST")
            addAllowedMethod("DELETE")
            addAllowedMethod("PATCH")
        })
        return CorsFilter(source)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(JAlgoArenaApiGatewayApplication::class.java, *args)
}
