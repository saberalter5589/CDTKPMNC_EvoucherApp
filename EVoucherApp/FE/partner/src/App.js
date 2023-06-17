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
import VoucherTemplate from "./pages/VoucherTemplate/VoucherTemplate";
import VoucherTemplateDetail from "./pages/VoucherTemplate/VoucherTemplateDetail";
import { getUserInfoFromLocalStorage } from "./commons/utils";
import CampaignView from "./pages/Campaign/CampaignView";
import Game from "./pages/Game/Game";
import Voucher from "./pages/Voucher/Voucher";
import VoucherDetail from "./pages/Voucher/VoucherDetail";
import RegisterCustomer from "./pages/Register/RegisterCustomer";
import RegisterPartner from "./pages/Register/RegisterPartner";
import CustomerDetail from "./pages/Customer/CustomerDetail";
import PartnerDetail from "./pages/Partner/PartnerDetail";
import PartnerType from "./pages/PartnerType/PartnerType";
import VoucherType from "./pages/VoucherType/VoucherType";
import VoucherTypeDetail from "./pages/VoucherType/VoucherTypeDetail";
import PartnerTypeDetail from "./pages/PartnerType/PartnerTypeDetail";
import Partner from "./pages/Partner/Partner";
import Customer from "./pages/Customer/Customer";
import { ADMIN, PARTNER, CUSTOMER } from "../src/commons/constant";
import CampaignStatistic from "./pages/Campaign/CampaignStatistic";

function App() {
  const [isLogin, setIsLogin] = useState(true);
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());

  return (
    <div className="App">
      <ToastContainer theme="colored"></ToastContainer>
      <BrowserRouter>
        <Header isLogin={isLogin} userInfo={userInfo} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/register-customer" element={<RegisterCustomer />} />
          <Route path="/register-partner" element={<RegisterPartner />} />
          <Route
            path="/login"
            element={
              <Login
                setIsLogin={setIsLogin}
                setUserInfo={setUserInfo}
                userTypeId={CUSTOMER}
              />
            }
          />
          <Route
            path="partner/login"
            element={
              <Login
                setIsLogin={setIsLogin}
                setUserInfo={setUserInfo}
                userTypeId={PARTNER}
              />
            }
          />
          <Route
            path="admin/login"
            element={
              <Login
                setIsLogin={setIsLogin}
                setUserInfo={setUserInfo}
                userTypeId={ADMIN}
              />
            }
          />
          <Route path="/branch" element={<Branch />} />
          <Route exact path="/branch-detail/:id" element={<BranchDetail />} />
          <Route path="/campaign" element={<Campaign />} />
          <Route path="/campaign-detail/:id" element={<CampaignDetail />} />
          <Route path="/campaign-view/:id" element={<CampaignView />} />
          <Route path="/campaign-statistic" element={<CampaignStatistic />} />
          <Route path="/voucher-template" element={<VoucherTemplate />} />
          <Route
            path="/voucher-template/:id"
            element={<VoucherTemplateDetail />}
          />
          <Route path="/game" element={<Game />} />
          <Route path="/voucher" element={<Voucher />} />
          <Route path="/voucher-detail/:id" element={<VoucherDetail />} />
          <Route path="/customer" element={<Customer />} />
          <Route path="/customer-detail/:id" element={<CustomerDetail />} />
          <Route path="/partner" element={<Partner />} />
          <Route path="/partner-detail/:id" element={<PartnerDetail />} />
          <Route path="/partner-type" element={<PartnerType />} />
          <Route
            path="/partner-type-detail/:id"
            element={<PartnerTypeDetail />}
          />
          <Route path="/voucher-type" element={<VoucherType />} />
          <Route
            path="/voucher-type-detail/:id"
            element={<VoucherTypeDetail />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
