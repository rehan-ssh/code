
import { useEffect, useState } from 'react';
import MovieCard from '../MovieCard';
import "./Movies.css";

function Movies() {
    const [movies, setMovies] = useState([]);

    useEffect(() => {
        fetch("https://api.themoviedb.org/3/trending/movie/week?language=en-US&api_key=135f939e0c7197a54788410a99af6f0b")
            .then(response => response.json())
            .then(data => setMovies(data.results))
            .catch(error => console.error(error));
    }, []);
    console.log(movies);


    return (
        <div>
            <h1>Trending Movies</h1>
            <div className="movies-container">
            {movies.map(movie => (
                <MovieCard key={movie.id} movie={movie} />
            ))}
            </div>
        </div>
    );
}


export default Movies;