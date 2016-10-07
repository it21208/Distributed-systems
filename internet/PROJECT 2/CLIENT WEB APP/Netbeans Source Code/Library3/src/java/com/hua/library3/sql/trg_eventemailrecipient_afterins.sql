/**
 * Author:  tioa
 * Created: Dec 01, 2016
 * Description: AFTER INSERT trigger on eventemailrecipient table ensures that
 *      appropriate "email" records are "sent" to this recipient for all members
 *      of the vlibraryevent view (that is, all active events).
 */
DROP trigger trg_eventemailrecipient_afterins;

DELIMITER //
CREATE TRIGGER trg_eventemailrecipient_afterins
AFTER INSERT ON eventemailrecipient FOR EACH ROW
BEGIN	
    INSERT INTO eventemail(eventemailrecipient_id, libraryevent_id, senttime)
    SELECT NEW.id, id, NOW() FROM vlibraryevent;
END//
DELIMITER ;