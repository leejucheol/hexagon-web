-- 9. Account roles (1): ROLE_HR_ADMIN.
INSERT INTO account_role (account_id, hr_role_id)
SELECT a.account_id, r.hr_role_id
FROM `account` AS a
CROSS JOIN hr_role AS r
WHERE a.login_id = 'hexagon'
  AND r.role_code = 'ROLE_HR_ADMIN'
  AND NOT EXISTS (
      SELECT 1
      FROM account_role AS ar
      WHERE ar.account_id = a.account_id
        AND ar.hr_role_id = r.hr_role_id
  );
