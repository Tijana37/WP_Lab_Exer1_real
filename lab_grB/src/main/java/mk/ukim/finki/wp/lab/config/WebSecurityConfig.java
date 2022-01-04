package mk.ukim.finki.wp.lab.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    //private final CustomAuthenticationFailureHandler customUsernamePasswordAuth;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        //this.customUsernamePasswordAuth = customUsernamePasswordAuth;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //--> da nema third party applications
                .authorizeRequests()
                .antMatchers("/","/register").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and() //--> if no custom implementation of /login comment next three lines
                .formLogin().loginPage("/login").permitAll()
                .successHandler((request, response, authentication) -> {
                    // run custom logics upon successful login

                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();

                    System.out.println("The user " + username + " has logged in.");
                    response.setHeader("User" , username);
                    response.sendRedirect("/courses");
                })
                .failureUrl("/login?error=BadCredentials")
                //.defaultSuccessUrl("/courses",true)
                .and()
                .logout().logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/login")
                ;
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                )
        );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //---------------inMemoryAUTH--------------------
        auth.inMemoryAuthentication()
                .withUser("Tijana")
                .password(passwordEncoder.encode("tijana"))
                .authorities("ROLE_USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ROLE_ADMIN");

        //--------------customAUTH----------------------
       // auth.authenticationProvider(customUsernamePasswordAuth);

    }


}
