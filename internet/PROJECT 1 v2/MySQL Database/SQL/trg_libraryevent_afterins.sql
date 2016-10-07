/**
 * Author:  tioa
 * Created: Dec 01, 2016
 * Description: AFTER INSERT trigger on libraryevent table ensures that
 *      appropriate "email" records are "sent" to all members of the
 *      eventemailrecipient table.
 */
DROP trigger trg_libraryevent_afterins;

DELIMITER //
CREATE TRIGGER trg_libraryevent_afterins
AFTER INSERT ON libraryevent FOR EACH ROW
BEGIN	
    INSERT INTO eventemail(eventemailrecipient_id, libraryevent_id, senttime)
    SELECT id, NEW.id, NOW() FROM eventemailrecipient;
END//
DELIMITER ;