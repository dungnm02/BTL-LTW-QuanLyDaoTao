# BTL-LTW-QuanLyDaoTao

## Yêu cầu

### Mô tả hệ thống bằng ngôn ngữ tự nhiên

#### Mục đích, phạm vi của hệ thống:

- Kiểu ứng dụng: Dành cho máy tính, điện thoại có kết nối Internet và trình duyệt Web.
- Phạm vi: Nội bộ một tổ chức giáo dục.
- Chỉ có các nhân viên sau được dùng:
    - Người quản lý.
    - Sinh viên.
    - Giảng viên.

#### Người dùng và chức năng họ sẽ sử dụng:

**Người dùng (ND):**

- Đăng nhập
- Đăng xuất
- Đổi mật khẩu

**Quản lý (QL):**

- Các chức năng của người dùng
- Quản lý sinh viên: Thêm, Sửa, Xóa.
- Quản lý giảng viên: Thêm, Sửa, Xóa.
- Quản lý môn học: Thêm, Sửa, Xóa; Thêm, Xóa giảng viên dạy môn này.
- Quản lý học kì: Thêm, Sửa, Xóa, quản lý lớp học phần kì học đó: Thêm, Sửa Xóa; Thêm, Xóa sinh viên trong lớp này.
- Mở đợt đăng ký lớp học phần cho kì học chưa bắt đầu.

**Sinh viên (SV):**

- Xem kết quả các lớp học phần.
- Xem buổi học các lớp học phần.
- Đăng ký lớp học phần kì học chưa bắt đầu.

**Giảng viên (GV):**

- Xem buổi học các lớp học phần GV dạy.
- Nhập điểm cho các lớp học phần đang hoạt động.

#### Chi tiết hoạt động từng chức năng:

- Quản lý sinh viên:
    - Trang quản lý sinh viên (/qly/sinhvien) gồm nút Thêm, thanh tìm kiếm theo tên, nút Tìm, bảng kết quả tìm kiếm có
      các thông tin MSV, Tên, Khoa, trên mỗi dòng của bảng có nút "Sửa" và "Xóa"
    - Thêm:
        - Trang thêm sinh viên (/qly/sinhvien/them) gồm ô nhập họ tên, ngày sinh, cccd, email, sdt, dropdown menu chọn
          khoa.
        - Quản lý nhập đủ thông tin, ấn Thêm.
        - Hệ thống sinh MSV, username, password dựa trên thông tin được nhập, lưu thông tin vào CSDL.
    - Sửa:
        - Trang sửa sinh viên (/qly/sinhvien/sua/{id}) gồm ô nhập họ tên, ngày sinh, cccd, email, sdt đã được điền sẵn
          thông tin cũ.
        - Quản lý nhập đủ thông tin, ấn Sửa.
        - Hệ thống lưu thông tin vào CSDL.
    - Xóa:
        - Trang xóa sinh viên (/qly/sinhvien/xóa/{id}) hiển thị các thông tin họ tên, ngày sinh, cccd, email, sdt, khoa
          của sinh viên.
        - Quản lý ấn Xóa.
        - Hệ thống xóa thông tin trong CSDL.
- Quản lý giảng viên:
    - Trang quản lý giảng viên (/qly/giangvien) gồm nút Thêm, thanh tìm kiếm theo tên, nút Tìm, bảng kết quả tìm kiếm có
      các thông tin MGV , Tên, Khoa, trên mỗi dòng của bảng có nút "Sửa" và "Xóa"
    - Thêm:
        - Trang thêm giảng viên (/qly/giangvien/them) gồm ô nhập họ tên, ngày sinh, cccd, email, sdt, dropdown menu chọn
          khoa.
        - Quản lý nhập đủ thông tin, ấn Thêm.
        - Hệ thống sinh MSV, username, password dựa trên thông tin được nhập, lưu thông tin vào CSDL.
    - Sửa:
        - Trang sửa giảng viên (/qly/giangvien/sua/{id}) gồm ô nhập họ tên, ngày sinh, cccd, email, sdt đã được điền sẵn
          thông tin cũ.
        - Quản lý nhập đủ thông tin, ấn Sửa.
        - Hệ thống lưu thông tin vào CSDL.
    - Xóa:
        - Trang xóa giảng viên (/qly/giangvien/xoa/{id}) hiển thị các thông tin họ tên, ngày sinh, cccd, email, sdt,
          khoa của sinh viên.
        - Quản lý ấn Xóa.
        - Hệ thống xóa thông tin trong CSDL.
