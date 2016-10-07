/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vcaninsertusertorole returns the Id and name for roles which
 * can still accept more users
 */
CREATE OR REPLACE VIEW vcaninsertusertorole AS
SELECT id, name
FROM vroleminmaxusercount
WHERE userCount < maxusers;