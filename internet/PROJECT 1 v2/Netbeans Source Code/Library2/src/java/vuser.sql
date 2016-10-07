/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vuser presents all fields from table user and adds the role name
 *      at the end
 */
CREATE OR REPLACE VIEW vuser AS
SELECT u.*, r.name AS rolename
FROM user u, role r
WHERE u.roleId = r.id;

