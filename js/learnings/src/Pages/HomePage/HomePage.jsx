import Banner from "../../Banner";
import Movies from "../../Movies";

function HomePage({ watchList, addToWatchList, removeFromWatchList }) {
    return (
        <div>
            <h1>Home Page</h1>
            <Banner />
            <Movies watchList={watchList} addToWatchList={addToWatchList} removeFromWatchList={removeFromWatchList} />
        </div>
    );
}

export default HomePage;