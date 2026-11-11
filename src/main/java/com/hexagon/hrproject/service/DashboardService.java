package com.hexagon.hrproject.service;

import com.hexagon.hrproject.mapper.DashboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        int totalEmp = dashboardMapper.countAll();
        int activeEmp = dashboardMapper.countByStatus("ACTIVE");
        int leaveEmp = dashboardMapper.countByStatus("LEAVE");
        int resignEmp = dashboardMapper.countByStatus("RESIGN");

        stats.put("totalEmp", totalEmp);
        stats.put("activeEmp", activeEmp);
        stats.put("leaveEmp", leaveEmp);
        stats.put("resignEmp", resignEmp);

        return stats;
    }
}