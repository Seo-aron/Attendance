package com.kltoy.Attendance.worklog;

import com.kltoy.Attendance.employee.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "work_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 결재 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    // 결재자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private Employee approver;

    // 결재 상태 Enum
    public enum ApprovalStatus {
        PENDING,  // 대기중
        APPROVED, // 승인
        REJECTED  // 반려
    }

    @Builder
    public WorkLog(Employee employee, LocalDate workDate, String content) {
        this.employee = employee;
        this.workDate = workDate;
        this.content = content;
        this.approvalStatus = ApprovalStatus.PENDING; // 생성 시 항상 '대기중'
    }

    // 결재 승인
    public void approve(Employee approver) {
        this.approvalStatus = ApprovalStatus.APPROVED;
        this.approver = approver;
    }

    // 결재 반려
    public void reject(Employee approver) {
        this.approvalStatus = ApprovalStatus.REJECTED;
        this.approver = approver;
    }
}