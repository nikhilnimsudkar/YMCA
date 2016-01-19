package com.ymca;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
@EnableAutoConfiguration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Resource(name="portalAuthenticationProvider")
	@Resource
    private UserDetailsService userService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(userService)
            .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/recovery/**").permitAll()
                .antMatchers("/location/**").permitAll()
                .antMatchers("/passRegistration/**").permitAll()
                .antMatchers("/signup/**").permitAll()     
                .antMatchers("/register/**").permitAll()     
                .antMatchers("/view_membership/**").permitAll()     
                .antMatchers("/trialPass/**").permitAll()      
                .antMatchers("/trialPassRegister/**").permitAll()
                .antMatchers("/viewTandC").permitAll()
                .antMatchers("/isEmailExists/**").permitAll()  
                .antMatchers("/ProcessDirectPayment/**").permitAll()
                .antMatchers("/processPaymentMethod/**").permitAll()
                .antMatchers("/shopping/**").permitAll()
                .antMatchers("/cart/**").permitAll()
                .antMatchers("/getItems").permitAll()
                .antMatchers("/getEventItems").permitAll()
                .antMatchers("/getContacts").permitAll()
                .antMatchers("/programRegistration").permitAll()
                .antMatchers("/getProgramDetails").permitAll()
                .antMatchers("/getEventDetails").permitAll()
                .antMatchers("/getProductDetailsByLocation/**").permitAll()
                .antMatchers("/loginPopup").permitAll()
                .antMatchers("/quickSignIn").permitAll()
                .antMatchers("/memberAuth").permitAll()
                .antMatchers("/viewPaymentForm").permitAll()  
                .antMatchers("/becomeMember").permitAll()
                .antMatchers("/becomeMemberRegister").permitAll()
                .antMatchers("/facilityBooking").permitAll()
                .antMatchers("/redirectSuccess").permitAll()  
                .antMatchers("/redirectFailure").permitAll()
                .antMatchers("/eventRegistration").permitAll()
                .antMatchers("/processACHPayment").permitAll() 
                .antMatchers("/getPromodetails").permitAll() 
                .antMatchers("/trailPassReg").permitAll()
                .antMatchers("/getTrailProductDetailsByLocation/**").permitAll()
                .antMatchers("/becomeTPMemberRegister").permitAll()
                .antMatchers("/search/**").permitAll()
                .antMatchers("/camp").permitAll()
                .antMatchers("/camp/").permitAll()
                .antMatchers("/program").permitAll()
                .antMatchers("/program/").permitAll()
                .antMatchers("/event").permitAll()
                .antMatchers("/event/").permitAll()
                .antMatchers("/childcare").permitAll()
                .antMatchers("/childcare/").permitAll()
                .antMatchers("/searchchildcare").permitAll()
                .antMatchers("/searchInservice").permitAll()
                .antMatchers("/searchAfterschool").permitAll()
                .antMatchers("/getPricingruleByItemDetailsIdAndTier").permitAll()
                .antMatchers("/camp/search").permitAll()
                .antMatchers("/sc/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/portal/ws/**").permitAll()
                .antMatchers("/donation**").permitAll()
                .antMatchers("/isEmailExistsWithUserData**").permitAll()
                .antMatchers("/getPromoMap**").permitAll()
                .antMatchers("/promoSignup**").permitAll()
                .antMatchers("/psu**").permitAll()
                //.antMatchers("/testMethod**").permitAll()	//	This is test path, need to remove 
                .antMatchers("/getMembershipProductDetail**").permitAll()
                .antMatchers("/getSetupFeeByItemDetailIdAndPartyId").permitAll()
                .antMatchers("/getRegistrationFeeByItemDetailIdAndPartyId").permitAll()
                .anyRequest().authenticated();
        
        http.csrf().disable();
       /* http
        .antMatcher("/becomeMember/**")
        .csrf().disable();*/
        
        
        /*.antMatcher("/viewPaymentForm/**")
        .antMatcher("/processPaymentByTokenId/**")
        .antMatcher("/memberAuth/**")
        .antMatcher("/viewPaymentInformationByLoggedInUser/**")
        .antMatcher("/getAccountDetailsByEmail/**")        */
        
        //.httpBasic();
        //http.headers().frameOptions().addHeaderWriter(new StaticHeadersWriter("X-Frame-Options","SAMEORIGIN"));
        //http.headers().frameOptions().disable();
        
        
       // http.headers()
       //   .addHeaderWriter(new StaticHeaderWriter("X-Content-Security-Policy","default-src 'self'"))
        //  .addHeaderWriter(new StaticHeaderWriter("X-WebKit-CSP","default-src 'self'"));
        //http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.ALLOW_FROM));
        http.headers().disable();
        
        http
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home",true)
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
}
