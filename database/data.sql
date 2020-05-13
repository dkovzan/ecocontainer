/* Inserting initial data */
INSERT INTO container(name, uuid)
VALUES ('prototype', 'c766ee9a-1b2f-4de6-9e2c-375b621ed3e3');

INSERT INTO container_data
(container_id, air_temp, air_humidity, air_co2, air_ventilation,
 water_ph, water_ec, light_grow, light_seed, light_work,
 internal_time, global_time)
VALUES (1, 21, 71, 1200, '1', 7, 2.1, '1', '0', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 22, 72, 2200, '0', 8, 2.2, '1', '1', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 23, 73, 3200, '1', 9, 2.3, '1', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 24, 74, 4200, '0', 10, 2.4, '0', '0', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 25, 75, 5200, '1', 11, 2.5, '0', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

/*
  Adds 2 test users with login/password
    - admin/admin with role ADMIN
    - user/user with role USER
 */
INSERT INTO users(username, password, roles)
VALUES ('admin','$2a$10$vuwjSUtugGtCnWv.vWmdSOTcREpEokp3Yi0aI2ONJAkyZRhc.cljm','ADMIN'),
    ('user', '$2a$10$W3xlQPFm5RRStaPQHETQj.IeMIF16vNbanJOrpd8a4o.QenAc1P1u', 'USER');