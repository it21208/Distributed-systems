/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vsubmission_manager filters vsubmission records for the manager role
 */
CREATE OR REPLACE VIEW vsubmission_manager AS
(SELECT *
FROM vsubmission
WHERE ((UCASE(librarianstatus) = 'ACCEPTED') AND 
      (pdf_pages <= 200) AND (UCASE(managerstatus) = 'NOT CHECKED') AND (studentinformdate IS NULL)))
UNION ALL
(SELECT *
FROM vsubmission_director
WHERE (director_status = 1));