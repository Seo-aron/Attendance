package com.kltoy.Attendance.worklog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WorkLogSaveRequestDto {

    private Long id;
    private LocalDate workDate;
    private String content;
}
