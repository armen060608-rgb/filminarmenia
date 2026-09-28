import "../../../App.css";
import "../../../hover.css";
import "./index.css";
import "../../../fonts.css";
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useRestaurants } from "./RestaurantContext";

function EditRestaurant() {
    const { id } = useParams();
    const { restaurants, updateRestaurant } = useRestaurants();
    const navigate = useNavigate();
    const [submitting, setSubmitting] = useState(false);

    const restaurant = restaurants.find(r => String(r.id) === String(id));

    const [formData, setFormData] = useState({
        name_hy: restaurant?.name_hy ?? "",
        name_en: restaurant?.name_en ?? "",
        name_ru: restaurant?.name_ru ?? "",
        description_hy: restaurant?.description_hy ?? "",
        description_en: restaurant?.description_en ?? "",
        description_ru: restaurant?.description_ru ?? "",
        address: restaurant?.address ?? "",
        phone: restaurant?.phone ?? "",
        cuisine: restaurant?.cuisine ?? "",
    });

    if (!restaurant) {
        return (
            <div className="container__regions">
                <div className="heading__regions">Restaurant not found</div>
            </div>
        );
    }

    const handleChange = (e) => setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSubmitting(true);
        await updateRestaurant({
            id: restaurant.id,
            locationId: restaurant.locationId,
            name_en: formData.name_en.trim(), name_hy: formData.name_hy.trim(), name_ru: formData.name_ru.trim(),
            description_en: formData.description_en.trim(), description_hy: formData.description_hy.trim(), description_ru: formData.description_ru.trim(),
            address: formData.address.trim(), phone: formData.phone.trim(), cuisine: formData.cuisine.trim(),
        });
        setSubmitting(false);
        navigate("/Restaurant");
    };

    const fields = [
        { name: "name_hy", label: "Name (HY)" }, { name: "name_en", label: "Name (EN)" }, { name: "name_ru", label: "Name (RU)" },
        { name: "description_hy", label: "Description (HY)" }, { name: "description_en", label: "Description (EN)" }, { name: "description_ru", label: "Description (RU)" },
        { name: "address", label: "Address" }, { name: "phone", label: "Phone" }, { name: "cuisine", label: "Cuisine" },
    ];

    return (
        <div>
            <div className="container__regions">
                <div className="heading__regions">Edit Food Place</div>
                <div className="content">
                    <form onSubmit={handleSubmit} className="create-hotel__form">
                        <div className="create-hotel__fields">
                            {fields.map(({ name, label }) => (
                                <div className="create-hotel__field-row" key={name}>
                                    <label className="create-hotel__field-label" htmlFor={name}>{label}</label>
                                    <input className="create-hotel__field-input" id={name} name={name} type="text"
                                           value={String(formData[name] ?? "")} onChange={handleChange} />
                                </div>
                            ))}
                        </div>
                        <div className="create-hotel__submit-row">
                            <button type="submit" className="tab-content__more-btn" disabled={submitting}>
                                {submitting ? "Saving..." : "Save"}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default EditRestaurant;