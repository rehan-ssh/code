import { useState, useEffect } from "react";


function Counter() {
    const [count, setCount] = useState(0);

    useEffect(() => {
        setCount(count + 1);
        console.log(count);
    }, []);





    return (
        <div>
            <h1>Counter</h1>
            <p>{count}</p>
            <button onClick={() => setCount(count + 1)}>Increment</button>
        </div>
    )
}

export default Counter;