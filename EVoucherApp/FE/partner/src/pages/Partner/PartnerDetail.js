import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { ADMIN, CUSTOMER, PARTNER } from "../../commons/constant";
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

const PartnerDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const { id } = useParams();
  const navigate = useNavigate();
  const [username, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [partnerTypeId, setPartnerTypeId] = useState("");
  const [partnerName, setPartnerName] = useState("");
  const [note, setNote] = useState("");
  const [partnerTypeList, setPartnerTypeList] = useState([]);
  const [password, setPassword] = useState();

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    getPartnerTypeList();
    if (id != -1) {
      loadPartner();
    }
  }, []);

  const getPartnerTypeList = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/partner-type/search"
    );
    setPartnerTypeList(response?.data?.partnerTypeDtoList);
    setPartnerTypeId(response?.data?.partnerTypeDtoList[0]?.partnerTypeId);
  };

  const loadPartner = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: {
        partnerId: id,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/partner/search",
      config
    );
    const info = response?.data?.partnerList[0];
    setUserName(info?.userName);
    setEmail(info?.email);
    setPhone(info?.phone);
    setAddress(info?.address);
    setPartnerName(info?.partnerName);
    setPartnerTypeId(info?.partnerTypeId);
    setNote(info?.partnerNote);
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
        userName: username,
        email: email,
        phone: phone,
        address: address,
        partnerName: partnerName,
        partnerTypeId: partnerTypeId,
        note: note,
        password: password,
      };

      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/partner/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigateAfterUpdate();
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/partner/create`,
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
      return "/partner";
    } else {
      //return "/campaign";
    }
  };

  const navigateAfterUpdate = () => {
    if (userInfo?.userTypeId == ADMIN) {
      navigate("/partner");
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
              <h1>{id == -1 ? "Create Partner" : "Edit Partner"}</h1>
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
              <div className="col-lg-12">
                <div className="form-group">
                  <label>Partner Type</label>
                  <select
                    value={partnerTypeId}
                    onChange={(e) => setPartnerTypeId(e.target.value)}
                    className="form-control"
                  >
                    {partnerTypeList?.map((pt) => (
                      <option value={pt?.partnerTypeId}>
                        {`${pt?.partnerTypeCode} - ${pt?.partnerTypeName}`}
                      </option>
                    ))}
                  </select>
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
                      value={partnerName}
                      onChange={(e) => setPartnerName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Note</label>
                    <textarea
                      value={note}
                      onChange={(e) => setNote(e.target.value)}
                      className="form-control"
                    ></textarea>
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

export default PartnerDetail;
