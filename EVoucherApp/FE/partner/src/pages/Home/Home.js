import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
const Home = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
  }, []);

  return <div>Home</div>;
};

export default Home;
