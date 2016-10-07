/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vroleusercount shows the number of users per roleId
 */
CREATE OR REPLACE VIEW vroleusercount AS
SELECT roleId, COUNT(*) AS userCount
FROM user
GROUP BY roleId;