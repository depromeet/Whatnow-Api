INSERT INTO tbl_promise_progress(promise_progress_id, promise_progress_group, code , kr, img )
VALUES
    (1,'PREPARING','SHOWER' ,'씻는중',''),
    (2,'PREPARING','LEAVE_SOON' ,'곧 나감',''),
    (3,'PREPARING','BED' ,'침대위',''),
    (4,'MOVING','WALKING' ,'걸어가는중',''),
    (5,'MOVING','RUNNING' ,'뛰어가는중',''),
    (6,'MOVING','BOARDING' ,'탑승중',''),
    (7,'LATE','KNEELING' ,'무릎 꿇기',''),
    (8,'LATE','해' ,'눈물',''),
    (9,'LATE','URGENCY' ,'다급함',''),
    (10,'EXPECTED_TIME','AFTER_SOON' ,'곧 도착',''),
    (11,'EXPECTED_TIME','AFTER_5' ,'5분뒤 도착',''),
    (12,'EXPECTED_TIME','AFTER_10' ,'10분뒤 도착',''),
    (13,'DEFAULT','DEFAULT' ,'기본 속성','')
    ON DUPLICATE KEY UPDATE promise_progress_group = VALUES (promise_progress_group), code = VALUES (code), kr = VALUES (kr), img = VALUES (img);
