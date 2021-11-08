package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginSuccessHandler loginSuccessHandler;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(LoginSuccessHandler loginSuccessHandler,
                                      UserDetailsService userDetailsService) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/logout").permitAll() // доступность всем
                .antMatchers("/user/**").hasAuthority("ADMIN") // разрешаем входить на /user пользователям с ролью Admin
                .antMatchers("/client").hasAuthority("USER")
                .and()
                .formLogin()  // Spring сам подставит свою логин форму
                .successHandler(loginSuccessHandler) // подключаем наш SuccessHandler для перенаправления по ролям
                .and().logout()
                .logoutUrl("/logout") //URL-адрес, запускающий выход из системы (по умолчанию "/ logout").
                .logoutSuccessUrl("/login") //URL-адрес для перенаправления после выхода из системы.
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
