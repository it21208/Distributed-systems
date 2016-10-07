/**
 * Author:  tioa
 * Created: Dec 30, 2015
 * Description: vdissertation_director filters dissertations which have active submissions
 *              unchecked by the director
 */
CREATE OR REPLACE VIEW vdissertation_director AS
SELECT vd.*
FROM vdissertation vd, vsubmission_director vsd
WHERE (vd.id = vsd.dissertationId);
