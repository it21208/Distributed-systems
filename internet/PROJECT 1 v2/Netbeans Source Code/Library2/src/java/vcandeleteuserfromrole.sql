/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vcandeleteuserfromrole returns the Id and name for roles which
 * allow a user to be removed from the role
 */
CREATE OR REPLACE VIEW vcandeleteuserfromrole AS
SELECT id, name
FROM vroleminmaxusercount
WHERE userCount > minusers;