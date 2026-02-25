document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("employee-modal");
    const closeModalBtn = document.getElementById("modal-close-btn");
    const editForm = document.getElementById("edit-form");
    const editBtn = document.getElementById("edit-btn");
    const saveBtn = document.getElementById("save-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    const viewModes = modal.querySelectorAll(".view-mode");
    const editModes = modal.querySelectorAll(".edit-mode");

    switchToViewMode();

    if (closeModalBtn) {
        closeModalBtn.addEventListener("click", closeModal);
    }
    if (modal) {
        modal.addEventListener("click", (event) => {
            if (event.target === modal) {
                closeModal();
            }
        });
    }

    if (editBtn) {
        editBtn.addEventListener("click", switchToEditMode);
    }
    if (saveBtn) {
        editForm.addEventListener("submit", handleSave);
    }
    if (cancelBtn) {
        cancelBtn.addEventListener("click", switchToViewMode);
    }

    const row = document.querySelectorAll(".employee-row");
    row.forEach(row => {
        row.addEventListener("click", function () {
            // data-id (직원 ID)를 가져옴
            const employeeId = this.dataset.id;

            // AJAX 통신
            fetch(`/employee/${employeeId}`)
                .then(response => {
                    if (!response.ok) {
                    throw new Error('데이터를 불러오는데 실패했습니다.');
                    }
                return response.json();
                })
                .then(data => {
                    showEmployeeInfoPopup(data);
                })
                .catch(error => {
                    alert(error.message);
                });
        });
    });

    // 함수
    function closeModal() {
        modal.style.display = "none";
        switchToViewMode();
    }

    function switchToViewMode() {
        viewModes.forEach(el => el.style.display = "");
        editModes.forEach(el => el.style.display = "none");
        editBtn.style.display = "";
        saveBtn.style.display = "none";
        cancelBtn.style.display = "none";
    }

    function switchToEditMode() {
        viewModes.forEach(el => el.style.display = "none");
        editModes.forEach(el => el.style.display = "");
        editBtn.style.display = "none";
        saveBtn.style.display = "";
        cancelBtn.style.display = "";

        document.getElementById("edit-email").value = document.getElementById("modal-email").textContent;
        document.getElementById("edit-phone").value = document.getElementById("modal-phone").textContent;
        document.getElementById("edit-department").value = document.getElementById("modal-department").textContent;
        document.getElementById("edit-position").value = document.getElementById("modal-position").textContent;

        const currentStatusText = document.getElementById("modal-status").textContent;
        const statusSelect = document.getElementById("edit-status");
        if (currentStatusText === "재직중") {
            statusSelect.value = "ACTIVE";
        } else if (currentStatusText === "퇴사") {
            statusSelect.value = "RESIGNED";
        }
    }

    function handleSave(event) {
        event.preventDefault();

        const employeeId = document.getElementById("modal-id").value;

        const updateData = {
            email: document.getElementById("edit-email").value,
            phone: document.getElementById("edit-phone").value,
            department: document.getElementById("edit-department").value,
            position: document.getElementById("edit-position").value,
            status: document.getElementById("edit-status").value
        }

        fetch(`/employee/${employeeId}`, {
           method: 'POST',
           headers: {
               'Content-Type': 'application/json',
           },
            body: JSON.stringify(updateData),
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => Promise.reject('저장 실패: ' + text));
            }
            // 성공 시 업데이트 된 직원 정보 받기
            return response.json();
        })
        .then(updatedEmployee => {
            alert("수정되었습니다.");
            updatePopupView(updatedEmployee);
            switchToViewMode();
            window.location.reload();
        })
        .catch(error => {
            alert(error);
        });
    }

    // 서버 응답으로 팝업 보기 모드 업데이트하는 함수
    function updatePopupView(employee) {
        document.getElementById("modal-id").value = employee.id;
        document.getElementById("modal-empNo").textContent = employee.empNo;
        document.getElementById("modal-name").textContent = employee.name;
        document.getElementById("modal-email").textContent = employee.email;
        document.getElementById("modal-phone").textContent = employee.phone;
        document.getElementById("modal-department").textContent = employee.department;
        document.getElementById("modal-position").textContent = employee.position;
        document.getElementById("modal-hireDate").textContent = employee.hireDate;
        document.getElementById("modal-status").textContent = employee.status === "ACTIVE" ? "재직중" : "퇴사";
    }

    function showEmployeeInfoPopup(employee) {
        updatePopupView(employee);
        switchToViewMode();
        modal.style.display = "flex";
    }
});

function showEmployeeInfoPopup(employee) {
    let statusInKorean;
    switch (employee.status) {
        case "ACTIVE":
            statusInKorean = "재직중";
            break;
        case "RESIGNED":
            statusInKorean = "퇴사";
            break;
        default:
            statusInKorean = "알 수 없음";
    }

    document.getElementById("modal-empNo").textContent = employee.empNo;
    document.getElementById("modal-name").textContent = employee.name;
    document.getElementById("modal-email").textContent = employee.email;
    document.getElementById("modal-phone").textContent = employee.phone;
    document.getElementById("modal-department").textContent = employee.department;
    document.getElementById("modal-position").textContent = employee.position;
    document.getElementById("modal-hireDate").textContent = employee.hireDate;
    document.getElementById("modal-status").textContent = statusInKorean;

    const modal = document.getElementById("employee-modal");
    modal.style.display = "flex";
}