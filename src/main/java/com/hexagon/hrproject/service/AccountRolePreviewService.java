package com.hexagon.hrproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexagon.hrproject.mapper.AccountRolePreviewMapper;

@Service
public class AccountRolePreviewService {

    private final AccountRolePreviewMapper accountRolePreviewMapper;

    @Autowired
    public AccountRolePreviewService(
            AccountRolePreviewMapper accountRolePreviewMapper) {

        this.accountRolePreviewMapper = accountRolePreviewMapper;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getRolePermissions() {
        return accountRolePreviewMapper.selectRolePermissions();
    }
}