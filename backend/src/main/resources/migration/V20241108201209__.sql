CREATE TABLE registration.users
(
    user_id           UUID primary key not null,
    first_name        varchar(64)      not null,
    last_name         varchar(64)      not null,
    email             varchar(64)      not null unique,
    password          varchar(64)      not null,
    role              varchar(12)      not null,
    status            varchar(12)      not null,
    verification_code varchar(15),
    reset_token       varchar(15)
);

CREATE TABLE registration.countries
(
    country_id          UUID primary key not null,
    name                varchar(64) not null,
    name_ru             varchar(64) not null,
    name_hy             varchar(64) not null
);

CREATE TABLE registration.foodPlaces
(
    food_place_id       UUID primary key not null,
    name                varchar(64) not null,
    region_id           UUID not null
);

CREATE TABLE registration.gasStations
(
    gas_station_id      UUID primary key not null,
    name                varchar(64) not null,
    region_id           UUID not null
);

CREATE TABLE registration.nearbyHotels
(
    nearby_hotels_id    UUID primary key not null,
    name                varchar(64) not null,
    rate                varchar(255) NOT NULL,
    website             TEXT NOT NULL,
    region_id           UUID not null,
    name_hy             varchar(64) not null,
    name_ru             varchar(64) not null,
    description         TEXT not null,
    description_hy      TEXT not null,
    description_ru      TEXT not null,
    photo_id            UUID not null
);

CREATE TABLE registration.nearbyHospitals
(
    nearby_hospitals_id UUID primary key not null,
    name                varchar(64) not null,
    region_id           UUID not null
);

CREATE TABLE registration.nearbyPharmacies
(
    nearby_pharmacy_id  UUID primary key not null,
    name                varchar(64) not null,
    region_id           UUID not null
);

CREATE TABLE registration.photos
(
    photo_id            UUID primary key not null,
    name                varchar(64) not null,
    size                varchar(64) not null,
    path                TEXT not null,
    name_ru             varchar(64) not null,
    name_hy             varchar(64) not null,
    description         TEXT not null,
    description_ru      TEXT not null,
    description_hy      TEXT not null
);

CREATE TABLE registration.photo360
(
    photo_id            UUID primary key not null,
    name                varchar(64) not null,
    size                varchar(64) not null,
    path                TEXT not null,
    description         TEXT not null,
    name_ru             varchar(64) not null,
    name_hy             varchar(64) not null,
    description_ru      TEXT not null,
    description_hy      TEXT not null
);

CREATE TABLE registration.roadQuality
(
    road_quality_id     UUID primary key not null,
    name                varchar(64) not null,
    name_ru             varchar(64) not null,
    name_hy             varchar(64) not null,
    description         TEXT not null,
    description_ru      TEXT not null,
    description_hy      TEXT not null
);

CREATE TABLE registration.transportType
(
    transport_type_id   UUID primary key not null,
    name                varchar(64) not null,
    name_ru             varchar(64) not null,
    name_hy             varchar(64) not null,
    description         TEXT not null,
    description_ru      TEXT not null,
    description_hy      TEXT not null
);

CREATE TABLE registration.climateZones
(
    climate_zone_id UUID primary key not null,
    name            varchar(64)      not null,
    name_ru         varchar(64)      not null,
    name_hy         varchar(64)      not null
);

CREATE TABLE registration.placeType
(
    place_type_id       UUID primary key not null,
    name                varchar(64) not null,
    name_ru             varchar(64) not null,
    name_hy             varchar(64) not null,
    description         TEXT not null,
    description_ru      TEXT not null,
    description_hy      TEXT not null
);

CREATE TABLE registration.place
(
    id                      UUID PRIMARY KEY NOT NULL,
    name                    VARCHAR(512) NOT NULL,
    name_hy                 VARCHAR(512) NOT NULL,
    name_ru                 VARCHAR(512) NOT NULL,
    coordinates             VARCHAR(512),
    place_type_id           UUID,
    country_id              UUID,
    climate_zone_id         UUID,
    photo_360_id            UUID,
    description             TEXT,
    description_hy          TEXT,
    description_ru          TEXT,
    road_quality_id         UUID,
    recommended             BOOLEAN,
    transport_type_id       UUID,
    region_id               UUID
);

CREATE TABLE registration.placeNearbyHotel (
                                               place_id          UUID not null,
                                               id                UUID primary key not null,
                                               nearby_hotels_id  UUID not null,
                                               distance          varchar(64)
);

CREATE TABLE registration.placeNearbyHospital (
                                                  place_id            UUID not null,
                                                  id                  UUID primary key not null,
                                                  nearby_hospitals_id UUID not null,
                                                  distance            varchar(64)
);

CREATE TABLE registration.placeNearbyPharmacy (
                                                  place_id            UUID not null,
                                                  id                  UUID primary key not null,
                                                  nearby_pharmacy_id  UUID not null,
                                                  distance            varchar(64)
);

CREATE TABLE registration.placeGasStation (
                                              place_id        UUID not null,
                                              id              UUID primary key not null,
                                              gas_station_id  UUID not null,
                                              distance        varchar(64)
);

CREATE TABLE registration.placeFoodPlace (
                                             place_id       UUID not null,
                                             id             UUID primary key not null,
                                             food_place_id  UUID not null,
                                             distance       varchar(64)
);

CREATE TABLE registration.placePhoto (
                                         place_id  UUID not null,
                                         id        UUID primary key not null,
                                         photo_id  UUID not null
);


CREATE TABLE registration.transportType_to_places
(
    place_id            UUID not null,
    transport_type_id   UUID not null
);

CREATE TABLE registration.keywords
(
    keyword_id          UUID primary key not null,
    name                varchar(16)
);

CREATE TABLE registration.placeKeyword
(
    id                  UUID primary key not null,
    place_id            UUID not null,
    keyword_id          UUID not null
);

CREATE TABLE registration.regions
(
    region_id           UUID primary key not null,
    name                varchar(64),
    country_id          UUID not null,
    name_ru             varchar not null,
    name_hy             varchar not null
);

CREATE TABLE registration.questions
(
    question_id         UUID primary key not null,
    message             TEXT not null,
    email               TEXT not null,
    stats               TEXT not null
);


CREATE TABLE registration.serviceTypes
(
    id               UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    name_ru          VARCHAR(255),
    name_hy          VARCHAR(255),
    description      TEXT,
    description_ru   TEXT,
    description_hy   TEXT
);

CREATE TABLE registration.services
(
    service_id       UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    name_ru          VARCHAR(255),
    name_hy          VARCHAR(255),
    description      TEXT,
    description_ru   TEXT,
    description_hy   TEXT,
    service_type_id  UUID
);
