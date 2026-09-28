import "../../../App.css"
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { usePlaceTypes } from "./PlaceTypeContext";

function CreatePlaceType() {
    const { addPlaceType } = usePlaceTypes();
    const navigate = useNavigate();
    const [submitting, setSubmitting] = useState(false);

    const [formData, setFormData] = useState({
        name_hy: "",
        name_en: "",
        name_ru: "",
        description_hy: "",
        description_en: "",
        description_ru: "",
    });

    const handleChange = (e) => {
        setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.name_hy.trim() && !formData.name_en.trim() && !formData.name_ru.trim()) {
            alert("Please enter the place type name in at least one language.");
            return;
        }

        setSubmitting(true);
        await addPlaceType({
            name_en: formData.name_en.trim(),
            name_hy: formData.name_hy.trim(),
            name_ru: formData.name_ru.trim(),
            description_en: formData.description_en.trim(),
            description_hy: formData.description_hy.trim(),
            description_ru: formData.description_ru.trim(),
        });
        setSubmitting(false);
        navigate("/PlaceTypes");
    };

    const fields = [
        { name: "name_hy",        label: "Name (HY)" },
        { name: "name_en",        label: "Name (EN)" },
        { name: "name_ru",        label: "Name (RU)" },
        { name: "description_hy", label: "Description (HY)" },
        { name: "description_en", label: "Description (EN)" },
        { name: "description_ru", label: "Description (RU)" },
    ];

    return (
        <div>
            <div className="container__regions">
                <div className="heading__regions">Create Place Type</div>
                <div className="content">
                    <form onSubmit={handleSubmit} className="create-hotel__form">
                        <div className="create-hotel__fields">
                            {fields.map(({ name, label }) => (
                                <div className="create-hotel__field-row" key={name}>
                                    <label className="create-hotel__field-label" htmlFor={name}>
                                        {label}
                                    </label>
                                    <input
                                        className="create-hotel__field-input"
                                        id={name}
                                        name={name}
                                        type="text"
                                        value={formData[name]}
                                        onChange={handleChange}
                                    />
                                </div>
                            ))}
                        </div>
                        <div className="create-hotel__submit-row">
                            <button
                                type="submit"
                                className="tab-content__more-btn"
                                disabled={submitting}
                            >
                                {submitting ? "Saving..." : "Save"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CreatePlaceType;