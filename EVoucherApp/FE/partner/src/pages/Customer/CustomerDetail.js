import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { CUSTOMER, ADMIN } from "../../commons/constant";
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
  const [password, setPassword] = useState("");

  useEffect(() => {
    console.log(userInfo);
    if (userInfo == null) {
      navigate("/login");
    }
    if (id != -1) {
      loadCustomer();
    }
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
    setPassword(info?.password);
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
        password: password,
        birthday: convertFromDateToString(birthday),
      };

      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/customer/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigateAfterUpdate();
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/customer/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigateAfterUpdate();
        }
      }
    } catch (error) {
      toast.error("Error");
    }
  };

  const renderBackButtonDestination = () => {
    if (userInfo?.userTypeId == ADMIN) {
      return "/customer";
    } else {
      //return "/campaign";
    }
  };

  const navigateAfterUpdate = () => {
    if (userInfo?.userTypeId == ADMIN) {
      navigate("/customer");
    } else {
      //navigate("/campaign");
    }
  };

  return (
    <>
      <div className="col-lg-12">
        <form onSubmit={handleSubmit}>
          <div className="card">
            <div className="card-header">
              <h1>{id == -1 ? "Create Customer" : "Edit Customer"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Username</label>
                    <input
                      value={username}
                      onChange={(e) => setUserName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Password</label>
                    <input
                      type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
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
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Phone</label>
                    <input
                      value={phone}
                      onChange={(e) => setEmail(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
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
                {id == -1 ? "Create" : "Edit"}
              </button>
              {userInfo?.userTypeId == ADMIN && (
                <Link
                  className="btn btn-danger "
                  to={renderBackButtonDestination()}
                >
                  Back
                </Link>
              )}
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default CustomerDetail;
