import { createContext } from 'react';
export const MovieContext = createContext({
    watchList: [],
    addToWatchList: () => { },
    removeFromWatchList: () => { }
});