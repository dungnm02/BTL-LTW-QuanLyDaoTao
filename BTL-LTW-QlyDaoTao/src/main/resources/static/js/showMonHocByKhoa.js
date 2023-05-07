function showMonHocByKhoa() {
    var allMonHoc = /*[[${dsMonHoc}]]*/ [];
    var khoa = document.getElementById("khoa").value;
    var monhocSelect = document.getElementById("monhoc");
    monhocSelect.innerHTML = "";
    for (var i = 0; i < allMonHoc.length; i++) {
        if (allMonHoc[i].khoa.id == khoa.id) {
            var option = document.createElement("option");
            option.text = allMonHoc[i].tenMonHoc;
            option.value = allMonHoc[i].id;
            monhocSelect.add(option);
        }
    }
}
