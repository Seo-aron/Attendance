package com.kltoy.Attendance.employee;

import com.kltoy.Attendance.employee.dto.EmployeeJoinRequestDto;
import com.kltoy.Attendance.employee.dto.EmployeeUpdateRequestDto;
import com.kltoy.Attendance.login.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void join(EmployeeJoinRequestDto dto) {
        //새로운 사원 생성
        String newEmpNo = createEmpNo();

        Employee employee = Employee.builder()
                .empNo(newEmpNo)
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())    //TODO 암호화 필요
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .hireDate(LocalDate.parse(dto.getHireDate()))
                .workDays(0)
                .status(Employee.EmployeeStatus.ACTIVE)
                .build();

        employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(Long id, EmployeeUpdateRequestDto dto) {
        // DB에서 기존 직원 정보 조회
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원을 찾을 수 없습니다. id=" + id));

        // DTO로부터 받은 정보로 직원 객체의 필드를 업데이트
        employee.updateProfile(dto.getDepartment(), dto.getPosition());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setStatus(dto.getStatus());

        // 퇴사할 시 퇴사일 수정
        if (dto.getStatus() == Employee.EmployeeStatus.RESIGNED && employee.getResignDate() == null) {
            employee.setResignDate(LocalDate.now());
        }

        return employee;
    }

    public String createEmpNo() {
        //2025 -
        String yearPrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")) + "-";

        //가입한 신입사원 찾기
        Optional<Employee> lastEmployee = employeeRepository.findTopByEmpNoStartingWithOrderByEmpNoDesc(yearPrefix);

        //가입한 첫 사원이라면
        if (lastEmployee.isEmpty()) {
            return yearPrefix + "001";
        }

        String newEmpNo = lastEmployee.get().getEmpNo();
        int lastSeq = Integer.parseInt(newEmpNo.substring(5));  //2025-001 -> 001부분
        int nextSeq = lastSeq + 1;

        return yearPrefix + String.format("%03d", nextSeq);
    }

    public Employee login(LoginRequestDto dto) {
        Employee employee = employeeRepository.findByEmpNo(dto.getEmpNo())
                .orElse(null);  //.orElse(null) => 직원이 없다면 null을 반환

        if (employee == null) {
            return null;
        }

        if (!employee.getPassword().equals(dto.getPassword())) {
            return null;
        }

        return employee;
    }
}
