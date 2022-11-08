package br.com.danielsaes.api_aluraflix.config;

import br.com.danielsaes.api_aluraflix.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()

//                .antMatchers(HttpMethod.GET, "/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/videos/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/categorias/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/usuarios/**").permitAll()

                .antMatchers(HttpMethod.POST, "/auth").permitAll()

                .antMatchers(HttpMethod.GET,"/videos/**").hasAnyRole("USUARIO","ADMIN")
                .antMatchers(HttpMethod.GET,"/categorias/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/usuarios/**").hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.POST,"/videos/**").hasAnyRole("USUARIO","ADMIN")
                .antMatchers(HttpMethod.POST,"/categorias/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/usuarios/**").hasAnyRole("ADMIN")

                .antMatchers(HttpMethod.PUT,"/videos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/categorias/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/usuarios/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.DELETE,"/videos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/categorias/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/usuarios/**").hasRole("ADMIN")


                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
                        UsernamePasswordAuthenticationFilter.class);


        return http.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
}
