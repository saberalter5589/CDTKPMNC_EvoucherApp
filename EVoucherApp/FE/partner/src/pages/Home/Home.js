import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { ADMIN, PARTNER, CUSTOMER } from "../../commons/constant";
const Home = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  useEffect(() => {
    if (userInfo == null || userInfo?.userId == undefined) {
      navigate("/login");
    }
  }, []);
  const renderHomeContent = () => {
    switch (userInfo?.userTypeId) {
      case ADMIN:
        return (
          <>
            <h1>Welcome to Administrator Home Page</h1>
          </>
        );
      case PARTNER:
        return (
          <>
            <h1>Welcome to Partner Home Page</h1>
          </>
        );
      case CUSTOMER:
        return (
          <>
            <h1>Welcome to Customer Home Page</h1>
          </>
        );
    }
  };

  return <div>{renderHomeContent()}</div>;
};

export default Home;
