package com.example.bookmanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

// lớp tiện ích này chỉ chịu trách nhiệm tạo thư mục nếu không tồn tại và lưu tệp đã tải lên từ đối tượng
// MultipartFile vào một tệp trong hệ thống tệp.
public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
   // Đó là cách triển khai chức năng tải lên tệp cho ứng dụng Spring Boot hiện có. Và trong trường hợp hình ảnh, bạn đã học cách hiển thị hình ảnh trong trình duyệt. Tóm lại, đây là những điểm chính:
  //  Thêm một cột để lưu tên tệp trong bảng cơ sở dữ liệu.
   // Khai báo một trường trong lớp thực thể để lưu trữ tên của tệp đã tải lên.
   // Sử dụng đầu vào tệp trong biểu mẫu web và đặt thuộc tính enctype = ”multiart / form-data” cho thẻ biểu mẫu.
  //  Sử dụng @RequestParam và MultipartFile trong phương thức xử lý của bộ điều khiển Spring.
 //  Chúng ta sử dụng Request Param ở controller để lấy giá trị người dùng nhập trên trình duyệt
   // Sử dụng phương thức Files.copy () để sao chép tệp đã tải lên từ vị trí tạm thời vào một thư mục cố định trong hệ thống tệp.
  //  Định cấu hình Spring MVC để hiển thị thư mục tải lên để các tệp hình ảnh có thể hiển thị trong trình duyệt.
}