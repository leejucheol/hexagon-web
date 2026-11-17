-- 1. Seed admin: change the Google email linked to 'hexagon'.
UPDATE emp AS e
JOIN `account` AS a ON a.emp_id = e.emp_id
SET e.email = 'jcllee9239@gmail.com'
WHERE a.login_id = 'hexagon';
