import "./MovieCard.css";

function MovieCard({ movie }) {

    const baseUrl = "https://image.tmdb.org/t/p/original";
    const posterUrl = `${baseUrl}${movie.backdrop_path}`;


    return (
        <div className="movie-card" >
            <div className="movie-card-image">
                <img src={posterUrl} alt={movie.title} />
            </div>
            <div className="movie-card-content">
            <h2>{movie.title}</h2>
            </div>
        </div>
    );
}

export default MovieCard;