- Quản lý môn học:
    - Trang quản lý giảng viên (/qly/monhoc) gồm nút Thêm, thanh tìm kiếm theo tên, nút Tìm, bảng kết quả tìm kiếm có
      các thông tin MSV, Tên, Ghi chú, Khoa, trên mỗi dòng của bảng có nút "Sửa" "Xóa", "Thêm Giảng viên" và "Xóa Giảng
      viên"
    - Thêm:
        - Trang thêm môn học (/qly/monhoc/them) gồm ô nhập tên môn học, ghi chú, dropdown menu chọn khoa, nút Thêm.
        - Quản lý nhập đủ thôn tin ấn Thêm.
        - Hệ thống lưu thông tin vào CSDL.
    - Sửa:
        - Trang sửa giảng viên (/qly/monhoc/sua/{id}) gồm ô nhập tên môn học, ghi chú đã được điền sẵn thông tin cũ.
        - Quản lý nhập đủ thông tin, ấn Sửa.
        - Hệ thống lưu thông tin vào CSDL.
    - Xóa:
        - Trang xóa giảng viên (/qly/monhoc/xoa/{id}) hiển thị các thông tin tên môn học, ghi chú của môn học.
        - Quản lý ấn Xóa.
        - Hệ thống xóa thông tin trong CSDL.
    - Thêm giảng viên:
        - Trang thêm giảng viên (/qly/monhoc/themgiangvien/{id}) hiển thị các thông tin của môn học (tên, ghi chú,
          khoa), bảng các giảng viên dạy môn học này (mã GV, tên, khoa), bảng các giảng viên không dạy môn học này (mã
          GV, tên, khoa, checkbox), nút "Xác nhận".
        - Quản lý chọn các giảng viên không dạy môn học này, ấn "Xác nhận".
        - Hệ thống lưu thông tin vào CSDL.
    - Xóa giảng viên:
        - Xóa thêm giảng viên (/qly/monhoc/xoagiangvien/{id}) hiển thị các thông tin của môn học (tên, ghi chú, khoa),
          bảng các giảng viên dạy môn học này (mã GV, tên, khoa, checkbox).
        - Quản lý chọn các giảng viên dạy môn học này, ấn "Xác nhận".
        - Hệ thống lưu thông tin vào CSDL.
- Quản lý học kì:
    - Trang quản lý học kì (/qly/hocki) gồm các nút Thêm, thanh tìm kiếm theo tên, nút Tìm, bảng kết quả tìm kiếm các
      học kì (tên, ngày bắt đầu, ngày kết thúc), trên mỗi dòng có nút Sửa, Xóa, Quản lý lớp học phần.
    - Thêm:
        - Trang thêm học kì (/qly/hocki/them) gồm ô nhập tên học kì, ô nhập ngày bắt đầu (ngày kết thúc sẽ dựa vào đây
          để tính (mặc định 1 học kì 29 tuần)), nút Thêm
        - Quản lý nhập thôn tin, ấn "Thêm"
        - Hệ thống lưu thông tin vào CSDL.
    - Sửa:
        - Trang thêm học kì (/qly/hocki/sua) gồm ô nhập tên học kì, ô nhập ngày bắt đầu đã điền sẵn thông tin cũ, nút
          Sửa,
        - Quản lý nhập thôn tin, ấn "Sửa"
        - Hệ thống lưu thông tin vào CSDL.
    - Xóa:
        - Trang thêm học kì (/qly/hocki/xoa) gồm thông tin học kì
        - Quản lý ấn "Xóa"
        - Hệ thống xóa thông tin trong CSDL.
    - Quản lý các lớp học phần:
        - Trang quản lý các lớp học phần (qly/hocki/lophocphan) gồm nút Thêm, thanh tìm kiếm lớp học phần theo tên,
          nút "Tìm", bảng kết quả tìm kiếm (khoa, môn, giảng viên, tên lớp học phần, ngày trong tuần, kíp trong ngày),
          trên mỗi hàng có nút Sửa, Xóa, Quản lý sinh viên.
        - Thêm lớp học phần:
            - Trang thêm lớp học phần (qly/hocki/lophocphan/them) gồm dropdown menu chọn khoa, dropdown menu chọn môn
              học, dropdown menu chọn giảng viên, ô nhập tên lớp học phần, dropdown menu chọn ngày trong tuần, dropdown
              menu chọn kíp trong ngày, dropdown menu chọn phòng học.
            - Qly nhập đủ thông tin ấn "Sửa"
            - Hệ thống lưu thông tin vào CSDL.
        - Sửa lớp học phần:
            - Quẳn lý ấn sửa một lớp học phần (qly/hocki/lophocphan/sua/{id}) gồm dropdown menu chọn khoa, dropdown menu
              chọn môn học, dropdown menu chọn giảng viên, ô nhập tên lớp học phần, dropdown menu chọn ngày trong tuần,
              dropdown menu chọn kíp trong ngày, dropdown menu chọn phòng học với thông tin cũ được điền sẵn
            - Qly nhập đủ thông tin ấn "Sửa"
            - Hệ thống lưu thông tin vào CSDL.
        - Xóa lớp học phần:
            - Qlý ấn xóa một lớp học phần (qly/hocki/lophocphan/sua/{id}) gồm các thông tin của lớp học phần.
            - Qly ấn "Xóa"
            - Hệ thống xóa thông tin trong CSDL.
        - Quản lý sinh viên:
            - Trang quản lý sinh viên trong lớp học phần (qly/hocki/lophocphan/sinhvien) gồm nút thêm, bảng các sinh
              viên đang học trong lớp đấy, trên mỗi hàng có nút Xóa.
            - ...
            - Trang thêm sinh viên (/qly/hocki/lophocphan/sinhvien/them) gồm thanh tìm kiếm theo tên, nút Tìm, bảng kết
              quả tìm kiếm theo tên (các sinh viên khoa đó), trên mỗi hàng có nút Thêm.
              -...
