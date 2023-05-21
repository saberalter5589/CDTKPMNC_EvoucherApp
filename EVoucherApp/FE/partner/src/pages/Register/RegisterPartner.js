import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { CUSTOMER, PARTNER } from "../../commons/constant";
import { convertFromDateToString } from "../../commons/utils";

const RegisterPartner = (props) => {
  const navigate = useNavigate();
  const [username, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [partnerTypeId, setPartnerTypeId] = useState("");
  const [partnerName, setPartnerName] = useState("");
  const [note, setNote] = useState("");
  const [partnerTypeList, setPartnerTypeList] = useState([]);

  useEffect(() => {
    getPartnerTypeList();
  }, []);

  const getPartnerTypeList = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/partner-type/search"
    );
    setPartnerTypeList(response?.data?.partnerTypeDtoList);
    setPartnerTypeId(response?.data?.partnerTypeDtoList[0]?.partnerTypeId);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      userTypeId: PARTNER,
      userName: username,
      password: password,
      email: email,
      phone: phone,
      address: address,
      partnerName: partnerName,
      partnerTypeId: partnerTypeId,
      note: note,
    };
    const res = await axios.post(
      `http://localhost:8080/api/partner/create`,
      payload
    );
    if (res.status == 200) {
      toast.success("Success");
      navigate("/login");
    }
  };
  return (
    <>
      <div className="col-lg-12">
        <form onSubmit={handleSubmit}>
          <div className="card">
            <div className="card-header">
              <h1>Register for new Partner</h1>
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
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="col-lg-6">
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
                Create
              </button>
              <Link className="btn btn-danger " to={"/campaign"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default RegisterPartner;
