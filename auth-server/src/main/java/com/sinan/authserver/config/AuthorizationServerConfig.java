package com.sinan.authserver.config;

import com.sinan.authserver.exception.AuthExceptionEntryPoint;
import com.sinan.authserver.resolver.CustomeRedirectResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private TokenStore jwtTokenStore;
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
        security.authenticationEntryPoint(new AuthExceptionEntryPoint());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.withClientDetails(clientDetails());
        clients.inMemory()
                .withClient("android")
                .scopes("read")
                .secret(passwordEncoder.encode("android"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .and()
                .withClient("webapp")
                .scopes("read")
                .authorizedGrantTypes("implicit")
                .and()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("read")
                .and()
                .withClient("client_1")
                .scopes("all")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .redirectUris("http://www.baidu.com")
                .autoApprove(true)
                .and()
                .withClient("client_2")
                .scopes("all")
                .secret(passwordEncoder.encode("123456"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .redirectUris("http://localhost:9090/**")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .redirectResolver(new CustomeRedirectResolver())
                .authenticationManager(authenticationManager)
//                .userDetailsService(userService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(new InMemoryTokenStore());
//                .tokenStore(jwtTokenStore) //配置令牌存储策略
//                .accessTokenConverter(jwtAccessTokenConverter);
        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);
    }

//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
//        accessTokenConverter.setSigningKey("test_key");//配置JWT使用的秘钥
//        return accessTokenConverter;
//    }

}
