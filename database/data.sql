INSERT INTO Containers(name) VALUES ('prototype');

INSERT INTO ContainerData(containerId, airTemp, airHumidity, airCo2, airVentilation, waterPh, waterEc, lightGrow, lightSeed, lightWork, internalTime, globalTime)
VALUES (1, 21, 71, 1200, '1', 7, 2.1, '1', '0', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 22, 72, 2200, '0', 8, 2.2, '1', '1', '0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 23, 73, 3200, '1', 9, 2.3, '1', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 24, 74, 4200, '0', 10, 2.4, '0', '0', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (1, 25, 75, 5200, '1', 11, 2.5, '0', '1', '1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users(username, password, roles)
VALUES ('admin','$2a$10$vuwjSUtugGtCnWv.vWmdSOTcREpEokp3Yi0aI2ONJAkyZRhc.cljm','ADMIN'),
    ('user', '$2a$10$W3xlQPFm5RRStaPQHETQj.IeMIF16vNbanJOrpd8a4o.QenAc1P1u', 'USER');