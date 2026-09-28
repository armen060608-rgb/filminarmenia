import "../../../App.css";
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { usePhotos } from "./PhotoContext";
import DeleteConfirmModal from "../../components/DeleteConfirmModal";

const PHOTO_BASE = "/registration/api/v1/photo";

function Photos() {
    const { photos, loading, error, deletePhoto } = usePhotos();
    const [nameSearch, setNameSearch] = useState("");
    const [pendingDeleteId, setPendingDeleteId] = useState(null);
    const [lightbox, setLightbox] = useState(null); // { url, name }

    const filtered = photos.filter(p =>
        (p.name ?? "").toLowerCase().includes(nameSearch.toLowerCase())
    );

    const handleConfirmDelete = () => {
        deletePhoto(pendingDeleteId);
        setPendingDeleteId(null);
    };

    const SearchIcon = () => (
        <svg width="22" height="22" viewBox="0 0 32 32" fill="none">
            <path d="M29.5111 32L18.3111 20.8C17.4222 21.5111 16.4 22.0741 15.2444 22.4889C14.0889 22.9037 12.8593 23.1111 11.5556 23.1111C8.32593 23.1111 5.59289 21.9923 3.35645 19.7547C1.12 17.517 0.00118613 14.784 9.40623e-07 11.5556C-0.00118424 8.32711 1.11763 5.59407 3.35645 3.35644C5.59526 1.11881 8.3283 0 11.5556 0C14.7828 0 17.5164 1.11881 19.7564 3.35644C21.9964 5.59407 23.1147 8.32711 23.1111 11.5556C23.1111 12.8593 22.9037 14.0889 22.4889 15.2444C22.0741 16.4 21.5111 17.4222 20.8 18.3111L32 29.5111L29.5111 32ZM11.5556 19.5556C13.7778 19.5556 15.667 18.7781 17.2231 17.2231C18.7793 15.6681 19.5567 13.779 19.5556 11.5556C19.5544 9.33215 18.7769 7.44356 17.2231 5.88978C15.6693 4.336 13.7801 3.55793 11.5556 3.55556C9.33096 3.55319 7.44237 4.33126 5.88978 5.88978C4.33719 7.4483 3.55911 9.33689 3.55556 11.5556C3.552 13.7742 4.33008 15.6634 5.88978 17.2231C7.44948 18.7828 9.33807 19.5603 11.5556 19.5556Z" fill="black"/>
        </svg>
    );

    return (
        <div>
            {pendingDeleteId !== null && (
                <DeleteConfirmModal
                    onConfirm={handleConfirmDelete}
                    onCancel={() => setPendingDeleteId(null)}
                />
            )}

            {/* Lightbox */}
            {lightbox && (
                <div
                    onClick={() => setLightbox(null)}
                    style={{
                        position: "fixed", inset: 0, zIndex: 1000,
                        background: "rgba(0,0,0,0.82)",
                        display: "flex", alignItems: "center", justifyContent: "center",
                        cursor: "zoom-out",
                    }}
                >
                    <div onClick={e => e.stopPropagation()} style={{ maxWidth: "90vw", maxHeight: "90vh", position: "relative" }}>
                        <img
                            src={lightbox.url}
                            alt={lightbox.name}
                            style={{ maxWidth: "90vw", maxHeight: "85vh", borderRadius: 10, display: "block", boxShadow: "0 24px 80px rgba(0,0,0,0.6)" }}
                        />
                        <div style={{ color: "white", textAlign: "center", marginTop: 12, fontSize: 14, opacity: 0.8 }}>
                            {lightbox.name}
                        </div>
                        <button
                            onClick={() => setLightbox(null)}
                            style={{ position: "absolute", top: -16, right: -16, background: "white", border: "none", borderRadius: "50%", width: 32, height: 32, fontSize: 18, cursor: "pointer", display: "flex", alignItems: "center", justifyContent: "center", boxShadow: "0 2px 8px rgba(0,0,0,0.3)" }}
                        >×</button>
                    </div>
                </div>
            )}

            <div className="container__locations">
                <div className="heading__locations">Photos</div>

                {/* Filters */}
                <div className="location__filters">
                    <div className="upper-container__search">
                        <input
                            className="search-left"
                            type="search"
                            placeholder="Name..."
                            value={nameSearch}
                            onChange={e => setNameSearch(e.target.value)}
                        />
                        <SearchIcon />
                    </div>
                </div>

                {loading && <div style={{ padding: "32px 0", textAlign: "center", color: "#888" }}>Loading…</div>}
                {error && <div style={{ padding: "16px", color: "#e55", background: "#fff0f0", borderRadius: 8, marginBottom: 16 }}>{error}</div>}

                {/* Table */}
                <div className="table-wrapper">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Preview</th>
                            <th>Name</th>
                            <th>Size</th>
                            <th>ID</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filtered.map(photo => (
                            <tr key={photo.id}>
                                <td>
                                    <div
                                        onClick={() => setLightbox({ url: photo.url, name: photo.name })}
                                        style={{ cursor: "zoom-in", display: "inline-block" }}
                                        title="Click to enlarge"
                                    >
                                        <img
                                            src={photo.url}
                                            alt={photo.name}
                                            style={{
                                                width: 72,
                                                height: 52,
                                                objectFit: "cover",
                                                borderRadius: 6,
                                                display: "block",
                                                background: "#eef",
                                                transition: "transform 0.15s",
                                            }}
                                            onError={e => {
                                                e.currentTarget.style.display = "none";
                                                e.currentTarget.nextSibling.style.display = "flex";
                                            }}
                                        />
                                        {/* fallback when image 404s */}
                                        <div style={{
                                            display: "none", width: 72, height: 52, borderRadius: 6,
                                            background: "#e8ecf4", alignItems: "center", justifyContent: "center",
                                            fontSize: 10, color: "#8899aa",
                                        }}>
                                            No image
                                        </div>
                                    </div>
                                </td>
                                <td>{photo.name || "—"}</td>
                                <td>{photo.size || "—"}</td>
                                <td style={{ fontFamily: "monospace", fontSize: 12, color: "#666" }}>
                                    {photo.id}
                                </td>
                            </tr>
                        ))}
                        {!loading && filtered.length === 0 && (
                            <tr>
                                <td colSpan={4} style={{ textAlign: "center", padding: "32px 0", color: "#aaa" }}>
                                    No photos found
                                </td>
                            </tr>
                        )}
                        </tbody>
                    </table>

                    <div className="actions-column">
                        <div className="create-wrapper">
                            <Link to="/CreatePhoto" className="btn-create">Upload</Link>
                        </div>
                        {filtered.map(photo => (
                            <div key={photo.id} className="row-actions">
                                <button className="btn-action" title="Delete" onClick={() => setPendingDeleteId(photo.id)}>
                                    <svg width="21" height="23" viewBox="0 0 21 23" fill="none">
                                        <path d="M3.79191 22.7514C3.09673 22.7514 2.50182 22.5041 2.00719 22.0095C1.51255 21.5148 1.26482 20.9195 1.26397 20.2235V3.7919C0.905848 3.7919 0.605867 3.67056 0.364027 3.42788C0.122188 3.1852 0.000847004 2.88522 4.35851e-06 2.52794C-0.000838287 2.17065 0.120503 1.87067 0.364027 1.62799C0.607552 1.38531 0.907533 1.26397 1.26397 1.26397H6.31984C6.31984 0.905844 6.44119 0.605862 6.68387 0.364023C6.92655 0.122184 7.22653 0.000842645 7.58381 0H12.6397C12.9978 0 13.2982 0.121341 13.5409 0.364023C13.7836 0.606705 13.9045 0.906686 13.9037 1.26397H18.9595C19.3176 1.26397 19.6181 1.38531 19.8607 1.62799C20.1034 1.87067 20.2243 2.17065 20.2235 2.52794C20.2227 2.88522 20.1013 3.18562 19.8595 3.42914C19.6176 3.67267 19.3176 3.79359 18.9595 3.7919V20.2235C18.9595 20.9187 18.7122 21.514 18.2176 22.0095C17.7229 22.5049 17.1276 22.7523 16.4316 22.7514H3.79191ZM16.4316 3.7919H3.79191V20.2235H16.4316V3.7919ZM7.58381 17.6956C7.94194 17.6956 8.24234 17.5742 8.48502 17.3315C8.7277 17.0888 8.84862 16.7889 8.84778 16.4316V7.58381C8.84778 7.22568 8.72644 6.9257 8.48376 6.68386C8.24108 6.44202 7.94109 6.32068 7.58381 6.31984C7.22653 6.319 6.92653 6.44034 6.68387 6.68386C6.44119 6.92739 6.31984 7.22737 6.31984 7.58381V16.4316C6.31984 16.7897 6.44119 17.0901 6.68387 17.3328C6.92655 17.5755 7.22653 17.6964 7.58381 17.6956ZM12.6397 17.6956C12.9978 17.6956 13.2982 17.5742 13.5409 17.3315C13.7836 17.0888 13.9045 16.7889 13.9037 16.4316V7.58381C13.9037 7.22568 13.7823 6.9257 13.5396 6.68386C13.2969 6.44202 12.997 6.32068 12.6397 6.31984C12.2824 6.319 11.9824 6.44034 11.7397 6.68386C11.4971 6.92739 11.3757 7.22737 11.3757 7.58381V16.4316C11.3757 16.7897 11.4971 17.0901 11.7397 17.3328C11.9824 17.5755 12.2824 17.6964 12.6397 17.6956Z" fill="black"/>
                                    </svg>
                                </button>
                                <button className="btn-action" title="View full size" onClick={() => setLightbox({ url: photo.url, name: photo.name })}>
                                    <svg width="23" height="16" viewBox="0 0 23 16" fill="none">
                                        <path d="M11.5 0C6.30435 0 1.86957 3.13043 0 7.65217C1.86957 12.1739 6.30435 15.3043 11.5 15.3043C16.6957 15.3043 21.1304 12.1739 23 7.65217C21.1304 3.13043 16.6957 0 11.5 0ZM11.5 12.7536C8.68116 12.7536 6.39855 10.4710 6.39855 7.65217C6.39855 4.83333 8.68116 2.55072 11.5 2.55072C14.3188 2.55072 16.6014 4.83333 16.6014 7.65217C16.6014 10.4710 14.3188 12.7536 11.5 12.7536ZM11.5 4.59420C9.80435 4.59420 8.44203 5.95652 8.44203 7.65217C8.44203 9.34783 9.80435 10.7101 11.5 10.7101C13.1957 10.7101 14.5580 9.34783 14.5580 7.65217C14.5580 5.95652 13.1957 4.59420 11.5 4.59420Z" fill="black"/>
                                    </svg>
                                </button>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Photos;