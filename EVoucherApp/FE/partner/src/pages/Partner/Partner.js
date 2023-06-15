import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { CUSTOMER, PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Partner = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [partners, setPartners] = useState([]);
  const [partnerId, setPartnerId] = useState();
  const [partnerName, setPartnerName] = useState();
  const [username, setUserName] = useState();
  const [email, setEmail] = useState();
  const [address, setAddress] = useState();
  const [partnerTypeId, setPartnerTypeId] = useState();
  const [partnerTypeList, setPartnerTypeList] = useState();

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    loadPartnerType();
    loadAllPartner();
  }, []);

  const loadPartnerType = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/partner-type/search",
      config
    );
    const partnerTypeList = [
      {
        partnerTypeId: -1,
        partnerTypeCode: "All",
        partnerTypeName: "All",
      },
      ...response?.data?.partnerTypeDtoList,
    ];

    setPartnerTypeList(partnerTypeList);
  };

  const loadAllPartner = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/partner/search",
      config
    );
    setPartners(response?.data?.partnerList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      partnerId: partnerId ? partnerId : null,
      partnerName: partnerName ? partnerName : "",
      username: username ? username : "",
      email: email ? email : "",
      address: address ? address : "",
      partnerTypeId:
        partnerTypeId && partnerTypeId != -1 ? partnerTypeId : null,
    };
    loadAllPartner(params);
  };

  const deletePartner = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/partner/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadAllPartner();
      }
    } catch (error) {
      toast.error("Error");
    }
  };

  return (
    <div className="col-lg-12">
      <div>
        <form onSubmit={handleSearch}>
          <div className="card">
            <div className="card-header">
              <h1>Search Partner</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Partner Id</label>
                    <input
                      value={partnerId}
                      onChange={(e) => setPartnerId(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Partner Name</label>
                    <input
                      value={partnerName}
                      onChange={(e) => setPartnerName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
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
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Search
              </button>
              <Link className="btn btn-success" to={`/partner-detail/${-1}`}>
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Partner List</h2>
      <div className="py-4 table-responsive" style={{ "max-height": "500px" }}>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Partner Type</th>
              <th scope="col">Partner name</th>
              <th scope="col">Username</th>
              <th scope="col">Email</th>
              <th scope="col">Address</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {partners?.map((pn, index) => (
              <>
                <tr>
                  <th scope="row" key={pn?.userId}>
                    {pn?.userId}
                  </th>
                  <td>{pn?.partnerTypeName}</td>
                  <td>{pn?.partnerName}</td>
                  <td>{pn?.userName}</td>
                  <td>{pn?.email}</td>
                  <td>{pn?.address}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/partner-detail/${pn?.userId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deletePartner(e, pn?.userId)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              </>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Partner;
