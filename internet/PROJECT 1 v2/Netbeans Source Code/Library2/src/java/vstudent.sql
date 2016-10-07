/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vstudent filters the vuser view and shows only the students.
 */
CREATE OR REPLACE VIEW vstudent AS
SELECT *
FROM vuser
WHERE rolename = 'Student';

