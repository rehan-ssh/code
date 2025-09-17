import './App.css';
import Counter from "./Counter"
import { BrowserRouter, Route, Switch } from "react-router-dom"
import DetailsPage from './Pages/DetailsPage';
import HomePage from './Pages/HomePage';
import NotFoundPage from './Pages/NotFoundPage';
import { useState } from 'react';

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
      <Switch>
        <Route path="/" exact={true} render={() => <HomePage watchList={watchList} addToWatchList={addToWatchList} removeFromWatchList={removeFromWatchList} />} />
        <Route path="/details/:id" exact={true} render={() => <DetailsPage />} />
        <Route component={NotFoundPage} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
