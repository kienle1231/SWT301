# System Test Cases cho VietCulture

## Chức năng đăng nhập (Login)

| Test Case ID | Tên Test Case | Mô tả | Điều kiện tiên quyết | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|-------------|--------------|-------|---------------------|-------------------|------------------|----------------|------------|
| TC_LOGIN_001 | Đăng nhập thành công | Kiểm tra đăng nhập với thông tin hợp lệ | Người dùng đã đăng ký | 1. Mở trang đăng nhập<br>2. Nhập email hợp lệ<br>3. Nhập mật khẩu hợp lệ<br>4. Nhấn nút đăng nhập | Đăng nhập thành công và chuyển đến trang chủ | | |
| TC_LOGIN_002 | Đăng nhập với email không tồn tại | Kiểm tra đăng nhập với email không tồn tại | - | 1. Mở trang đăng nhập<br>2. Nhập email không tồn tại<br>3. Nhập mật khẩu<br>4. Nhấn nút đăng nhập | Hiển thị thông báo lỗi | | |
| TC_LOGIN_003 | Đăng nhập với mật khẩu sai | Kiểm tra đăng nhập với email đúng nhưng mật khẩu sai | Người dùng đã đăng ký | 1. Mở trang đăng nhập<br>2. Nhập email hợp lệ<br>3. Nhập mật khẩu sai<br>4. Nhấn nút đăng nhập | Hiển thị thông báo lỗi | | |
| TC_LOGIN_004 | Đăng nhập với email trống | Kiểm tra đăng nhập với email trống | - | 1. Mở trang đăng nhập<br>2. Để trống email<br>3. Nhập mật khẩu<br>4. Nhấn nút đăng nhập | Hiển thị thông báo lỗi | | |
| TC_LOGIN_005 | Đăng nhập với mật khẩu trống | Kiểm tra đăng nhập với mật khẩu trống | - | 1. Mở trang đăng nhập<br>2. Nhập email hợp lệ<br>3. Để trống mật khẩu<br>4. Nhấn nút đăng nhập | Hiển thị thông báo lỗi | | |
| TC_LOGIN_006 | Đăng nhập với email và mật khẩu trống | Kiểm tra đăng nhập với email và mật khẩu trống | - | 1. Mở trang đăng nhập<br>2. Để trống email<br>3. Để trống mật khẩu<br>4. Nhấn nút đăng nhập | Hiển thị thông báo lỗi | | |
| TC_LOGIN_007 | Đăng nhập với email sai định dạng | Kiểm tra đăng nhập với email sai định dạng | - | 1. Mở trang đăng nhập<br>2. Nhập email sai định dạng<br>3. Nhập mật khẩu<br>4. Nhấn nút đăng nhập | Hiển thị thông báo lỗi | | |

## Chức năng cập nhật thông tin cá nhân (Profile)

| Test Case ID | Tên Test Case | Mô tả | Điều kiện tiên quyết | Các bước thực hiện | Kết quả mong đợi | Kết quả thực tế | Trạng thái |
|-------------|--------------|-------|---------------------|-------------------|------------------|----------------|------------|
| TC_PROFILE_001 | Cập nhật thông tin cá nhân thành công | Kiểm tra cập nhật thông tin cá nhân với dữ liệu hợp lệ | Người dùng đã đăng nhập | 1. Mở trang thông tin cá nhân<br>2. Nhập tên đầy đủ<br>3. Nhập số điện thoại<br>4. Nhập địa chỉ<br>5. Nhấn nút cập nhật | Hiển thị thông báo cập nhật thành công và dữ liệu được cập nhật | | |
| TC_PROFILE_002 | Upload avatar thành công | Kiểm tra tính năng upload avatar | Người dùng đã đăng nhập | 1. Mở trang thông tin cá nhân<br>2. Chọn file ảnh hợp lệ<br>3. Nhấn nút cập nhật | Hiển thị thông báo cập nhật thành công và avatar được cập nhật | | |
| TC_PROFILE_003 | Cập nhật với số điện thoại trống | Kiểm tra cập nhật khi để trống số điện thoại | Người dùng đã đăng nhập | 1. Mở trang thông tin cá nhân<br>2. Nhập tên đầy đủ<br>3. Để trống số điện thoại<br>4. Nhập địa chỉ<br>5. Nhấn nút cập nhật | Hiển thị thông báo lỗi | | |
| TC_PROFILE_004 | Cập nhật với tên trống | Kiểm tra cập nhật khi để trống tên | Người dùng đã đăng nhập | 1. Mở trang thông tin cá nhân<br>2. Để trống tên<br>3. Nhập số điện thoại<br>4. Nhập địa chỉ<br>5. Nhấn nút cập nhật | Hiển thị thông báo lỗi | | |
| TC_PROFILE_005 | Upload file không phải ảnh | Kiểm tra upload avatar với file không phải ảnh | Người dùng đã đăng nhập | 1. Mở trang thông tin cá nhân<br>2. Chọn file không phải ảnh (vd: .txt, .pdf)<br>3. Nhấn nút cập nhật | Hiển thị thông báo lỗi | | |
| TC_PROFILE_006 | Upload ảnh quá kích thước cho phép | Kiểm tra upload avatar với ảnh có kích thước quá lớn | Người dùng đã đăng nhập | 1. Mở trang thông tin cá nhân<br>2. Chọn file ảnh có kích thước > 5MB<br>3. Nhấn nút cập nhật | Hiển thị thông báo lỗi | | | 