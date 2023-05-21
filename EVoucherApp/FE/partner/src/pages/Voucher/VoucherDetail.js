import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { CUSTOMER, PARTNER } from "../../commons/constant";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import { CAMPAIGN_STATUS } from "../../commons/constant";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { convertFromDateToString } from "../../commons/utils";

const VoucherDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [voucher, setVoucher] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != CUSTOMER) {
      navigate("/login");
    }
    loadVoucherDetail();
  }, []);

  const loadVoucherDetail = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: { voucherId: id },
    };
    const response = await axios.get(
      "http://localhost:8080/api/voucher/search",
      config
    );
    setVoucher(response?.data?.voucherDtoList[0]);
  };

  const useVoucher = async () => {
    const payload = {
      authentication: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const res = await axios.post(
      `http://localhost:8080/api/voucher/use-voucher/${id}`,
      payload
    );
    navigate("/voucher");
  };

  return (
    <>
      <div className="col-lg-12">
        <div className="card">
          <div className="card-header">
            <h1>Voucher Info</h1>
          </div>
          <div className="card-body">
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Campaign Id</label>
                  <input
                    value={voucher?.voucherId}
                    disabled
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Voucher code</label>
                  <input
                    disabled
                    value={voucher?.voucherCode}
                    className="form-control"
                  ></input>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Voucher name</label>
                  <input
                    disabled
                    value={voucher?.voucherName}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div className="col-lg-6">
                <div className="form-group">
                  <label>Applied branches</label>
                  <input
                    disabled
                    value={voucher?.branchList
                      ?.map((br) => br?.branchName)
                      ?.join([" , "])}
                    className="form-control"
                  ></input>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-lg-12">
                <div className="form-group">
                  <label>Description</label>
                  <textarea
                    disabled
                    value={voucher?.description}
                    className="form-control"
                  ></textarea>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-lg-12">
                <div className="form-group">
                  <label>Is used</label>
                  <input
                    disabled
                    value={voucher?.isUsed ? "Used" : "Not used"}
                    className="form-control"
                  ></input>
                </div>
              </div>
            </div>
            {!voucher?.isUsed && (
              <button onClick={useVoucher} className="btn btn-primary">
                Use Voucher
              </button>
            )}

            <Link className="btn btn-danger " to={"/voucher"}>
              Back
            </Link>
          </div>
        </div>
      </div>
    </>
  );
};

export default VoucherDetail;
