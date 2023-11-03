package username.password.auth.usernamepasswordauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    // HttpSecurity で SecurityFilterChain を Buildする。
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // permitAll: 認証なし！
        // authenticated: 認証あり！

        // UsernamePasswordAuthenticationFilter がRespondする POST /login は開放。
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().anyRequest().authenticated();

        // formLogin() -> DefaultのLoginページ表示
        // DispatcherServletで静的コンテンツをHostするために StaticResourceConfigurationクラスを定義。
        // POST /login -> redirect -> GET / で 静的コンテンツ(/static/index.html)へ遷移する。
        http.formLogin();

        // filter -> manager へ依存
        // ✅UsernamePasswordAuthenticationFilter -> POST /login に対してだけ適用される。
        http.addFilter(new UsernamePasswordAuthenticationFilter(this.authenticationManager()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        // manager -> provider へ依存
        return new ProviderManager(this.authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // provider -> userDetailService, passwordEncoder へ依存
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
