import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ADMIN, PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const VoucherType = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [voucherTypeList, setVoucherTypeList] = useState([]);

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    loadVoucherType();
  }, []);

  const loadVoucherType = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/voucher-type/search",
      config
    );
    setVoucherTypeList(response?.data?.voucherTypeDtoList);
  };

  return (
    <div className="col-lg-12">
      <div>
        <form>
          <div className="card">
            <div className="card-header">
              <h1>Voucher Type setting</h1>
            </div>

            <div className="card-footer text-center">
              <Link
                className="btn btn-success"
                to={`/voucher-type-detail/${-1}`}
              >
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Voucher Type List</h2>
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
            {voucherTypeList?.map((vt, index) => (
              <>
                <tr>
                  <th scope="row" key={vt?.id}>
                    {vt?.id}
                  </th>
                  <td>{vt?.code}</td>
                  <td>{vt?.name}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/voucher-type-detail/${vt?.id}`}
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

export default VoucherType;
