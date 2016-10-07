/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vdissertation complements dissertation with students fields
 */
CREATE OR REPLACE VIEW vdissertation AS
SELECT d.*, vs.name AS studentname, vs.userId AS userid, vs.onlinesubmit AS studentcansubmitonline, vs.email AS studentemail
FROM dissertation d, vstudent vs
WHERE (d.studentId = vs.id);
