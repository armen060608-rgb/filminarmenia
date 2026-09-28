import "../../../App.css";
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { useRestaurants } from "./RestaurantContext";
import DeleteConfirmModal from "../../components/DeleteConfirmModal";

function Restaurant() {
    const { restaurants, loading, error, deleteRestaurant, refetch } = useRestaurants();
    const [nameSearch, setNameSearch] = useState("");
    const [pendingDeleteId, setPendingDeleteId] = useState(null);
    const navigate = useNavigate();

    const filtered = restaurants.filter(row =>
        row.name_en?.toLowerCase().includes(nameSearch.toLowerCase()) ||
        row.name_hy?.toLowerCase().includes(nameSearch.toLowerCase()) ||
        row.name_ru?.toLowerCase().includes(nameSearch.toLowerCase())
    );

    const handleDeleteClick = (id) => setPendingDeleteId(id);
    const handleConfirmDelete = () => { deleteRestaurant(pendingDeleteId); setPendingDeleteId(null); };
    const handleCancelDelete = () => setPendingDeleteId(null);

    const SearchIcon = () => (
        <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M29.5111 32L18.3111 20.8C17.4222 21.5111 16.4 22.0741 15.2444 22.4889C14.0889 22.9037 12.8593 23.1111 11.5556 23.1111C8.32593 23.1111 5.59289 21.9923 3.35645 19.7547C1.12 17.517 0.00118613 14.784 9.40623e-07 11.5556C-0.00118424 8.32711 1.11763 5.59407 3.35645 3.35644C5.59526 1.11881 8.3283 0 11.5556 0C14.7828 0 17.5164 1.11881 19.7564 3.35644C21.9964 5.59407 23.1147 8.32711 23.1111 11.5556C23.1111 12.8593 22.9037 14.0889 22.4889 15.2444C22.0741 16.4 21.5111 17.4222 20.8 18.3111L32 29.5111L29.5111 32ZM11.5556 19.5556C13.7778 19.5556 15.667 18.7781 17.2231 17.2231C18.7793 15.6681 19.5567 13.779 19.5556 11.5556C19.5544 9.33215 18.7769 7.44356 17.2231 5.88978C15.6693 4.336 13.7801 3.55793 11.5556 3.55556C9.33096 3.55319 7.44237 4.33126 5.88978 5.88978C4.33719 7.4483 3.55911 9.33689 3.55556 11.5556C3.552 13.7742 4.33008 15.6634 5.88978 17.2231C7.44948 18.7828 9.33807 19.5603 11.5556 19.5556Z" fill="black"/>
        </svg>
    );

    return (
        <div>
            {pendingDeleteId !== null && (
                <DeleteConfirmModal onConfirm={handleConfirmDelete} onCancel={handleCancelDelete} />
            )}
            <div className="container__regions">
                <div className="heading__regions">Food Places</div>
                <div className="upper-container__regions">
                    <div className="upper-container__search">
                        <input id="restaurant-search" name="restaurant-search" className="search-left" type="search"
                               placeholder="Name" value={nameSearch} onChange={e => setNameSearch(e.target.value)} />
                        <SearchIcon />
                    </div>
                </div>
                {error && (
                    <div style={{ color: "red", marginBottom: 12 }}>
                        Error: {error} — <button onClick={refetch} style={{ textDecoration: "underline", cursor: "pointer" }}>Retry</button>
                    </div>
                )}
                {loading ? (
                    <div style={{ padding: "32px", textAlign: "center" }}>Loading...</div>
                ) : (
                    <div className="table-wrapper">
                        <table className="table">
                            <colgroup>
                                <col style={{ width: "18%" }} /><col style={{ width: "18%" }} /><col style={{ width: "18%" }} />
                                <col style={{ width: "18%" }} /><col style={{ width: "14%" }} /><col style={{ width: "14%" }} />
                            </colgroup>
                            <thead>
                            <tr>{["Name (HY)", "Name (EN)", "Name (RU)", "Address", "Phone", "Cuisine"].map(col => <th key={col}>{col}</th>)}</tr>
                            </thead>
                            <tbody>
                            {filtered.map(row => (
                                <tr key={row.id}>
                                    <td>{row.name_hy}</td>
                                    <td>{row.name_en}</td>
                                    <td>{row.name_ru}</td>
                                    <td>{row.address}</td>
                                    <td>{row.phone}</td>
                                    <td>{row.cuisine}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                        <div className="actions-column">
                            <div className="create-wrapper">
                                <Link to="/CreateRestaurant" className="btn-create">Create</Link>
                            </div>
                            {filtered.map(row => (
                                <div key={row.id} className="row-actions">
                                    <button className="btn-action" title="Delete" onClick={() => handleDeleteClick(row.id)}>
                                        <svg width="21" height="23" viewBox="0 0 21 23" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M3.79191 22.7514C3.09673 22.7514 2.50182 22.5041 2.00719 22.0095C1.51255 21.5148 1.26482 20.9195 1.26397 20.2235V3.7919C0.905848 3.7919 0.605867 3.67056 0.364027 3.42788C0.122188 3.1852 0.000847004 2.88522 4.35851e-06 2.52794C-0.000838287 2.17065 0.120503 1.87067 0.364027 1.62799C0.607552 1.38531 0.907533 1.26397 1.26397 1.26397H6.31984C6.31984 0.905844 6.44119 0.605862 6.68387 0.364023C6.92655 0.122184 7.22653 0.000842645 7.58381 0H12.6397C12.9978 0 13.2982 0.121341 13.5409 0.364023C13.7836 0.606705 13.9045 0.906686 13.9037 1.26397H18.9595C19.3176 1.26397 19.6181 1.38531 19.8607 1.62799C20.1034 1.87067 20.2243 2.17065 20.2235 2.52794C20.2227 2.88522 20.1013 3.18562 19.8595 3.42914C19.6176 3.67267 19.3176 3.79359 18.9595 3.7919V20.2235C18.9595 20.9187 18.7122 21.514 18.2176 22.0095C17.7229 22.5049 17.1276 22.7523 16.4316 22.7514H3.79191ZM16.4316 3.7919H3.79191V20.2235H16.4316V3.7919ZM7.58381 17.6956C7.94194 17.6956 8.24234 17.5742 8.48502 17.3315C8.7277 17.0888 8.84862 16.7889 8.84778 16.4316V7.58381C8.84778 7.22568 8.72644 6.9257 8.48376 6.68386C8.24108 6.44202 7.94109 6.32068 7.58381 6.31984C7.22653 6.319 6.92655 6.44034 6.68387 6.68386C6.44119 6.92739 6.31984 7.22737 6.31984 7.58381V16.4316C6.31984 16.7897 6.44119 17.0901 6.68387 17.3328C6.92655 17.5755 7.22653 17.6964 7.58381 17.6956ZM12.6397 17.6956C12.9978 17.6956 13.2982 17.5742 13.5409 17.3315C13.7836 17.0888 13.9045 16.7889 13.9037 16.4316V7.58381C13.9037 7.22568 13.7823 6.9257 13.5396 6.68386C13.2969 6.44202 12.997 6.32068 12.6397 6.31984C12.2824 6.319 11.9824 6.44034 11.7397 6.68386C11.4971 6.92739 11.3757 7.22737 11.3757 7.58381V16.4316C11.3757 16.7897 11.4971 17.0901 11.7397 17.3328C11.9824 17.5755 12.2824 17.6964 12.6397 17.6956Z" fill="black"/></svg>
                                    </button>
                                    <button className="btn-action" title="Edit" onClick={() => navigate(`/restaurants/${row.id}/edit`)}>
                                        <svg width="23" height="23" viewBox="0 0 23 23" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M2.52794 20.2235H4.32909L16.6844 7.8682L14.8832 6.06705L2.52794 18.4223V20.2235ZM0 22.7514V17.3796L16.6844 0.726782C16.9372 0.495054 17.2165 0.315992 17.5224 0.189595C17.8283 0.0631985 18.1493 0 18.4855 0C18.8217 0 19.1483 0.0631985 19.4651 0.189595C19.7819 0.315992 20.0558 0.505587 20.2867 0.758381L22.0246 2.52794C22.2774 2.75966 22.462 3.03352 22.5783 3.34952C22.6945 3.66551 22.7523 3.9815 22.7514 4.29749C22.7514 4.63455 22.6937 4.95602 22.5783 5.2619C22.4628 5.56778 22.2783 5.84669 22.0246 6.09865L5.37186 22.7514H0ZM15.768 6.98342L14.8832 6.06705L16.6844 7.8682L15.768 6.98342Z" fill="black"/></svg>
                                    </button>
                                </div>
                            ))}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Restaurant;