# BTL-LTW-QuanLyDaoTao
## Requirement
### Mô tả hệ thống bằng ngôn ngữ tự nhiên
#### Mục đích, phạm vi của hệ thống:
- Kiểu ứng dụng: Dành cho máy tính, điện thoại có kết nối Internet và trình duyệt Web.
- Phạm vi: Nội bộ một tổ chức giáo dục.
- Chỉ có các nhân viên sau được dùng:
  - Người quản lý.
  - Sinh viên.
  - Giảng viên.
#### Người dùng và chức năng họ sẽ sử dụng:
- Người dùng hệ thống:
  - Đăng nhập
  - Đăng xuất
  - Đổi mật khẩu
- Người quản lý: 
  - Các chức năng của người dùng.
  - Quản lý người dùng hệ thống.
  - Quản lý khoa.
  - Quản lý sinh viên.
  - Quản lý giảng viên.
  - Quản lý phòng học.
  - Quản lý môn học.
  - Quản lý kì học.
  - Quản lý lớp học phần.
  - Quản lý buổi học
- Sinh viên:
  - Các chức năng của người dùng.
  - Xem kì học. 
  - Xem lớp học phần.  
  - Đăng ký lớp học phần (Các môn trong kì học là cố định, chỉ đăng ký lớp học phần).
  - Xem buổi học.
- Giảng viên:
  - Các chức năng của người dùng.
  - Xem lớp học.
  - Nhập điểm cho các lớp học.
  - Xem buổi dạy (buổi học).
#### Chi tiết hoạt động từng chức năng:
#### Các thông tin cần phải xử lý:
  - Người dùng: username, password, role.
  - Khoa: departmentName, description.
  - Người quản lý: fullName.
  - Sinh viên: studentId, fullName, citizenId, email, tel.
  - Giảng viên: teacherId, fullName, citizenId, email, tel.
  - Kì học: termName (Spring2022, Fall2022,...etc), startDate, endDate.
  - Môn học: subjectName, description.
  - Lớp học phần: ...
  - Buổi học: date, time.
  - Phòng học: roomName, description.
#### Quan hệ giữa các thông tin cần phải xử lý:
  - Một khoa có nhiều giảng viên.
  - Một khoa có nhiều sinh viên.
  - Một khoa có nhiều môn học.
  - Một kì học có nhiều môn học.
  - Một môn học có nhiều lớp học phần.
  - Một môn học có nhiều giảng viên dạy, một giảng viên dạy nhiều môn học.
  - Một lớp học phần có nhiều buổi học.
  - Một lớp học phần học tại một phòng học.
  - Một sinh viên có nhiều lớp học phần, một lớp học phần có nhiều sinh viên.
  - Một giảng viên dạy nhiều lớp học phần.
  - Một sinh viên có nhiều kì học, một kì học có nhiều môn học.
