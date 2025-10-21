document.addEventListener("DOMContentLoaded", function () {
   const row = document.querySelectorAll(".employee-row");

   row.forEach(row => {
      row.addEventListener("click", function () {
          // data-id (직원 ID)를 가져옴
          const employeeId = this.dataset.id;

          // AJAX 통신
          fetch(`/api/employee/${employeeId}`)
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
});

function showEmployeeInfoPopup(employee) {
    const info = `
        사원번호: ${employee.empNo};
        이름: ${employee.name};
        이메일: ${employee.email};
        연락처: ${employee.phone};
        부서: ${employee.department};
        직책: ${employee.position};
        입사일: ${employee.hireDate};
        재직상태: ${employee.status};
    `;
    alert(info);

    //TODO: 팝업창 새로 만들기
}