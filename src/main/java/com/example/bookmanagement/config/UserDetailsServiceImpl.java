package com.example.bookmanagement.config;

import com.example.bookmanagement.model.User;
import com.example.bookmanagement.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//UserDetailsServiceđược sử dụng DaoAuthenticationProvider để lấy tên người dùng, mật khẩu và các thuộc tính khác để
// xác thực bằng tên người dùng và mật khẩu

//Lớp UserDetailsServiceImpl cài đặt interface UserDetailsService, và cài đặt phương thức loadUserByUserName().
  //  Phương thức này sẽ được gọi bởi Spring Security để lấy ra thông tin của tài khoản có username là username được nhập từ màn hình login.
  //  Thông tin của tài khoản có thể lấy ra từ nhiều nguồn và được tổng hợp lại ở phương thức này để trả về một đối tượng duy nhất là cài đặt
  //  của UserDetails. Ở ví dụ này, để đơn giản ta sẽ hard code thông tin của tài khoản với username là codertiensinh với mật khẩu tương ứng là
   // a12345678 và có khả năng tương tác với hệ thống dưới quyền là ROLE_TEACHER. Còn lại trên thực tế, đây chính là chỗ mà ta
    // gọi các hàm để lấy thông tin từ các database.

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
       if (user == null) throw new UsernameNotFoundException("");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}

//UserDetailsService interface có một phương thức để lấy thông tin người dùng bằng username và trả về một đối tượng UserDetails 
//mà Spring Security có thể sử dụng để xác thực và xác nhận.
//UserDetails chứa các thông tin cần thiết (như: username, password, authorities – quyền hạn) để xây dựng một đối tượng Authentication.

//Từ thông tin username và password trong request đăng nhập tạo ra instance của UsernamePasswordAuthenticationToken là 
//implement của interface Authentication (để biết điều này phải đọc sâu vào 
//	trong code mới thấy được). Rồi AuthenticationManager sẽ sử dụng instance này để xác thực tài khoản đăng nhập.