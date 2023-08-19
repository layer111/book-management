package com.example.bookmanagement.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//Trong trường hợp tệp tải lên là hình ảnh, chúng tôi có thể hiển thị hình ảnh trong trình duyệt với một chút cấu hình. 
//Chúng ta cần hiển thị thư mục chứa các tệp đã tải lên để các máy khách (trình duyệt web) có thể truy cập.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("book-photos", registry);
    }
//cấu hình Spring MVC để cho phép truy cập vào thư mục book-photos trong hệ thống tệp,
    //với ánh xạ tới đường dẫn ngữ cảnh của ứng dụng dưới dạng / book-photos .
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
//        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
//    Sử dụng addResourceHandler
//    Chúng tôi đã sử dụng addResourceHandler (), một phiên bản đơn giản hơn và có thể được sử dụng trong một ứng dụng đơn giản, 
 //   nơi chúng tôi cần một thư mục làm đường dẫn để tham chiếu đến tất cả các tài nguyên tĩnh.
//
//    Sử dụng addResourceLocations
//    Trong các kịch bản mà chúng tôi muốn định cấu hình nhiều vị trí tài nguyên trong ứng dụng dựa trên mùa xuân.
    //Chúng ta có thể sử dụng  addResourceLocations .
}