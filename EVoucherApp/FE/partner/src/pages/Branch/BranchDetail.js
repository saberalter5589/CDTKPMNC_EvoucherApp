import { Link, useNavigate, useParams } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const BranchDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [branchId, setBranchId] = useState(null);
  const [branchName, setBranchName] = useState(null);
  const [address, setAddress] = useState(null);
  const [phone, setPhone] = useState(null);

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
    if (id != -1) {
      const params = {
        branchId: id,
        partnerId: userInfo?.userId,
      };
      loadBranch(params);
    }
  }, []);

  const loadBranch = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    try {
      const response = await axios.get(
        "http://localhost:8080/api/branch/search",
        config
      );
      const branch = response?.data?.branchList[0];
      setBranchId(branch?.branchId);
      setBranchName(branch?.branchName);
      setAddress(branch?.address);
      setPhone(branch?.phone);
    } catch (error) {
      toast.error("error");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        branchName: branchName,
        address: address,
        phone: phone,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/branch/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/branch");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/branch/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/branch");
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
              <h1>{id == -1 ? "Create Branch" : "Edit Branch"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Branch Id</label>
                    <input
                      value={branchId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Branch name</label>
                    <input
                      value={branchName}
                      onChange={(e) => setBranchName(e.target.value)}
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
                    <label>Phone</label>
                    <input
                      value={phone}
                      onChange={(e) => setPhone(e.target.value)}
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
              <Link className="btn btn-danger" to={"/branch"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default BranchDetail;
