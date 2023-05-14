import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import { PARTNER } from "../../commons/constant";

const Login = (props) => {
  const { setIsLogin } = props;
  const [username, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.clear();
    //setUserInfo(null);
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
        localStorage.setItem(
          "user",
          JSON.stringify({
            id: user.userId,
            username: username,
            password: password,
            userTypeId: user.userTypeId,
          })
        );
        if (user.userTypeId != PARTNER) {
          toast.error("You are not partner");
          return;
        }
        setIsLogin(true);
        toast.success("Success");
        navigate("/");
      }
    } catch (error) {
      if (error?.response?.status == 401) {
        toast.error("In correct username or password");
      }
    }
  };

  const validate = () => {
    let result = true;
    if (username === "" || username === null) {
      result = false;
      toast.warning("Please enter username");
    }
    if (password === "" || password === null) {
      result = true;
      toast.warning("Please enter password");
    }
    return result;
  };

  return (
    <div className="row">
      <div className="offset-lg-3 col-lg-6">
        <form onSubmit={ProceedLogin} className="container">
          <div className="card">
            <div className="card-header">
              <h2>Partner Login</h2>
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
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="form-control"
                ></input>
              </div>
            </div>
            <div className="card-footer">
              <button type="submit" className="btn btn-primary">
                Login
              </button>
              <Link className="btn btn-success" to={"/register"}>
                Create new partner
              </Link>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
