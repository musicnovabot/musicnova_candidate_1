package eu.musicnova.musicnova.web


import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariDataSource
import eu.musicnova.musicnova.MusicnovaUserDetailsManager
import eu.musicnova.webshared.RESTLoginRequestResponse
import eu.musicnova.webshared.LoginRequestResponseStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@EnableWebSecurity
class WebAuthConfig(
    private val objectMapper: ObjectMapper,
    private val musicnovaUserDetailsManager: MusicnovaUserDetailsManager,
    private val dataSource: HikariDataSource
) : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    override fun configure(auth: AuthenticationManagerBuilder) {
        //auth.apply(musicnovaUserDetailsManager)
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("123456")).roles("USER")

        //auth.jdbcAuthentication().withDefaultSchema()
        //auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().withUser("admin").password(passwordEncoder.encode("123456")).roles("USER")

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
        //.cors().disable()

    }

}