package application.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Autowired
    ApplicationSecurityConfiguration
            (@Qualifier("userDetailsImplementationService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/static/**" , "/user/**" , "/uploads/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //Access for getting an authentication pages
                .antMatchers(HttpMethod.GET , "/api/authentication/**").permitAll()
                //Access for sending an authorizing requests
                .antMatchers(HttpMethod.POST , "/api/authentication/user/login").permitAll()
                //Access for confirming a register by confirmation code
                .antMatchers(HttpMethod.POST , "/api/user/confirm").permitAll()
                //Access for registering a user model in system
                .antMatchers(HttpMethod.POST , "/api/user/register").permitAll()
                //Access for sending a verification code
                .antMatchers(HttpMethod.POST , "/api/mail/verification").permitAll()
                //Access for checking credentials on repeats in system
                .antMatchers(HttpMethod.GET , "/api/manager/credentials/reserved").permitAll()
                //Access for restoring password
                .antMatchers(HttpMethod.GET , "/api/authentication/user/restore/password").permitAll()
                //Access for restoring password
                .antMatchers(HttpMethod.PUT, "/api/user/update/password").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(new SecuritySuccessHandlerEntity())
                //URL for login page
                .loginProcessingUrl("/api/authentication/user/login")
                .loginPage("/api/authentication/user/login").permitAll()
                //Redirect to [] if login is successful
                .defaultSuccessUrl("/api/user/personal")
                .and()
                .logout()
                //Way to logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/authentication/user/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                //Clearing cookie - vulnerability
                .deleteCookies("JSESSIONID")
                //Redirect to [] if logout successful
                .logoutSuccessUrl("/api/authentication/user/login");
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}