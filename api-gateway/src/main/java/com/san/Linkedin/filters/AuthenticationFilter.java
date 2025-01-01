package com.san.linkedin.filters;


import com.san.Linkedin.JWTService;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final JWTService jwtService;



    public AuthenticationFilter(JWTService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Login request: {}",exchange.getRequest().getURI());

            final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(tokenHeader == null || !tokenHeader.startsWith("Bearer")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                log.error("Authorizaton token header not found");

                return exchange.getResponse().setComplete();
            }
            try{

                final String token = tokenHeader.split("Bearer ")[1];
                String userId = jwtService.getUserIdFromToken(token);
                ServerWebExchange modifiedExchange = exchange.
                        mutate().
                        request(r -> r.header("X-User-Id",userId))
                        .build();

                return chain.filter(exchange);

            }catch (JwtException e)
            {
                log.error("JWt exception: {}",e.getLocalizedMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    public static class Config{

    }
}
