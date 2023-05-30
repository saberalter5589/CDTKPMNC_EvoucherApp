import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { CUSTOMER, PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { CAMPAIGN_STATUS } from "../../commons/constant";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {
  convertFromDateToString,
  convertFromStringToDate,
} from "../../commons/utils";

const CustomerDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const { id } = useParams();
  const navigate = useNavigate();
  const [username, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [customerName, setCustomerName] = useState("");
  const [birthday, setBirthDay] = useState("");

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    loadCustomer();
  }, []);

  const loadCustomer = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: {
        customerId: id,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/customer/search",
      config
    );
    const info = response?.data?.customerList[0];
    setUserName(info?.userName);
    setEmail(info?.email);
    setPhone(info?.phone);
    setAddress(info?.address);
    setCustomerName(info?.customerName);
    setBirthDay(convertFromStringToDate(info?.birthday));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        userTypeId: CUSTOMER,
        userName: username,
        email: email,
        phone: phone,
        address: address,
        customerName: customerName,
        birthday: convertFromDateToString(birthday),
      };
      const res = await axios.put(
        `http://localhost:8080/api/customer/${id}`,
        payload
      );
      if (res.status == 200) {
        toast.success("Success");
      }
    } catch (error) {
      toast.error("Error");
    }
  };

  return (
    <>
      <div className="col-lg-12">
        <form onSubmit={handleSubmit}>
          <div className="card">
            <div className="card-header">
              <h1>Update customer info</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Username</label>
                    <input
                      value={username}
                      onChange={(e) => setUserName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Email</label>
                    <input
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Phone</label>
                    <input
                      value={phone}
                      onChange={(e) => setPhone(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Address</label>
                    <input
                      value={address}
                      onChange={(e) => setAddress(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Name</label>
                    <input
                      value={customerName}
                      onChange={(e) => setCustomerName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Birthday</label>
                    <DatePicker
                      selected={birthday}
                      onChange={(date) => setBirthDay(date)}
                      dateFormat="yyyy-MM-dd"
                      className="form-control"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Update
              </button>
              <Link className="btn btn-danger " to={"/home"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default CustomerDetail;
