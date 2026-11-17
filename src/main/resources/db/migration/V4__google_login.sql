-- 1. Google account link: OIDC subject identifier per account.
ALTER TABLE `account`
    ADD COLUMN google_sub VARCHAR(255) NULL,
    ADD CONSTRAINT uk_account_google_sub UNIQUE (google_sub);

-- 2. Seed admin: link 'hexagon' to its Google email.
UPDATE emp AS e
JOIN `account` AS a ON a.emp_id = e.emp_id
SET e.email = 'dotu.standard@gmail.com'
WHERE a.login_id = 'hexagon';
