import "./MovieCard.css";

import { Link } from "react-router-dom";

function MovieCard({ movie, watchList, addToWatchList, removeFromWatchList }) {

    const baseUrl = "https://image.tmdb.org/t/p/original";
    const posterUrl = `${baseUrl}${movie.backdrop_path}`;

    function doesContain() {
        return watchList.includes(movie.id);
    }


    return (
        <div className="movie-card" >
            <div className="movie-card-image">
                <img src={posterUrl} alt={movie.title} />
            </div>

            <div className="movie-card-content">
                <h2>{movie.title}</h2>

            </div>
            <div className="movie-card-footer">
                <div className="movie-card-actions">
                    {/* <a href={`/details/${movie.id}`}>i-href</a> */}
                    <Link to={`/details/${movie.id}`}>i</Link>
                </div>
                {doesContain() ? (
                    <button onClick={() => removeFromWatchList(movie)}>Remove from Watchlist</button>
                ) : (
                    <button onClick={() => addToWatchList(movie)}>Add to Watchlist</button>
                )}

            </div>
        </div>
    );
}

export default MovieCard;