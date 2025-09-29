import "./MovieCard.css";

import { Link } from "react-router-dom";
import { MovieContext } from "../Context/MovieContext";
import { useContext } from "react";
import { useDispatch, useSelector } from "react-redux";

function MovieCard({ movie }) {

    const dispatch = useDispatch();
    const watchList = useSelector(state => state.watchList);

    // const { watchList, addToWatchList, removeFromWatchList } = useContext(MovieContext);

    const baseUrl = "https://image.tmdb.org/t/p/original";
    const posterUrl = `${baseUrl}${movie.backdrop_path}`;

    function doesContain() {
        return watchList.includes(movie.id);
    }

    function handleAddToWatchList() {
        // removeFromWatchList(movie)
        dispatch({ type: 'ADD_TO_WATCHLIST', payload: movie });
    }

    function handleRemoveFromWatchList() {
        // addToWatchList(movie)
        console.log("movie", watchList);

        dispatch({ type: 'REMOVE_FROM_WATCHLIST', payload: movie });
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
                    <button onClick={handleRemoveFromWatchList}>Remove from Watchlist</button>
                ) : (
                    <button onClick={handleAddToWatchList}>Add to Watchlist</button>
                )}

            </div>
        </div>
    );
}

export default MovieCard;