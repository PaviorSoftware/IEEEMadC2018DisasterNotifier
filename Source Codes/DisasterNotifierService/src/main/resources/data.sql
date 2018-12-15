INSERT INTO roles (name)
VALUES ("SYS_ADMIN");
INSERT INTO roles (name)
VALUES ("SYS_USER");

INSERT INTO `users` (`id`, `e_mail`, `firebase_token`, `latitude`, `longitude`, `password`, `username`) VALUES
(1, 'ieee@teicm.gr', 'ew_AVqJzGjM:APA91bHEwHELDYdFxOdljUAPXORrurU2DTCJJgydewMrRcKYtJGO66DUASwep12uYPmkx4JisehrfFHytIZ29d64HIgt5sDyMNaWOkks3cnjicdDrNkJuKKsVGai3-ti_Nrd_OnL6LkA', 40.7698, 22.709, 'EF273EFBE70F384C519FF62A2EF9BFE180AF52EA626675145ED2712D04DDE3CB', 'ieee');

INSERT INTO users_roles(user_id, roles_id)
VALUES (1, 1);