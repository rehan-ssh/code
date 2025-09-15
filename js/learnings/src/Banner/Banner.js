import { useState, useEffect } from "react";
import './Banner.css';

function Banner() {
    const [film, setFilm] = useState(null);

    useEffect(() => {
        fetch(`https://api.themoviedb.org/3/trending/movie/week?language=en-US&api_key=${process.env.REACT_APP_API_KEY}`)
            .then(response => response.json())
            .then(data => {
                let id = Math.floor(Math.random() * data.results.length);
                setFilm(data.results[id]);
                console.log(film); // this gives no result as film is not set just after setFilm it gets set after the re-render.
            })
            .catch(error => console.error(error));
    }, []);
    console.log(film);


    return (
        <div className="banner">
            <h1>Welcome to Movie App</h1>
            {film && (
                <>
                    <div className="film-poster">
                        <img src={`https://image.tmdb.org/t/p/original${film.backdrop_path}`} alt={film.title} />
                    </div>
                    <div className="film-details">
                        <h2>{film.title}</h2>
                        <p>{film.overview}</p>
                    </div>
                </>
            )}
        </div>
    );
}
export default Banner;