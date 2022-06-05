package application.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
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
                .antMatchers("/static/**" , "/user/**" , "/favicon.ico" , "/error" , "/resources/**" ,
                        "/js/**" , "/css/**" , "/fonts/**" , "/personal/**" , "/manager/**" ,
                        "/all/**" , "/websocket/**" , "/stomp/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
        http
                .sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry());
        http
                .authorizeRequests()
                //Access for public pages
                .antMatchers("/api/all/**").permitAll()
                //Access for getting an authentication pages
                .antMatchers(HttpMethod.GET , "/api/authentication/**").permitAll()
                //Access for sending an authorizing requests
                .antMatchers(HttpMethod.POST , "/api/authentication/user/login").permitAll()
                //Access for confirming a register by confirmation code
                .antMatchers(HttpMethod.POST , "/api/user/confirm").permitAll()
                //Access for registering a user model in system
                .antMatchers(HttpMethod.POST , "/api/user/register").permitAll()
                //Access for restoring password
                .antMatchers(HttpMethod.PUT, "/api/user/update/password").permitAll()
                //Access for sending a verification code
                .antMatchers(HttpMethod.POST , "/api/mail/verification").permitAll()
                //Access for checking credentials on repeats in system
                .antMatchers(HttpMethod.GET , "/api/manager/credentials/reserved").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(new SecuritySuccessHandlerEntity())
                //URL for login page
                .loginProcessingUrl("/api/authentication/user/login")
                //Redirect to [] if login is failed
                .failureUrl("/api/authentication/user/login?credentials_good=false")
                .loginPage("/api/authentication/user/login").permitAll()
                //Redirect to [] if login is successful
                .defaultSuccessUrl("/api/user/personal" , true)
                .and()
                .logout()
                //Way to logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/authentication/user/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                //Clearing cookie - vulnerability
                .deleteCookies("JSESSIONID" , AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY)
                //Redirect to [] if logout successful
                .logoutSuccessUrl("/api/authentication/user/login?logout=true");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
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