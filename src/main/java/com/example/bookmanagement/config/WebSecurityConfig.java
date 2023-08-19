package com.example.bookmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //kích hoạt tính năng Spring Security trên ứng dụng Web
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  //  class WebSecurityConfig để là nơi tập trung các xử lý các thông tin liên quan tới security.


    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        // Tạo ra user trong bộ nhớ
//        // lưu ý, chỉ sử dụng cách này để minh họa
//        // Còn thực tế chúng ta sẽ kiểm tra user trong csdl
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
//                        .username("loda")
//                        .password("loda")
//                        .roles("USER") // phân quyền là người dùng.
//                        .build()
//        );
//        return manager;
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                 .antMatchers("/book-photos/**", "/css/**", "/js/**", "/registration", "/books", "/", "/login", "/books/view/**").permitAll() //cho phep truy cập vào địa chỉ này
                 .anyRequest().authenticated() //Tất cả các request khác đều cần phải xác thực mới được truy cập
                 .and()
                .formLogin()
                 .loginPage("/login")// dùng trang login đc custom
                 .permitAll()// tất cả đều đc truy cập vào địa chỉ này để login 
                 .and()
                .logout() // cho phép log out mặc định tạo ra 1 trang logout vs dịa chỉ logout
                 .permitAll();
    }
//Một instance mà nó thực hiện AuthenticationManager có thể làm 1 trong 3 việc sau khi nó implement method authenticate(): 1.
// Trả về 1 object Authentication (thường thì với trường hợp attribute authenticated=true) nếu nó kiểm tra được các giá trị input là hợp lệ.
// 2. Trả về 1 exception (AuthenticationException) nếu các giá trị input không hợp lệ 3. Trả về null nếu nó không thể xử lý.
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
  //  AuthenticationManager sử dụng DaoAuthenticationProvider cùng với UserDetailsService & PasswordEncoder validate instance 
   // của UsernamePasswordAuthenticationToken. Nếu thành công, AuthenticationManager trả về một đối tượng Authentication đầy đủ 
   // thông tin (bao gồm cả các quyền).
    
    
  //  Method thứ 3 là configureGlobal(AuthenticationManagerBuilder auth) throws Exception Trong Spring Security có
  //  một object quan trọng đó là UserDetailsService.
 //   Đây là object của Spring, nó nắm giữ thông tin quan trọng như Username này là ai trong hệ thống , UserName này
  //  có quyền gì. Chúng ta sẽ đi chi tiết trong bước 5 tiếp theo để hiểu nó làm được .
    @Autowired
  //  Method thứ 3 là configureGlobal(AuthenticationManagerBuilder auth) throws Exception Trong Spring Security có một object quan trọng đó là
  //  UserDetailsService. Đây là object của Spring, nó nắm giữ thông tin quan trọng như Username này là ai trong hệ thống ,
   // UserName này có quyền gì. Chúng ta sẽ đi chi tiết trong  tiếp theo để hiểu nó làm được .
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}