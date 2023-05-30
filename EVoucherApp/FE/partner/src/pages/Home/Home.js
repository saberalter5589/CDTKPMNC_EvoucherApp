import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
const Home = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  useEffect(() => {
    if (userInfo == null || userInfo?.userId == undefined) {
      navigate("/login");
    }
  }, []);

  return <div>Home</div>;
};

export default Home;
