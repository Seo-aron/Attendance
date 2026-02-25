package com.kltoy.Attendance.employee;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 시스템 내부 PK

    @Column(name = "emp_no", unique = true, nullable = false, length = 20)
    private String empNo;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @Setter
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Setter
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Setter
    @Column(name = "resign_date")
    private LocalDate resignDate;

    @Column(name = "work_days", nullable = false)
    private Integer workDays;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 재직 상태
    public enum EmployeeStatus {
        ACTIVE, // 재직 중
        RESIGNED // 퇴사
    }

    @Builder
    public Employee(String empNo, String name, String password, String email, String phone,
                    String department, String position, LocalDate hireDate,
                    LocalDate resignDate, Integer workDays, EmployeeStatus status) {
        this.empNo = empNo;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
        this.resignDate = resignDate;
        this.workDays = workDays;
        this.status = status;
    }

    // 정보 수정
    public void updateProfile(String department, String position) {
        this.department = department;
        this.position = position;
    }
}