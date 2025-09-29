
import { useEffect, useState } from 'react';
import MovieCard from '../MovieCard';
import "./Movies.css";
import Pagination from '../Pagination';
import { memo } from 'react';

function Movies() {
    const [movies, setMovies] = useState([]);
    const [pageNo, setPageNo] = useState(1);

    useEffect(() => {
        fetch(`https://api.themoviedb.org/3/trending/movie/week?language=en-US&api_key=${process.env.REACT_APP_API_KEY}&page=${pageNo}`)
            .then(response => response.json())
            .then(data => setMovies(data.results))
            .catch(error => console.error(error));
    }, [pageNo]);
    console.log(movies);

    const handleNextPage = () => {
        if (pageNo >= 500) return;
        setPageNo(prev => prev + 1);
    }

    const handlePreviousPage = () => {
        if (pageNo <= 1) return;
        setPageNo(prev => prev - 1);
    }

    return (
        <div>
            <h1>Trending Movies</h1>
            <div className="movies-container">
                {movies.map(movie => (
                    <MovieCard key={movie.id} movie={movie} />
                ))}
            </div>
            <Pagination pageNumber={pageNo}
                nextPageFunc={handleNextPage}
                previousPageFunc={handlePreviousPage} />
        </div>
    );
}


export default memo(Movies);