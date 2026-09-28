import React, { useEffect } from "react";
import { BrowserRouter, Route, Routes, useLocation } from "react-router";
import Header from "./language/components/Header/Header";
import Footer from "./language/components/Footer/Footer";
import Explore from "./language/pages/Explorer/Explore";
import Home from "./language/pages/Home/Home";
import NotFound from "./language/pages/NotFound/NotFound";
import Login from "./language/pages/Register/Login";
import Image from "./language/pages/Image/Image";
import Signup from "./language/pages/Signup/Signup";
import Test from "./language/pages/Test/Test";
import Howitworks from "./language/pages/Howitworks/Howitworks";
import { LanguageProvider } from "./language/language";
import "./App.css";
import "./hover.css";
import "./fonts.css";
import Database from "./language/pages/CMS/Database";
import ContactUs from "./language/pages/ContactUs/ContactUs";
import Services from "./language/pages/Services/Services";
import Nearbyservicedetail from "./language/pages/NearbyServiceDetail/Nearbyservicedetail";
import ServiceDetail from "./language/pages/Services/ServiceDetail";
import AboutsUs from "./language/pages/AboutUs/AboutUs";

import Hotel from "./language/pages/Hotel/Hotel";
import CreateHotel from "./language/pages/Hotel/CreateHotel";
import EditHotel from "./language/pages/Hotel/EditHotel";
import { HotelProvider } from "./language/pages/Hotel/HotelContext";

import Regions from "./language/pages/Regions/Regions";
import CreateRegion from "./language/pages/Regions/CreateRegion";
import { RegionProvider } from "./language/pages/Regions/RegionContext";

import Countries from "./language/pages/Countries/Countries";
import CreateCountry from "./language/pages/Countries/CreateCountry";
import { CountryProvider } from "./language/pages/Countries/CountryContext";

import PlaceTypes from "./language/pages/PlaceType/PlaceType";
import CreatePlaceType from "./language/pages/PlaceType/CreatePlaceType";
import { PlaceTypeProvider } from "./language/pages/PlaceType/PlaceTypeContext";

import ClimateZones from "./language/pages/ClimateZone/ClimateZone";
import CreateClimateZone from "./language/pages/ClimateZone/CreateClimateZone";
import { ClimateZoneProvider } from "./language/pages/ClimateZone/ClimateZoneContext";

import Restaurant from "./language/pages/Restaurant/Restaurant";
import CreateRestaurant from "./language/pages/Restaurant/CreateRestaurant";
import EditRestaurant from "./language/pages/Restaurant/EditRestaurant";
import { RestaurantProvider } from "./language/pages/Restaurant/RestaurantContext";

import Pharmacies from "./language/pages/Pharmacy/Pharmacies";
import CreatePharmacy from "./language/pages/Pharmacy/CreatePharmacy";
import { PharmacyProvider } from "./language/pages/Pharmacy/PharmacyContext";

import Hospitals from "./language/pages/Hospital/Hospitals";
import CreateHospital from "./language/pages/Hospital/CreateHospital";
import { HospitalProvider } from "./language/pages/Hospital/HospitalContext";

import GasStations from "./language/pages/GasStation/GasStations";
import CreateGasStation from "./language/pages/GasStation/CreateGasStation";
import { GasStationProvider } from "./language/pages/GasStation/GasStationContext";

import Locations from "./language/pages/Locations/Locations";
import CreateLocation from "./language/pages/Locations/CreateLocation";
import { LocationProvider } from "./language/pages/Locations/LocationContext";

import Photos from "./language/pages/Photo/Photos";
import CreatePhoto from "./language/pages/Photo/CreatePhoto";
import { PhotoProvider } from "./language/pages/Photo/PhotoContext";

import NearbyServices from "./language/pages/NearbyServices/NearbyServices";
import NearbyServiceDetail from "./language/pages/NearbyServiceDetail/Nearbyservicedetail";
import AboutUs from "./language/pages/AboutUs/AboutUs";

function ScrollToTop() {
    const { pathname } = useLocation();
    useEffect(() => { window.scrollTo(0, 0); }, [pathname]);
    return null;
}



