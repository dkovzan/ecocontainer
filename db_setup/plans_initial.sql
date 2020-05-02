DROP TABLE IF EXISTS PlanTemplates;
DROP TABLE IF EXISTS PlanTemplateStages;
DROP TABLE IF EXISTS ContainerPlans;
DROP TABLE IF EXISTS ContainerPlanStages;

CREATE TABLE PlanTemplates
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(25),
    description VARCHAR(125),
    plantName VARCHAR(25),

    createdOn TIMESTAMP,
    updatedOn TIMESTAMP DEFAULT NOW()
);

CREATE TABLE PlanTemplateStages
(
    id   SERIAL PRIMARY KEY,
    planTemplateId INTEGER NOT NULL,

    stage VARCHAR(25), -- title of the stage as 'seeding' or 'flush'
    description VARCHAR(125),

    duration INTEGER, -- number of days for this stage
    startTime INTEGER, -- time of the day (hours) when start using this scheme
    endTime INTEGER, -- time of the day (hours) when finish using this scheme

    solutionA FLOAT,
    solutionB FLOAT,
    solutionC FLOAT,
    ppmMin INTEGER, ppmMax INTEGER, -- Range for PPM
    waterPHMin FLOAT, waterPHMax FLOAT, -- Range for Water pH Lebel
    waterTempMin INTEGER, waterTempMax INTEGER, -- Range for Water Temperature
    waterECMin FLOAT, waterECMax FLOAT, -- Range for Water EC
    airTempMin INTEGER, airTempMax INTEGER, -- Range for Air Temperature
    airHumidityMin INTEGER, airHumidityMax INTEGER, -- Range for Air Humidity
    co2Min INTEGER, co2Max INTEGER, -- Range for CO2 ppm
    dissOxygenMin INTEGER, dissOxygenMax INTEGER, -- Range for Dissolved Oxygen ppm



    createdOn TIMESTAMP,
    updatedOn TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (planTemplateId) REFERENCES PlanTemplates (id) ON DELETE NO ACTION
);



CREATE TABLE ContainerPlans
(
    id   SERIAL PRIMARY KEY,
    planTemplateId INTEGER NOT NULL,
    containerId INTEGER NOT NULL,
    name VARCHAR(25),
    description VARCHAR(125),
    plantName VARCHAR(25),
    stage VARCHAR(25),
    stageStartDate TIMESTAMP, -- maybe it's better to replace with stageStartDate

    createdOn TIMESTAMP,
    updatedOn TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (planTemplateId) REFERENCES PlanTemplates (id) ON DELETE NO ACTION,
    FOREIGN KEY (containerId) REFERENCES Containers (id) ON DELETE NO ACTION
);

CREATE TABLE ContainerPlanStages
(
    id   SERIAL PRIMARY KEY,
    containerPlanId INTEGER NOT NULL,

    stage VARCHAR(25), -- title of the stage as 'seeding' or 'flush'
    description VARCHAR(125),

    duration INTEGER, -- number of days for this stage
    startTime INTEGER, -- time of the day (hours) when start using this scheme
    endTime INTEGER, -- time of the day (hours) when finish using this scheme

    solutionA FLOAT,
    solutionB FLOAT,
    solutionC FLOAT,
    ppmMin INTEGER, ppmMax INTEGER, -- Range for PPM
    waterPHMin FLOAT, waterPHMax FLOAT, -- Range for Water pH Lebel
    waterTempMin INTEGER, waterTempMax INTEGER, -- Range for Water Temperature
    waterECMin FLOAT, waterECMax FLOAT, -- Range for Water EC
    airTempMin INTEGER, airTempMax INTEGER, -- Range for Air Temperature
    airHumidityMin INTEGER, airHumidityMax INTEGER, -- Range for Air Humidity
    co2Min INTEGER, co2Max INTEGER, -- Range for CO2 ppm
    dissOxygenMin INTEGER, dissOxygenMax INTEGER, -- Range for Dissolved Oxygen ppm

    createdOn TIMESTAMP,
    updatedOn TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (containerPlanId) REFERENCES ContainerPlans (id) ON DELETE NO ACTION
);