package com.kopec.wojciech.enginners_thesis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests().antMatchers("/wallPage").hasAnyRole("ADMIN", "USER")
//                .and()
//                .authorizeRequests().antMatchers("/login", "/resource/**").permitAll()
//                .and()
//                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
//                .loginProcessingUrl("/doLogin")
//                .successForwardUrl("/postLogin")
//                .failureUrl("/loginFailed")
//                .and()
//                .logout().logoutUrl("/doLogout").logoutSuccessUrl("/logout").permitAll()
//                .and()
//                .csrf().disable();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/login","/user-edit").permitAll()
                .antMatchers(HttpMethod.POST).authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }
}
