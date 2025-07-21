# Automation Testing cho VietCulture

Dự án kiểm thử tự động cho ứng dụng web VietCulture sử dụng Selenium WebDriver và JUnit 5.

## Cấu trúc dự án

```
Exercise6_VietCulture/
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   │   ├── BasePage.java        # Lớp cơ sở cho tất cả các page objects
│       │   │   ├── LoginPage.java       # Page object cho trang đăng nhập
│       │   │   └── ProfilePage.java     # Page object cho trang thông tin cá nhân
│       │   ├── tests/
│       │   │   ├── BaseTest.java        # Lớp cơ sở cho tất cả các test classes
│       │   │   ├── LoginTest.java       # Test class cho chức năng đăng nhập
│       │   │   └── ProfileTest.java     # Test class cho chức năng thông tin cá nhân
│       │   └── utils/
│       │       └── DriverFactory.java   # Tạo và cấu hình WebDriver
│       └── resources/
│           ├── login-data.csv           # Dữ liệu kiểm thử cho đăng nhập
│           └── avatar.jpg               # Ảnh mẫu cho kiểm thử upload
├── pom.xml                              # Cấu hình Maven
├── README.md                            # Tài liệu mô tả
└── SystemTest.md                        # Tài liệu System Test Cases
```

## Mô tả chức năng được kiểm thử

1. **Đăng nhập (Login):**
   - Đăng nhập thành công với thông tin hợp lệ
   - Xử lý các trường hợp lỗi (email/mật khẩu không hợp lệ)
   - Kiểm thử với nhiều bộ dữ liệu khác nhau

2. **Thông tin cá nhân (Profile):**
   - Cập nhật thông tin cá nhân thành công
   - Upload ảnh đại diện (avatar)
   - Xử lý các trường hợp lỗi (dữ liệu không hợp lệ)

## Cách chạy kiểm thử

1. Chuẩn bị môi trường:
   - Cài đặt JDK 17+
   - Cài đặt Maven
   - Đảm bảo ứng dụng VietCulture đang chạy tại http://localhost:8080/Travel

2. Chạy kiểm thử:
```bash
mvn clean test
```

## Mô hình Page Object

Dự án sử dụng mô hình Page Object Model (POM) để tổ chức code kiểm thử:
- **BasePage**: Lớp cơ sở cho tất cả các page objects, cung cấp các phương thức chung
- **Page Objects**: Mỗi trang trong ứng dụng có một class riêng (LoginPage, ProfilePage)
- **BaseTest**: Lớp cơ sở cho tất cả các test classes, quản lý vòng đời của WebDriver
- **Test Classes**: Mỗi chức năng có một test class riêng

## System Test Cases

Xem chi tiết các test cases trong file [SystemTest.md](SystemTest.md). 