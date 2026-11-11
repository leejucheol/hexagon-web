
package com.hexagon.hrproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hexagon.hrproject.dto.response.DepartmentAttendanceRow;
import com.hexagon.hrproject.mapper.DepartmentAttendanceMapper;

@Service
public class DepartmentAttendanceService {

    private final DepartmentAttendanceMapper departmentAttendanceMapper;

    public DepartmentAttendanceService(
            DepartmentAttendanceMapper departmentAttendanceMapper) {
        this.departmentAttendanceMapper = departmentAttendanceMapper;
    }

    public Integer getMyDepartmentId(String loginId) {
        return departmentAttendanceMapper.selectDepartmentIdByLoginId(loginId);
    }

    public List<DepartmentAttendanceRow> search(
            Integer departmentId,
            LocalDate workDate) {

        return departmentAttendanceMapper.search(
            departmentId,
            workDate
        );
    }
}
