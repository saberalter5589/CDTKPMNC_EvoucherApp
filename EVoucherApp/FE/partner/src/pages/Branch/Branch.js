import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Branch = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [branches, setBranches] = useState([]);
  const [branchId, setBranchId] = useState(null);
  const [branchName, setBranchName] = useState(null);
  const [address, setAddress] = useState(null);
  const [phone, setPhone] = useState(null);

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
    const params = {
      partnerId: userInfo?.userId,
    };
    loadBranch(params);
  }, []);

  const loadBranch = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/branch/search",
      config
    );
    setBranches(response?.data?.branchList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      branchId: branchId,
      branchName: branchName,
      address: address,
      phone: phone,
      partnerId: userInfo?.userId,
    };
    loadBranch(params);
  };

  const deleteBranch = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/branch/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadBranch();
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
              <h1>Search branches</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Branch Id</label>
                    <input
                      value={branchId}
                      onChange={(e) => setBranchId(e.target.value)}
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
                Search
              </button>
              <Link className="btn btn-success" to={`/branch-detail/${-1}`}>
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Branch List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Branch Id</th>
              <th scope="col">Branch name</th>
              <th scope="col">Address</th>
              <th scope="col">Phone</th>
              <th scope="col">Partner</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {branches?.map((branch, index) => (
              <>
                <tr>
                  <th scope="row" key={branch?.branchId}>
                    {branch?.branchId}
                  </th>
                  <td>{branch?.branchName}</td>
                  <td>{branch?.address}</td>
                  <td>{branch?.phone}</td>
                  <td>{branch?.partnerName}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/branch-detail/${branch?.branchId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deleteBranch(e, branch?.branchId)}
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

export default Branch;
