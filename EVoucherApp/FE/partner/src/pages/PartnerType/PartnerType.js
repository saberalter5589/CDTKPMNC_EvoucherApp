import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN, PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const PartnerType = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [partnerTypeList, setPartnerTypeList] = useState([]);

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    loadPartnerType();
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
    setPartnerTypeList(response?.data?.partnerTypeDtoList);
  };

  return (
    <div className="col-lg-12">
      <div>
        <form>
          <div className="card">
            <div className="card-header">
              <h1>Partner Type setting</h1>
            </div>

            <div className="card-footer text-center">
              <Link
                className="btn btn-success"
                to={`/partner-type-detail/${-1}`}
              >
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Partner Type List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Code</th>
              <th scope="col">Name</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {partnerTypeList?.map((pt, index) => (
              <>
                <tr>
                  <th scope="row" key={pt?.partnerTypeId}>
                    {pt?.partnerTypeId}
                  </th>
                  <td>{pt?.partnerTypeCode}</td>
                  <td>{pt?.partnerTypeName}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/partner-type-detail/${pt?.partnerTypeId}`}
                    >
                      Edit
                    </Link>
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

export default PartnerType;
