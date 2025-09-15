
import "./DetailsBanner.css";
function DetailsBanner({ details: movie }) {

    const baseUrl = "https://image.tmdb.org/t/p/original";
    return (
        <div className="details-banner">
            <img src={`${baseUrl}${movie.backdrop_path}`} alt={movie.title} />
            <div className="details-banner-content">
                <h1>{movie.title}</h1>
                <p>{movie.overview}</p>
                {movie && <a href={`https://www.youtube.com/watch?v=${movie.videos.results[1].key}`} target="_blank" rel="noopener noreferrer">Watch trailer</a>}
            </div>
        </div>
    );
}

export default DetailsBanner;