- Quản lý mở đợt đăng ký lớp học phần
- Sinh viên xem kết quả lớp học phần
    - Trang xem kết quả "/svien/ketqua" gồm một bảng các lớp học phần sinh viên đã học (kì học, môn học, điểm cc, kt1,
      kt2, thi, tổng kết)
- Sinh viên xem lịch học:
    - Trang xem lịch học "/svien/lichhoc" gồm một bảng thể hiện lịch học của học kì đang hoạt động có các cột là ngày
      trong tuần, các hàng là kíp trong ngày,
- Sinh viên đăng ký lớp học phần.
- Giảng viên xem lịch dạy:
    - Trang xem lịch học "/gvien/lichday" gồm một bảng thể hiện lịch học của học kì đang hoạt động có các cột là ngày
      trong tuần, các hàng là kíp trong ngày,
- Giảng viên nhập điểm:
    - Trang nhập điểm "/gvien/nhapdiem" gồm danh sách lớp học phần (trong học kì hiện tại) theo tên trên mỗi hàng có
      nút "Xem".
    - GV ấn "Xem" trên một hàng.
    - Trang nhập điểm lớp học phần "/gvien/nhapdiem/lophocphan/{id} hiện ra gồm danh sách các sinh viên trong lớp, trên
      mỗi hàng có nút "Nhập điểm".
    - GV ấn nhập điểm một sinh viên.
    - Trang nhập điểm sinh viên hiện ra gồm các ô nhập các đầu điểm cho svien. Nút lưu
    - GV nhập thông tin và ấn lưu.
    - Hệ thống lưu thông tin vào CSDL.

#### Các thông tin cần phải xử lý:

- Khoa: Tên khoa, Mô tả.
- Sinh viên: username, password, mã SV, họ tên, ngày sinh, CCCD, email, sđt.
- Giảng viên: username, password, mã GV, họ tên, ngày sinh, CCCD, email, sđt.
- Kì học: Tên kì học, ngày bắt đầu, ngày kết thúc.
- Môn học: Tên môn học, ghi chú
- Lớp học phần: Tên lớp học phần, ngày trong tuần, kíp trong ngày.
- Kết quả lớp học phần: Điểm CC, KT1, KT2, Thi.

#### Quan hệ giữa các thông tin cần phải xử lý:

- Một khoa có nhiều giảng viên.
- Một khoa có nhiều sinh viên.
- Một khoa có nhiều môn học.
- Một môn học có nhiều lớp học phần trong một kì học.
- Một lớp học phần có nhiều sinh viên, một sinh viên học nhiều lớp học phần.
- Một lớp học phần được dạy bởi một giảng viên
- Một sinh viên và một lớp học phần có một kết quả học phần

### Mô tả hệ thống bằng UML

#### Biểu đồ Use Case tổng quan:

## Phân tích:

### Trích lớp:

- Các lớp ban đầu: Khoa, GiangVien, SinhVien, MonHoc, HocKi, LopHocPhan, KetQuaHocPhan.

### Xét quan hệ số lượng giữa các lớp

- Khoa 1-n GiangVien
- Khoa 1-n SinhVien
- Khoa 1-n MonHoc
- MonHoc n-n GiangVien
  => MonHoc 1-n GiangVienMonHoc n-1 GiangVien
- MonHoc n-n HocKi
  => MonHoc 1-n LopHocPhan n-1 HocKi
- LopHocPhan n-n SinhVien
  => LopHocPhan 1-n KetQuaHocPhan n-1 SinhVien

### Xét quan hệ đối tượng giữa các lớp

- Khoa nằm trong GiangVien, SinhVien, MonHoc.
- GiangVien, MonHoc nằm trong GiangVienMonHoc.
- GiangVienMonHoc, HocKi nằm trong LopHocPhan.
- LopHocPhan nằm trong KetQuaHocPhan
- KetQuaHocPhan nằm trong SinhVien.

### Biểu đồ lớp:

![Biểu đồ lớp](./img/Class%20Diagram.jpg)

## Thiết kế:

![Thiết kế CSDL](./img/Database%20Diagram.png)
  


