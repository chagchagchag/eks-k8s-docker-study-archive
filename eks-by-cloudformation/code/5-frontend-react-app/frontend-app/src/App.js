import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Intro from './intro/Intro';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Intro/>}/>
      </Routes>
    </Router>
  );
}

export default App;
