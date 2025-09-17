import Card from "../Card";
import "./Cast.css";

function Cast({ cast }) {
    return (
        <div className="cast">
            {cast.map(member => (
                <>
                    <Card key={member.id} member={member} />
                </>
            ))}
        </div>
    );
}

export default Cast;