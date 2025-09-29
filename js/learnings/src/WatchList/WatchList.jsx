import { useContext, useEffect, useState } from "react";

import { MovieContext } from "../Context/MovieContext";
import WatchListCard from "../WatchListCard";
import { useSelector, useDispatch } from "react-redux";


function WatchList() {
    const [search, setSearch] = useState("");
    const dispatch = useDispatch();
    const watchList = useSelector(state => state.watchList);
    // const { watchList, removeFromWatchList } = useContext(MovieContext);
    const [details, setDetails] = useState([]);

    // below renders twice as react mounts twice in dev mode to check side effects
    // useEffect(() => {
    //     setDetails([]);
    //     watchList.forEach(id => {
    //         fetch(`https://api.themoviedb.org/3/movie/${id}?language=en-US&api_key=${process.env.REACT_APP_API_KEY}&append_to_response=credits,videos`)
    //             .then(response => response.json())
    //             .then(data => {
    //                 // Handle movie details data
    //                 setDetails(prevDetails => [...prevDetails, data]);
    //             })
    //             .catch(error => console.error(error));
    //     });
    // }, [watchList]);

    useEffect(() => {
        const controller = new AbortController();
        const signal = controller.signal;
        const uniqueIds = [...new Set(watchList)];

        Promise.all(
            uniqueIds.map(id =>
                fetch(`https://api.themoviedb.org/3/movie/${id}?language=en-US&api_key=${process.env.REACT_APP_API_KEY}&append_to_response=credits,videos`, { signal })
                    .then(res => res.json())
            )
        )
            .then(data => !signal.aborted && setDetails(data))
            .catch(err => {
                if (err.name !== "AbortError") console.error(err);
            });

        return () => controller.abort();
    }, [watchList]);

    function handleSortAscending() {
        const sorted = [...details].sort((a, b) => a.vote_average - b.vote_average);
        setDetails(sorted);
    }

    function handleSortDescending() {
        const sorted = [...details].sort((a, b) => b.vote_average - a.vote_average);
        setDetails(sorted);
    }

    return (
        <div>
            <input
                type="text"
                placeholder="Search..."
                value={search}
                onChange={e => setSearch(e.target.value)}
            />
            <div className="watchlist-sort-buttons">
                <button onClick={handleSortAscending}>Sort Ascending</button>
                <button onClick={handleSortDescending}>Sort Descending</button>
            </div>
            {details
                .filter(movie => movie.original_title.toLowerCase().includes(search.toLowerCase()))
                .map(movie => (
                    <WatchListCard key={movie.id} movie={movie} removeFromWatchList={() => dispatch({ type: 'REMOVE_FROM_WATCHLIST', payload: movie })} />
                ))}
        </div>
    );
}

export default WatchList;