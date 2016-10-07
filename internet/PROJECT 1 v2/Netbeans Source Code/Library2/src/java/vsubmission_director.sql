/**
 * Author:  tioa
 * Created: Dec 20, 2015
 * Description: vsubmission_director filters vsubmission records for the director role
 */
CREATE OR REPLACE VIEW vsubmission_director AS
SELECT *
FROM vsubmission
WHERE ((UCASE(librarianstatus) = 'ACCEPTED') AND 
      (pdf_pages > 200) AND UCASE(managerstatus = 'NOT CHECKED'));