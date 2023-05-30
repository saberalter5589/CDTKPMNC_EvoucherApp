import { Link, useNavigate, useParams } from "react-router-dom";
import { ADMIN, PARTNER } from "../../commons/constant";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const PartnerTypeDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [partnerTypeId, setPartnerTypeId] = useState();
  const [partnerTypeCode, setPartnerTypeCode] = useState("");
  const [partnerTypeName, setPartnerTypeName] = useState("");

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    if (id != -1) {
      loadPartnerType(id);
    }
  }, []);

  const loadPartnerType = async (id) => {
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
    const partnerType = response?.data?.partnerTypeDtoList?.filter(
      (p) => p?.partnerTypeId == id
    )[0];
    setPartnerTypeId(partnerType?.partnerTypeId);
    setPartnerTypeCode(partnerType?.partnerTypeCode);
    setPartnerTypeName(partnerType?.partnerTypeName);
    console.log(partnerType);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        partnerTypeCode: partnerTypeCode,
        partnerTypeName: partnerTypeName,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/partner-type/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/partner-type");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/partner-type/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/partner-type");
        }
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
              <h1>{id == -1 ? "Create Partner Type" : "Edit Partner Type"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Partner Type Id</label>
                    <input
                      value={partnerTypeId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Partner Type Code</label>
                    <input
                      value={partnerTypeCode}
                      onChange={(e) => setPartnerTypeCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Partner Type Name</label>
                    <input
                      value={partnerTypeName}
                      onChange={(e) => setPartnerTypeName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                {id == -1 ? "Create" : "Edit"}
              </button>
              <Link className="btn btn-danger" to={"/partner-type"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default PartnerTypeDetail;
