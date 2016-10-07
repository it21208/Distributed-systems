/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vsubmission brings additional information from all related tables
 */
CREATE OR REPLACE VIEW vsubmission AS
SELECT s.*, 
       vd.dissertationId AS dissertation_ID, vd.title, vd.supervisor, vd.studentname, vd.userid, vd.studentcansubmitonline, vd.studentemail,
       ls.status AS librarianstatus,
       ms.status AS managerstatus
FROM submission s, vdissertation vd, librarian_status ls, manager_status ms
WHERE (s.dissertationId = vd.id) AND
      (s.librarianstatusId = ls.id) AND
      (s.managerstatusId = ms.id);