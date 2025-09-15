import "./Pagination.css";


function Pagination({ pageNumber, nextPageFunc, previousPageFunc }) {
    return (
        <div className="pagination">
            <button className="pagination-previous" onClick={previousPageFunc}>Previous</button>
            <div>{pageNumber}</div>
            <button className="pagination-next" onClick={nextPageFunc}>Next</button>
        </div>
    )
}

export default Pagination;