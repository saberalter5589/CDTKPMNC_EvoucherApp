import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { CAMPAIGN_STATUS } from "../../commons/constant";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { convertFromDateToString } from "../../commons/utils";

const Voucher = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [voucherId, setVoucherId] = useState();
  const [voucherCode, setVoucherCode] = useState();
  const [voucherName, setVoucherName] = useState();
  const [isUsed, setIsUsed] = useState(false);
  const [voucherList, setVoucherList] = useState([]);
  const [partnerList, setPartnerList] = useState();
  const [partnerId, setPartnerId] = useState();

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    loadPartner();
    loadVouchers();
  }, []);

  const loadPartner = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/partner/search",
      config
    );
    const partnerList = [
      {
        userId: -1,
        partnerName: "All",
      },
      ...response?.data?.partnerList,
    ];
    setPartnerList(partnerList);
  };

  const loadVouchers = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/voucher/search",
      config
    );
    setVoucherList(response?.data?.voucherDtoList);
  };

  const onChangeIsUsed = (e) => {
    setIsUsed(e.target.checked);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      voucherId: voucherId ? voucherId : null,
      voucherCode: voucherCode ? voucherCode : "",
      voucherName: voucherName ? voucherName : "",
      partnerId: partnerId && partnerId != -1 ? partnerId : "",
      isUsed: isUsed ? !isUsed : "",
    };
    loadVouchers(params);
  };

  return (
    <div className="col-lg-12">
      <div>
        <form onSubmit={handleSearch}>
          <div className="card">
            <div className="card-header">
              <h1>Search Voucher</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Id</label>
                    <input
                      value={voucherId}
                      onChange={(e) => setVoucherId(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher code</label>
                    <input
                      value={voucherCode}
                      onChange={(e) => setVoucherCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Name</label>
                    <input
                      value={voucherName}
                      onChange={(e) => setVoucherName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Partner</label>
                    <select
                      value={partnerId}
                      onChange={(e) => setPartnerId(e.target.value)}
                      className="form-control"
                    >
                      {partnerList?.map((partner) => (
                        <option value={partner?.userId}>
                          {`${partner?.partnerName}`}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div class="form-check">
                    <label class="form-check-label" for="flexCheckDefault">
                      Is not used
                    </label>
                    <input
                      class="form-check-input"
                      type="checkbox"
                      checked={isUsed}
                      onChange={(e) => onChangeIsUsed(e)}
                    ></input>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Search
              </button>
            </div>
          </div>
        </form>
      </div>
      <h2>Voucher List</h2>
      <div className="py-4 table-responsive" style={{ "max-height": "500px" }}>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Voucher Id</th>
              <th scope="col">Voucher Code</th>
              <th scope="col">Voucher name</th>
              <th scope="col">Partner</th>
              <th scope="col">Status</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {voucherList?.map((voucher, index) => (
              <>
                <tr>
                  <th scope="row" key={voucher?.voucherId}>
                    {voucher?.voucherId}
                  </th>
                  <td>{voucher?.voucherCode}</td>
                  <td>{voucher?.voucherName}</td>
                  <td>{voucher?.partner?.partnerName}</td>
                  <td>{voucher?.isUsed ? "Is Used" : "Not Used"}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/voucher-detail/${voucher?.voucherId}`}
                    >
                      View detail
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

export default Voucher;
