package com.kltoy.Attendance.worklog;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    // '내 업무일지' 목록을 보기 위한 쿼리 메서드
    List<WorkLog> findByEmployeeId(Long employeeId);

    // '사장'이 결재할 목록을 보기 위한 쿼리 메서드
    List<WorkLog> findByApprovalStatus(WorkLog.ApprovalStatus status);
}