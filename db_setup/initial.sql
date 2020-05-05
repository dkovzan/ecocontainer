/* Clearing DB tables */

DROP TABLE IF EXISTS user_to_container;
DROP TABLE IF EXISTS user_to_role;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user_info;

DROP TABLE IF EXISTS container_plan_stage;
DROP TABLE IF EXISTS container_plan;
DROP TABLE IF EXISTS plan_template_stage;
DROP TABLE IF EXISTS plan_template;

DROP TABLE IF EXISTS container_data;
DROP TABLE IF EXISTS container;

/* Creating tables */
CREATE TABLE container
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    uuid VARCHAR(36) NOT NULL
);

CREATE TABLE container_data
(
    id             SERIAL PRIMARY KEY,
    container_id    INTEGER NOT NULL,
    air_temp        INTEGER,
    air_humidity    INTEGER,
    air_co2         INTEGER,
    air_ventilation BOOLEAN,
    water_ph        FLOAT,
    water_ec        INTEGER,
    light_grow      BOOLEAN,
    light_seed      BOOLEAN,
    light_work      BOOLEAN,
    internal_time   TIMESTAMP,
    global_time     TIMESTAMP,
    FOREIGN KEY (container_id) REFERENCES container (id) ON DELETE NO ACTION
);

CREATE TABLE user_role
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(500),
    category VARCHAR(200)
);

CREATE TABLE user_info
(
    id SERIAL PRIMARY KEY,
    login VARCHAR(200) NOT NULL,
    first_name VARCHAR(200),
    last_name VARCHAR(200),
    password VARCHAR(500) NOT NULL,
    email VARCHAR(200),
    phone VARCHAR(200),
    address VARCHAR(500),
    status VARCHAR(200),
    password_changed_on TIMESTAMP,
    is_enabled BOOLEAN NOT NULL,
    created_on TIMESTAMP DEFAULT NOW(),
    updated_on TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_to_role
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    status VARCHAR(200),

    FOREIGN KEY (user_id) REFERENCES user_info (id) ON DELETE NO ACTION,
    FOREIGN KEY (role_id) REFERENCES user_role (id) ON DELETE NO ACTION
);

CREATE TABLE user_to_container
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    container_id INTEGER NOT NULL,
    status VARCHAR(200),

    FOREIGN KEY (user_id) REFERENCES user_info (id) ON DELETE NO ACTION,
    FOREIGN KEY (container_id) REFERENCES container (id) ON DELETE NO ACTION
);

CREATE TABLE plan_template
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    description VARCHAR(125),
    plant_name VARCHAR(25) NOT NULL,

    created_on TIMESTAMP DEFAULT NOW(),
    updated_on TIMESTAMP DEFAULT NOW()
);

CREATE TABLE plan_template_stage
(
    id   SERIAL PRIMARY KEY,
    plan_template_id INTEGER NOT NULL,

    stage VARCHAR(25) NOT NULL, -- title of the stage as 'seeding' or 'flush'
    description VARCHAR(125),

    duration INTEGER, -- number of days for this stage
    start_time INTEGER, -- time of the day (hours) when start using this scheme
    end_time INTEGER, -- time of the day (hours) when finish using this scheme

    solution_a FLOAT,
    solution_b FLOAT,
    solution_c FLOAT,
    ppm_min INTEGER, ppm_max INTEGER, -- Range for PPM
    water_ph_min FLOAT, water_ph_max FLOAT, -- Range for Water pH Lebel
    water_temp_min INTEGER, water_temp_max INTEGER, -- Range for Water Temperature
    water_ec_min FLOAT, water_ec_max FLOAT, -- Range for Water EC
    air_temp_min INTEGER, air_temp_max INTEGER, -- Range for Air Temperature
    air_humidity_min INTEGER, air_humidity_max INTEGER, -- Range for Air Humidity
    co2_min INTEGER, co2_max INTEGER, -- Range for CO2 ppm
    diss_oxygen_min INTEGER, diss_oxygen_max INTEGER, -- Range for Dissolved Oxygen ppm

    created_on TIMESTAMP DEFAULT NOW(),
    updated_on TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (plan_template_id) REFERENCES plan_template (id) ON DELETE NO ACTION
);

CREATE TABLE container_plan
(
    id   SERIAL PRIMARY KEY,
    plan_template_id INTEGER NOT NULL,
    container_id INTEGER NOT NULL,
    name VARCHAR(25),
    description VARCHAR(125),
    plant_name VARCHAR(25),
    stage VARCHAR(25),
    stage_start_date TIMESTAMP, -- maybe it's better to replace with stageStartDate

    created_on TIMESTAMP DEFAULT NOW(),
    updated_on TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (plan_template_id) REFERENCES plan_template (id) ON DELETE NO ACTION,
    FOREIGN KEY (container_id) REFERENCES container (id) ON DELETE NO ACTION
);

CREATE TABLE container_plan_stage
(
    id   SERIAL PRIMARY KEY,
    container_plan_id INTEGER NOT NULL,

    stage VARCHAR(25), -- title of the stage as 'seeding' or 'flush'
    description VARCHAR(125),

    duration INTEGER, -- number of days for this stage
    start_time INTEGER, -- time of the day (hours) when start using this scheme
    end_time INTEGER, -- time of the day (hours) when finish using this scheme

    solution_a FLOAT,
    solution_b FLOAT,
    solution_c FLOAT,
    ppm_min INTEGER, ppm_max INTEGER, -- Range for PPM
    water_ph_min FLOAT, water_ph_max FLOAT, -- Range for Water pH Lebel
    water_temp_min INTEGER, water_temp_max INTEGER, -- Range for Water Temperature
    water_ec_min FLOAT, water_ec_max FLOAT, -- Range for Water EC
    air_temp_min INTEGER, air_temp_max INTEGER, -- Range for Air Temperature
    air_humidity_min INTEGER, air_humidity_max INTEGER, -- Range for Air Humidity
    co2_min INTEGER, co2_max INTEGER, -- Range for CO2 ppm
    diss_oxygen_min INTEGER, diss_oxygen_max INTEGER, -- Range for Dissolved Oxygen ppm

    created_on TIMESTAMP DEFAULT NOW(),
    updated_on TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (container_plan_id) REFERENCES container_plan (id) ON DELETE NO ACTION
);

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

