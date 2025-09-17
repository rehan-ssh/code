import "./Card.css";


function Card({ member }) {
    return (
        <div className="card">
            <div className="card-image">
                <img src={`https://image.tmdb.org/t/p/original/${member.profile_path}`} alt={member.name} />
            </div>
            <div className="card-header">{member.name}</div>
            <div className="card-character">{member.character}</div>
            {/* Additional member details can be displayed here */}
        </div>
    );
}

export default Card;