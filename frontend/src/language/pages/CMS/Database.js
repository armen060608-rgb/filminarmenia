import React from "react";
import "../../../App.css";
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import { Link } from "react-router-dom";

const ArrowIcon = () => (
    <svg width="114" height="47" viewBox="0 0 114 47" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect width="114" height="47" rx="5" fill="#33673B"/>
        <path d="M88.4142 24.4142C89.1953 23.6332 89.1953 22.3668 88.4142 21.5858L75.6863 8.85786C74.9052 8.07682 73.6389 8.07682 72.8579 8.85786C72.0768 9.63891 72.0768 10.9052 72.8579 11.6863L84.1716 23L72.8579 34.3137C72.0768 35.0948 72.0768 36.3611 72.8579 37.1421C73.6389 37.9232 74.9052 37.9232 75.6863 37.1421L88.4142 24.4142ZM28 23V25L87 25V23V21L28 21V23Z" fill="white"/>
    </svg>
);

const rows = [
    { label: "Locations",    to: "/Locations" },
    { label: "Countries",    to: "/Countries" },
    { label: "Photos",    to: "/Photos" },
    { label: "Regions",      to: "/Regions" },
    { label: "Climate Zones",to: "/ClimateZones" },
    { label: "Place Types",  to: "/PlaceTypes" },
    { label: "Hotels",       to: "/Hotel" },
    { label: "Food Places",  to: "/Restaurant" },
    { label: "Pharmacies",   to: "/Pharmacies" },
    { label: "Hospitals",    to: "/Hospitals" },
    { label: "Gas Stations", to: "/GasStations" },
];

function Database() {
    return (
        <div>
            <div className="container__database">
                <div className="heading__database">Database</div>
                {rows.map(({ label, to }) => (
                    <div className="container__database__header" key={to}>
                        <div className="row__database">
                            {label}
                            <Link className="database__to-links" to={to}>
                                <ArrowIcon />
                            </Link>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Database;