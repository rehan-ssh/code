import './App.css';
import { BrowserRouter, Route, Switch } from "react-router-dom"
import DetailsPage from './Pages/DetailsPage';
import HomePage from './Pages/HomePage';
import NotFoundPage from './Pages/NotFoundPage';
import { useState } from 'react';
import { MovieContext } from './Context/MovieContext';
import WatchListPage from './Pages/WatchListPage';
import NavBar from './NavBar';

function App() {
  const [watchList, setWatchList] = useState(localStorage.getItem("watchList") ? JSON.parse(localStorage.getItem("watchList")) : []);
  console.log("watchList", watchList)
  function addToWatchList(movie) {
    setWatchList(prevList => [...prevList, movie.id]);
    localStorage.setItem("watchList", JSON.stringify([...watchList, movie.id]));
  }

  function removeFromWatchList(movie) {
    setWatchList(prevList => prevList.filter(m => m !== movie.id));
    localStorage.setItem("watchList", JSON.stringify(watchList.filter(m => m !== movie.id)));
  }

  return (
    <BrowserRouter>
      <MovieContext.Provider value={{ watchList, addToWatchList, removeFromWatchList }}>
        <NavBar />
        <Switch>
          <Route path="/" exact={true} component={HomePage} />
          {/* render gives you ability to pass props, we made prop drilling better with context*/}
          {/* <Route path="/" exact={true} render={() => <HomePage watchList={watchList} addToWatchList={addToWatchList} removeFromWatchList={removeFromWatchList} />} /> */}
          <Route path="/details/:id" exact={true} render={() => <DetailsPage />} />
          <Route path="/watchlist" exact={true} render={() => <WatchListPage />} />
          <Route component={NotFoundPage} />
        </Switch>
      </MovieContext.Provider>
    </BrowserRouter>
  );
}

export default App;
