import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import { PARTNER, ADMIN, CUSTOMER } from "../../commons/constant";

const Login = (props) => {
  const { setIsLogin, setUserInfo, userTypeId } = props;
  const [username, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.clear();
    setIsLogin(false);
  }, []);

  const ProceedLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8080/api/login", {
        userName: username,
        password: password,
      });
      if (res.status == 200) {
        let user = res?.data?.user;
        if (user != null && user.userTypeId == userTypeId) {
          localStorage.setItem(
            "user",
            JSON.stringify({
              id: user.userId,
              username: username,
              password: password,
              userTypeId: user.userTypeId,
            })
          );
          setIsLogin(true);
          toast.success("Success");
          navigate("/");
          setUserInfo({
            userId: user.userId,
            userName: username,
            password: password,
            userTypeId: user.userTypeId,
          });
        } else {
          toast.error("In correct username or password");
        }
      }
    } catch (error) {
      if (error?.response?.status == 401) {
        toast.error("In correct username or password");
      }
    }
  };

  const renderTitle = () => {
    switch (userTypeId) {
      case ADMIN:
        return "Voucher Application (admin page)";
      case PARTNER:
        return "Voucher Application (partner page)";
      case CUSTOMER:
        return "Voucher Application (customer page)";
    }
  };

  return (
    <div className="row">
      <div className="offset-lg-3 col-lg-6">
        <form onSubmit={ProceedLogin} className="container">
          <div className="row text-center">
            <h1>{renderTitle()}</h1>
          </div>
          <div className="card">
            <div className="card-header">
              <h2>Login</h2>
            </div>
            <div className="card-body">
              <div className="form-group">
                <label>
                  User Name <span className="errmsg">*</span>
                </label>
                <input
                  value={username}
                  onChange={(e) => setUserName(e.target.value)}
                  className="form-control"
                ></input>
              </div>
              <div className="form-group">
                <label>
                  Password <span className="errmsg">*</span>
                </label>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="form-control"
                ></input>
              </div>
            </div>
            <div className="card-footer">
              <div className="row">
                <div className="offset-lg-12 col-lg-12 text-center">
                  <button type="submit" className="btn btn-primary m-1">
                    Login
                  </button>
                  {userTypeId == PARTNER && (
                    <Link
                      className="btn btn-success m-2"
                      to={"/register-partner"}
                      style={{ margin: 20 }}
                    >
                      Register new Partner
                    </Link>
                  )}
                  {userTypeId == CUSTOMER && (
                    <Link
                      className="btn btn-success m-2"
                      to={"/register-customer"}
                    >
                      Register new Customer
                    </Link>
                  )}
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