function App() {
    return (
        <LanguageProvider>
            <CountryProvider>
                <RegionProvider>
                    <PlaceTypeProvider>
                        <ClimateZoneProvider>
                            <PharmacyProvider>
                                <HospitalProvider>
                                    <GasStationProvider>
                                        <RestaurantProvider>
                                            <HotelProvider>
                                                <LocationProvider>
                                                    <PhotoProvider>
                                                        <BrowserRouter>
                                                            <ScrollToTop />
                                                            <Header />
                                                            <Routes>
                                                                <Route path="/" element={<Home />} />
                                                                <Route path="/explore" element={<Explore />} />
                                                                <Route path="/faq" element={<Howitworks />} />
                                                                <Route path="/database" element={<Database />} />
                                                                <Route path="/services" element={<Services />} />
                                                                <Route path="/contactus" element={<ContactUs />} />
                                                                <Route path="/nearby/:uid" element={<Nearbyservicedetail />} />
                                                                <Route path="/nearby/:id" element={<NearbyServiceDetail />} />
                                                                <Route path="/aboutus" element={<AboutUs />} />

                                                                <Route path="/services" element={<Services />} />
                                                                <Route path="/services/:id" element={<ServiceDetail />} />

                                                                {/* Locations */}
                                                                <Route path="/Locations" element={<Locations />} />
                                                                <Route path="/CreateLocation" element={<CreateLocation />} />

                                                                {/* Countries */}
                                                                <Route path="/Countries" element={<Countries />} />
                                                                <Route path="/CreateCountry" element={<CreateCountry />} />

                                                                {/* Regions */}
                                                                <Route path="/Regions" element={<Regions />} />
                                                                <Route path="/CreateRegion" element={<CreateRegion />} />

                                                                {/* Climate Zones */}
                                                                <Route path="/ClimateZones" element={<ClimateZones />} />
                                                                <Route path="/CreateClimateZone" element={<CreateClimateZone />} />

                                                                {/* Place Types */}
                                                                <Route path="/PlaceTypes" element={<PlaceTypes />} />
                                                                <Route path="/CreatePlaceType" element={<CreatePlaceType />} />

                                                                {/* Hotels */}
                                                                <Route path="/Hotel" element={<Hotel />} />
                                                                <Route path="/CreateHotel" element={<CreateHotel />} />
                                                                <Route path="/hotels/:id/edit" element={<EditHotel />} />

                                                                {/* Restaurants */}
                                                                <Route path="/Restaurant" element={<Restaurant />} />
                                                                <Route path="/CreateRestaurant" element={<CreateRestaurant />} />
                                                                <Route path="/restaurants/:id/edit" element={<EditRestaurant />} />

                                                                {/* Pharmacies */}
                                                                <Route path="/Pharmacies" element={<Pharmacies />} />
                                                                <Route path="/CreatePharmacy" element={<CreatePharmacy />} />

                                                                {/* Hospitals */}
                                                                <Route path="/Hospitals" element={<Hospitals />} />
                                                                <Route path="/CreateHospital" element={<CreateHospital />} />

                                                                {/* Gas Stations */}
                                                                <Route path="/GasStations" element={<GasStations />} />
                                                                <Route path="/CreateGasStation" element={<CreateGasStation />} />

                                                                {/* Photos */}
                                                                <Route path="/Photos" element={<Photos />} />
                                                                <Route path="/CreatePhoto" element={<CreatePhoto />} />

                                                                {/* Public pages */}
                                                                <Route path="/location/:id" element={<Image />} />
                                                                <Route path="/location/:id/nearby" element={<NearbyServices />} />
                                                                <Route path="/login" element={<Login />} />
                                                                <Route path="/signup" element={<Signup />} />
                                                                <Route path="/test" element={<Test />} />
                                                                <Route path="*" element={<NotFound />} />
                                                            </Routes>
                                                            <Footer />
                                                        </BrowserRouter>
                                                    </PhotoProvider>
                                                </LocationProvider>
                                            </HotelProvider>
                                        </RestaurantProvider>
                                    </GasStationProvider>
                                </HospitalProvider>
                            </PharmacyProvider>
                        </ClimateZoneProvider>
                    </PlaceTypeProvider>
                </RegionProvider>
            </CountryProvider>
        </LanguageProvider>
    );
}

export default App;