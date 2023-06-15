import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { CUSTOMER, PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const Customer = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [customers, setCustomers] = useState([]);
  const [customerId, setCustomerId] = useState();
  const [customerName, setCustomerName] = useState();
  const [username, setUserName] = useState();
  const [email, setEmail] = useState();
  const [address, setAddress] = useState();

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    loadCustomer();
  }, []);

  const loadCustomer = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/customer/search",
      config
    );
    setCustomers(response?.data?.customerList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      customerId: customerId ? customerId : null,
      customerName: customerName ? customerName : "",
      username: username ? username : "",
      email: email ? email : "",
      address: address ? address : "",
    };
    loadCustomer(params);
  };

  const deleteCustomer = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/customer/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadCustomer();
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
              <h1>Search Customer</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Customer Id</label>
                    <input
                      value={customerId}
                      onChange={(e) => setCustomerId(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Customer Name</label>
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
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Search
              </button>
              <Link className="btn btn-success" to={`/customer-detail/${-1}`}>
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Customer List</h2>
      <div className="py-4 table-responsive" style={{ "max-height": "500px" }}>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Customer name</th>
              <th scope="col">Username</th>
              <th scope="col">Email</th>
              <th scope="col">Address</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {customers?.map((cm, index) => (
              <>
                <tr>
                  <th scope="row" key={cm?.userId}>
                    {cm?.userId}
                  </th>
                  <td>{cm?.customerName}</td>
                  <td>{cm?.userName}</td>
                  <td>{cm?.email}</td>
                  <td>{cm?.address}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/customer-detail/${cm?.userId}`}
                    >
                      Edit
                    </Link>
                    <button
                      className="btn btn-danger mx-2"
                      onClick={(e) => deleteCustomer(e, cm?.userId)}
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

export default Customer;
