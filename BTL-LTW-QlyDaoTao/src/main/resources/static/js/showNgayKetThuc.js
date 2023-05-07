function showNgayKetThuc() {
    var ngayBatDauSelect = document.getElementById("ngayBatDau");
    var ngayKetThucSelect = document.getElementById("ngayKetThuc");
    var ngayBatDau = new Date(ngayBatDauSelect.value);
    var ngayKetThuc = new Date(ngayBatDau.getTime() + (29 * 7 * 24 * 60 * 60 * 1000));
    ngayKetThucSelect.value = ngayKetThuc.toISOString().slice(0, 10);
}