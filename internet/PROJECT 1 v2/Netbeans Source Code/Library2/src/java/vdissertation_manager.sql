/**
 * Author:  tioa
 * Created: Dec 30, 2015
 * Description: vdissertation_manager filters dissertations which have active submissions
 *              viewable by the manager
 */
CREATE OR REPLACE VIEW vdissertation_manager AS
SELECT vd.*
FROM vdissertation vd, vsubmission_manager vsm
WHERE (vd.id = vsm.dissertationId);
