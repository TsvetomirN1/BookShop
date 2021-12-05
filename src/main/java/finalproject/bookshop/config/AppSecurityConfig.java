package finalproject.bookshop.config;


import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public AppSecurityConfig(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.
            authorizeRequests().
            // with this line we allow access to all static resources
                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().

            // The following block code gives a permission to anyone:
                    antMatchers(

                    //Global
                    "/",
                    "/login",
                    "/register",
                    "/about",
                    "/library",

                    //Rest Endpoints
                    "/api/books/all",
                    "/api/categories/all",
                    "/api/authors/all"

            )
            .permitAll().

            // we permit the page below only for admin users
                    antMatchers("/statistics","/admin/books/add").hasRole(UserRoleEnum.ADMIN.name()).
            // next we forbid all other pages for unauthenticated users.
                    antMatchers("/**").authenticated().
            and().
            // configure login with login HTML forms with two input fields
                    formLogin().
            // our login page is located at http://<serveraddress>>:<port>/users/login
                    loginPage("/login").
            // this is the name of the <input.> in the login form where the user enters her email/username/etc
            // the value of this input will be presented to our User details service
            // those that want to name the input field differently, e.g. email may change the value below
                    usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
            // the name of the <input...> HTML filed that keeps the password
                    passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
            // The place where we should land in case that the login is successful
                    defaultSuccessUrl("/").
            // the place where I should land if the login is NOT successful
                    failureForwardUrl("/login-error").
            and().
            logout().
            // This is the URL which spring will implement for me and will log the user out.
                    logoutUrl("/logout").
            // where to go after the logout.
                    logoutSuccessUrl("/").
            // remove the session from server
                    invalidateHttpSession(true).
            //delete the cookie that references my session
                    deleteCookies("JSESSIONID");

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.
        userDetailsService(userDetailsService).
        passwordEncoder(passwordEncoder);

  }
}
