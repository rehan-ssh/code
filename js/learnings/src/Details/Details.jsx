import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Cast from "../Cast";
import DetailsBanner from "../DetailsBanner";

function Details() {
    const [details, setDetails] = useState(null);
    const { id } = useParams();

    useEffect(() => {
        // Fetch movie details using the movie ID
        fetch(`https://api.themoviedb.org/3/movie/${id}?language=en-US&api_key=${process.env.REACT_APP_API_KEY}&append_to_response=credits,videos`)
            .then(response => response.json())
            .then(data => {
                // Handle movie details data
                console.log("DATA", data);
                setDetails(data);
            })
            .catch(error => console.error(error));
    }, [id]);
    return (
        <div>
            {details && <DetailsBanner details={details} />}
            {details && <Cast cast={details?.credits?.cast} />}
        </div>
    );
}

export default Details;
