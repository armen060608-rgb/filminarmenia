import axios from "axios";
import {useEffect, useState} from "react";

const api = axios.create({
    baseURL: "http://10.40.44.2:5101/registration/swagger-ui/index.html", // same as Swagger server
});

function Photos() {
    const [photos, setPhotos] = useState([]);

    useEffect(() => {
        api
            .get("/api/v1/photo/getPhotos")
            .then(res => setPhotos(res.data))
            .catch(err => console.error(err));
    }, []);

    return (
        <div style={{display: "flex", gap: 12, flexWrap: "wrap"}}>
            {photos.map(photo => (
                <img
                    key={photo.id}
                    src={photo.url}
                    alt="photo"
                    width={200}
                />
            ))}
        </div>
    );
}

export default Photos;
