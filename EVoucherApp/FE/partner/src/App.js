import logo from "./logo.svg";
import "./App.css";
import { ToastContainer } from "react-toastify";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Login from "./pages/Login/Login";
import Home from "./pages/Home/Home";
import Branch from "./pages/Branch/Branch";
import BranchDetail from "./pages/Branch/BranchDetail";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Header from "./components/Header/Header";
import { useEffect, useState } from "react";
import Campaign from "./pages/Campaign/Campaign";
import CampaignDetail from "./pages/Campaign/CampaignDetail";

function App() {
  const [isLogin, setIsLogin] = useState(true);
  return (
    <div className="App">
      <ToastContainer theme="colored"></ToastContainer>
      <BrowserRouter>
        <Header isLogin={isLogin} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login setIsLogin={setIsLogin} />} />
          <Route path="/branch" element={<Branch />} />
          <Route exact path="/branch-detail/:id" element={<BranchDetail />} />
          <Route path="/campaign" element={<Campaign />} />
          <Route path="/campaign-detail/:id" element={<CampaignDetail />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
