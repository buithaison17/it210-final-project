#
Tạo dữ liệu mẫu

use it210_final_project;
-- 3 data admin, staff, user
-- password: sonbui123
insert into users(balance, email, full_name, password, phone, role)
VALUES (10000000, 'sonbui@admin.com', 'Son Bui',
        '$2a$08$dzoKiWRP3EX558E1gipYhOjSnuacGI8yzEEfyCNN6TdaMAaRBuRau',
        '0124567890', 'admin'),
       (5000000, 'staff1@gmail.com', 'Staff 1',
        '$2a$08$dzoKiWRP3EX558E1gipYhOjSnuacGI8yzEEfyCNN6TdaMAaRBuRau',
        '0123567891', 'staff'),
       (100000, 'user1@gmail.com', 'User 1',
        '$2a$08$dzoKiWRP3EX558E1gipYhOjSnuacGI8yzEEfyCNN6TdaMAaRBuRau',
        '0123456892', 'passenger');

insert into locations(name)
values ('Thái Bình'),
       ('Nam Định'),
       ('Hà Nội'),
       ('TP. Hồ Chí Minh'),
       ('Đà Lạt');

insert into routes(distance, destination_id, origin_id, route_id)
values (100, 1, 2, 1),
       (200, 3, 4, 2),
       (300, 3, 1, 3),
       (400, 5, 1, 4),
       (500, 4, 5, 5)
