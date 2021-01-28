package eu.musicnova.musicnova.web


import com.fasterxml.jackson.databind.ObjectMapper
import eu.musicnova.webshared.RESTLoginRequestResponse
import eu.musicnova.webshared.LoginRequestResponseStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
class WebAuthConfig(
    val objectMapper: ObjectMapper
) : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("admin").password(passwordEncoder.encode("123456")).roles("USER")


    }

    @Bean
    fun passwordEndcoder() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.headers()
            .cacheControl()
            .disable()
            .and()

            .authorizeRequests()
            .antMatchers(
                "/resources/**",
                "/internal/login",
                "/internal/logout",
                "/favicon.ico"
            ).permitAll()

            .and()//---------------------------------
            .authorizeRequests()
            .antMatchers("/internal/**")
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler { request, response, accessDeniedException ->
                response.status = 403
            }
            .and()//----------------------------------------
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/internal/login")
            .successForwardUrl("/")
            .failureForwardUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")

            .permitAll()
            .failureHandler { request, response, exception ->
                response.contentType = "application/json"
                objectMapper.writeValue(
                    response.writer,
                    RESTLoginRequestResponse(LoginRequestResponseStatus.WRONG_CREDENTIALS, exception.localizedMessage)
                )
            }
            .successHandler { request, response, authentication ->
                response.contentType = "application/json"
                objectMapper.writeValue(
                    response.writer, RESTLoginRequestResponse(
                        LoginRequestResponseStatus.SUCCESS
                    )
                )
            }
            .and()
            .logout()
            .logoutUrl("/internal/logout")
            .and()
            .csrf().disable() //TODO("add csrf protection")
        //.cors().disable()

    }

}