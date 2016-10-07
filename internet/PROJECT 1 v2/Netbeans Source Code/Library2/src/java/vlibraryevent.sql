/**
 * Author:  tioa
 * Created: Dec 31, 2015
 * Description: vlibraryevent filters libray events that have not closed yet
 */
CREATE OR REPLACE VIEW vlibraryevent AS
SELECT *
FROM libraryevent
WHERE CURDATE() < enddate;