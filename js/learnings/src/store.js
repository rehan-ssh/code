import { createStore } from "redux";

let watchList = JSON.parse(localStorage.getItem("watchList")) || [];

function watchListReducer(state = { watchList }, action) {
    switch (action.type) {
        case 'ADD_TO_WATCHLIST': {
            const newWatchList = [...state.watchList, action.payload.id];
            localStorage.setItem("watchList", JSON.stringify(newWatchList));
            return { ...state, watchList: newWatchList };
        }
        case 'REMOVE_FROM_WATCHLIST': {
            const filteredMovies = state.watchList.filter(id => id !== action.payload.id);
            localStorage.setItem("watchList", JSON.stringify(filteredMovies));
            return { ...state, watchList: filteredMovies };
        }
        default:
            return state;
    }
}



const store = createStore(watchListReducer);

export default store;