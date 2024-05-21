import React, { useEffect, useState } from 'react';
import Home from './pages/Home';

function App() {

    return (
        <div className="App">
            <Home />
        </div>
    );
    // const [hello, setHello] = useState('');
    // useEffect(() => {
    //     axios.get('http://localhost:8080/api/hello')
    //         .then(response => setHello(response.data))
    //         .catch(error => console.log(error));
    // }, []);
    //
    // return (
    //     <div>
    //         백엔드에서 가져온 데이터입니다 : {hello}
    //     </div>
    // );
}

export default App;
