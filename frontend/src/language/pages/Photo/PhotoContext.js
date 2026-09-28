import React, { createContext, useContext, useState, useEffect } from "react";

const PHOTO_API = "/registration/api/v1/photo";

const PhotoContext = createContext(null);

/**
 * Returns all photo URLs whose path matches the given region + locationName.
 * e.g. path = "http://10.40.44.2/media/Yerevan/Yerevan%20Tower%20(05.04.2023)/027.avif"
 *   → segments[0] = "Yerevan", segments[1] = "Yerevan Tower (05.04.2023)"
 * We match region against segments[0] and name_en against the START of segments[1].
 */


export function PhotoProvider({ children }) {
    const [photos, setPhotos] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const getPhotosForLocation = (region, nameEn) => {
        if (!region || !nameEn) return [];
        const regionNorm = region.trim().toLowerCase();
        const nameNorm   = nameEn.trim().toLowerCase();

        return photos
            .filter(photo => {
                try {
                    // decode the path so %20 → space, %28 → ( etc.
                    const decoded = decodeURIComponent(photo.path ?? "");
                    // extract the part after /media/
                    const afterMedia = decoded.split("/media/")[1] ?? "";
                    const parts = afterMedia.split("/");          // ["Yerevan", "Yerevan Tower (05.04.2023)", "027.avif"]
                    const seg0 = (parts[0] ?? "").trim().toLowerCase();  // region
                    const seg1 = (parts[1] ?? "").trim().toLowerCase();  // folder name (starts with location name)
                    return seg0 === regionNorm && seg1.startsWith(nameNorm);
                } catch { return false; }
            })
            .map(photo => photo.path);  // return the URL directly
    };


    const fetchPhotos = async () => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch(`${PHOTO_API}/getPhotos`, {
                headers: { accept: "*/*", "Accept-Language": "en" },
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            if (!Array.isArray(data)) { setPhotos([]); return; }

            setPhotos(data.map(item => ({
                id: item.id,
                name: item.name ?? "",
                nameHy: item.nameHy ?? "",
                nameRu: item.nameRu ?? "",
                size: item.size ?? "",
                path: item.path ?? "",
                description: item.description ?? "",
                // Serve the image directly via getPhoto/{id}
                url: item.path ?? "",
            })));
        } catch (err) {
            setError("Could not load photos: " + err.message);
        } finally {
            setLoading(false);
        }
    };


    useEffect(() => { fetchPhotos(); }, []);

    /**
     * Creates a photo record by sending JSON metadata.
     * The backend stores name/size/path as strings — no file bytes are sent.
     * Returns the created photo object or null on failure.
     */
    const createPhoto = async ({ file, name, nameHy, nameRu, size, path, description, descriptionHy, descriptionRu }) => {
        try {
            const formData = new FormData();
            if (file) formData.append("file", file); // ← key must match what backend expects
            formData.append("name", name ?? "");
            if (nameHy)        formData.append("nameHy", nameHy);
            if (nameRu)        formData.append("nameRu", nameRu);
            if (size)          formData.append("size", size);
            if (path)          formData.append("path", path);
            if (description)   formData.append("description", description);
            if (descriptionHy) formData.append("descriptionHy", descriptionHy);
            if (descriptionRu) formData.append("descriptionRu", descriptionRu);

            const res = await fetch(`${PHOTO_API}/createPhoto`, {
                method: "POST",
                headers: {
                    accept: "*/*",
                    "Accept-Language": "en",
                    // ⚠️ Do NOT set Content-Type — browser sets it automatically with the multipart boundary
                },
                body: formData,
            });

            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const item = await res.json();
            const newPhoto = {
                id: item.id,
                name: item.name ?? name,
                nameHy: item.nameHy ?? nameHy ?? "",
                nameRu: item.nameRu ?? nameRu ?? "",
                size: item.size ?? size ?? "",
                path: item.path ?? path ?? "",
                description: item.description ?? description ?? "",
                url: `${PHOTO_API}/getPhoto/${item.id}`,
            };
            setPhotos(prev => [...prev, newPhoto]);
            return newPhoto;
        } catch (err) {
            setError("Could not create photo: " + err.message);
            return null;
        }
    };

    const deletePhoto = async (id) => {
        try {
            const res = await fetch(`${PHOTO_API}/deletePhoto/${id}`, { method: "DELETE" });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            setPhotos(prev => prev.filter(p => p.id !== id));
        } catch (err) {
            setError("Could not delete photo: " + err.message);
        }
    };

    return (
        <PhotoContext.Provider value={{
            photos, loading, error,
            createPhoto, deletePhoto,
            refetch: fetchPhotos,
            getPhotosForLocation,   // ← add this
        }}>
            {children}
        </PhotoContext.Provider>
    );
}

export function usePhotos() { return useContext(PhotoContext); }