
import "./WatchListCard.css";

function WatchListCard({ movie, removeFromWatchList }) {

    return (
        <div className="watchlist-card">
            <div className="poster-container">
                <img src={`https://image.tmdb.org/t/p/original/${movie?.poster_path}`} alt={movie?.title} />
            </div>
            <h2>{movie?.original_title}</h2>
            <div className="rating">{movie?.vote_average}</div>
            <button className="remove-button" onClick={() => removeFromWatchList(movie)}>Delete</button>
        </div>
    );
}
export default WatchListCard;