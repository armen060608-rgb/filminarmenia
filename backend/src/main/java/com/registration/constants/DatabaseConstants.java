package com.registration.constants;

public class DatabaseConstants {

    public static final String SCHEMA_NAME = "registration";
    public static final String USER_TABLE_NAME = "users";
    public static final String PLACE_TABLE_NAME = "place";
    public static final String COUNTRY_TABLE_NAME = "countries";
    public static final String FOOD_TABLE_NAME = "foodplaces";
    public static final String GAS_STATION_TABLE_NAME = "gasstations";
    public static final String NEARBY_HOTELS_TABLE_NAME = "nearbyhotels";
    public static final String NEARBY_HOSPITALS_TABLE_NAME = "nearbyhospitals";
    public static final String NEARBY_PHARMACIES_TABLE_NAME = "nearbypharmacies";
    public static final String PHOTOS_TABLE_NAME = "photos";
    public static final String PHOTO_360_TABLE_NAME = "photo360";
    public static final String ROAD_QUALITY_TABLE_NAME = "roadquality";
    public static final String TRANSPORT_TYPE_TABLE_NAME = "transporttype";
    public static final String CLIMATE_ZONE_TABLE_NAME = "climatezones";
    public static final String KEYWORDS_TABLE_NAME = "keywords";
    public static final String PLACE_TYPE_TABLE_NAME = "placetype";
    public static final String PLACE_KEYWORD_TABLE_NAME = "place_keyword";
    public static final String REGION_TABLE_NAME = "regions";
    public static final String QUESTIONS_TABLE_NAME = "questions";
    public static final String FAQS_TABLE_NAME = "faq";

    // ---- NEW: junction tables (Place ↔ N items + distance / photo) ----
    public static final String PLACE_HOTEL_TABLE_NAME     = "place_nearby_hotel";
    public static final String PLACE_HOSPITAL_TABLE_NAME  = "place_nearby_hospital";
    public static final String PLACE_PHARMACY_TABLE_NAME  = "place_nearby_pharmacy";
    public static final String PLACE_GAS_STATION_TABLE_NAME = "place_gas_station";
    public static final String PLACE_FOOD_PLACE_TABLE_NAME  = "place_food_place";
    public static final String PLACE_PHOTO_TABLE_NAME       = "place_photo";


    public static final String SERVICE_TYPE_TABLE_NAME = "servicetypes";
    public static final String SERVICE_TABLE_NAME      = "services";
}
