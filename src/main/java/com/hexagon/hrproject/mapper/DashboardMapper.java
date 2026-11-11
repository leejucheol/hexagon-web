package com.hexagon.hrproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashboardMapper {
    
    @Select("SELECT COUNT(*) FROM emp")
    int countAll();

    @Select("SELECT COUNT(*) FROM emp WHERE status = #{status}")
    int countByStatus(String status);
}