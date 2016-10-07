/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vroleminmaxusercount shows the (min, max) range, the user count per role Id
 */
CREATE OR REPLACE VIEW vroleminmaxusercount AS
SELECT id, name, MaxUsers AS maxusers, MinUsers AS minusers, IF(ISNULL(userCount), 0, userCount) AS userCount
FROM role LEFT JOIN vroleusercount ON (role.id = vroleusercount.roleId);