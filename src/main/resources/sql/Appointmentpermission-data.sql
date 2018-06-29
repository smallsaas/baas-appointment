INSERT INTO `perm_group` (`id`, `name`, `identifier`) VALUES
('1012573379300782085', 'Appointment模块', 'Appointment.management');

INSERT INTO `perm` (`id`, `groupid`, `name`, `identifier`) VALUES
('1012573379300782082', '1012573379300782085', '查看Appointment', 'Appointment.view'),
('1012573379300782083', '1012573379300782085', '编辑Appointment', 'Appointment.edit');
