

#修复无法索引我的订单问题 24/04/2019




#增加user_id 字段，去处理我的预约，虽然直接update member_id 为 user_id 也能实现，但是，还是加吧。 26/04/2019


#25/05/2019 删除 预约 ，因为 保存的是 vipId ，所以需要将传递的userId 转化为 vipid




























UPDATE t_appointment SET user_id = 22      WHERE member_id = 24;
UPDATE t_appointment SET user_id = 71      WHERE member_id = 38;
UPDATE t_appointment SET user_id = 72      WHERE member_id = 39;
UPDATE t_appointment SET user_id = 159      WHERE member_id = 42;
UPDATE t_appointment SET user_id = 23      WHERE member_id = 43;
UPDATE t_appointment SET user_id = 55      WHERE member_id = 66;
UPDATE t_appointment SET user_id = 248      WHERE member_id = 93;
UPDATE t_appointment SET user_id = 44      WHERE member_id = 94;
UPDATE t_appointment SET user_id = 254      WHERE member_id = 96;
UPDATE t_appointment SET user_id = 255      WHERE member_id = 97;
UPDATE t_appointment SET user_id = 260      WHERE member_id = 99;
UPDATE t_appointment SET user_id = 66      WHERE member_id = 115;


,14
,223
,399
,401
,402
,403
,404
,405
,407
,408
,409
,410
,411
,413
,415
,416
,417
,425
,426
,427
,434
,436
,437
,838
,436









