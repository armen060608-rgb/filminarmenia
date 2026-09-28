import "../../../App.css";
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { usePhotos } from "./PhotoContext";

const EMPTY = {
    name: "", nameHy: "", nameRu: "",
    path: "", size: "",
    description: "", descriptionHy: "", descriptionRu: "",
};

function CreatePhoto() {
    const { createPhoto } = usePhotos();
    const navigate = useNavigate();
    const [activeTab, setActiveTab] = useState("en");
    const [form, setForm] = useState(EMPTY);
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState(null);

    // Add to initial state
    const EMPTY = {
        name: "", nameHy: "", nameRu: "",
        path: "", size: "",
        description: "", descriptionHy: "", descriptionRu: "",
        file: null,  // ← add this
    };

    const tabs = [
        { key: "en", label: "English" },
        { key: "hy", label: "Armenian" },
        { key: "ru", label: "Russian" },
    ];

    const set = (key, value) => setForm(prev => ({ ...prev, [key]: value }));

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!form.name.trim()) { setError("English name is required."); return; }
        setError(null);
        setSubmitting(true);
        try {
            const result = await createPhoto({
                file: form.file,  // ← add this
                name:           form.name,
                nameHy:         form.nameHy || null,
                nameRu:         form.nameRu || null,
                path:           form.path || null,
                size:           form.size || null,
                description:    form.description || null,
                descriptionHy:  form.descriptionHy || null,
                descriptionRu:  form.descriptionRu || null,
            });
            if (result) {
                navigate("/Photos");
            } else {
                setError("Failed to save. Check the console for details.");
            }
        } finally {
            setSubmitting(false);
        }
    };

    const renderTab = () => {
        if (activeTab === "en") return (
            <>
                <div className="create-hotel__field-row">
                    <label className="create-hotel__field-label">Image file *</label>
                    <input
                        type="file"
                        accept="image/*"
                        onChange={e => {
                            const file = e.target.files[0];
                            if (file) {
                                set("file", file);
                                // Auto-fill name and size if empty
                                if (!form.name) set("name", file.name.replace(/\.[^/.]+$/, ""));
                                set("size", (file.size / 1024).toFixed(1) + " kb");
                            }
                        }}
                    />
                </div>
            </>
        );
        if (activeTab === "hy") return (
            <>
                <div className="create-hotel__field-row">
                    <label className="create-hotel__field-label">Name (HY)</label>
                    <input className="create-hotel__field-input" type="text"
                           value={form.nameHy} onChange={e => set("nameHy", e.target.value)}/>
                </div>
                <div className="create-hotel__field-row">
                    <label className="create-hotel__field-label">Description (HY)</label>
                    <input className="create-hotel__field-input" type="text"
                           value={form.descriptionHy} onChange={e => set("descriptionHy", e.target.value)} />
                </div>
            </>
        );
        if (activeTab === "ru") return (
            <>
                <div className="create-hotel__field-row">
                    <label className="create-hotel__field-label">Name (RU)</label>
                    <input className="create-hotel__field-input" type="text"
                           value={form.nameRu} onChange={e => set("nameRu", e.target.value)} />
                </div>
                <div className="create-hotel__field-row">
                    <label className="create-hotel__field-label">Description (RU)</label>
                    <input className="create-hotel__field-input" type="text"
                           value={form.descriptionRu} onChange={e => set("descriptionRu", e.target.value)} />
                </div>
            </>
        );
    };

    return (
        <div>
            <div className="container__locations">
                <div className="heading__locations">Create Photo</div>

                {/* Language tabs */}
                <div className="tabs">
                    {tabs.map(({ key, label }) => (
                        <button key={key}
                                className={activeTab === key ? "active" : ""}
                                onClick={() => setActiveTab(key)}>
                            {label}
                        </button>
                    ))}
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="tab-content">
                        <div className="create-hotel__fields">

                            {/* Tab-specific fields (name + description per language) */}
                            {renderTab()}

                            {/* Shared fields — always visible */}
                            <div className="create-hotel__field-row">
                                <label className="create-hotel__field-label">File path</label>
                                <input className="create-hotel__field-input" type="text"
                                       value={form.path}
                                       onChange={e => set("path", e.target.value)}
                                       placeholder="/Users/yourname/Desktop/photo.jpg" />
                                <span style={{ fontSize: 12, color: "#99a", marginTop: 4 }}>
                                    The server-side path where the image file is stored
                                </span>
                            </div>

                            <div className="create-hotel__field-row">
                                <label className="create-hotel__field-label">Size</label>
                                <input className="create-hotel__field-input" type="text"
                                       value={form.size}
                                       onChange={e => set("size", e.target.value)}
                                       placeholder="e.g. 9kb, 2.4mb" />
                            </div>

                            {error && (
                                <div style={{ color: "#ef4444", background: "#fff5f5", border: "1px solid #fecaca", borderRadius: 8, padding: "10px 14px", fontSize: 14 }}>
                                    {error}
                                </div>
                            )}
                        </div>
                    </div>

                    <div className="create-hotel__submit-row" style={{ display: "flex", gap: 12 }}>
                        <button type="submit" className="tab-content__more-btn" disabled={submitting}>
                            {submitting ? "Saving…" : "Save"}
                        </button>
                        <button type="button"
                                className="tab-content__more-btn"
                                style={{ background: "transparent", color: "#1e2a4a", border: "1.5px solid #1e2a4a" }}
                                onClick={() => navigate("/Photos")}>
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default CreatePhoto;