import React from "react";
import Hav404 from "../../images/Hav-404.png"
import "./index.css"
import "../../../App.css";
import "../../../hover.css";
import "../../../fonts.css";


function NotFound() {
    return (
        <div className="not-found">
            <div className="not-found-container">
                <h1 className="sorry">Sorry</h1>
                <h2 className="page-not-found">Page not found</h2>
            </div>
        </div>
    );
}

export default NotFound;
