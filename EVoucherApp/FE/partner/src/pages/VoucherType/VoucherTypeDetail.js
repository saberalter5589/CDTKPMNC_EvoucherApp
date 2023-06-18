import { Link, useNavigate, useParams } from "react-router-dom";
import { ADMIN, PARTNER } from "../../commons/constant";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";

const VoucherTypeDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [voucherTypeId, setVoucherTypeId] = useState();
  const [voucherTypeCode, setVoucherTypeCode] = useState("");
  const [voucherTypeName, setVoucherTypeName] = useState("");

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != ADMIN) {
      navigate("/login");
    }
    if (id != -1) {
      loadVoucherType(id);
    }
  }, []);

  const loadVoucherType = async (id) => {
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
    const voucherType = response?.data?.voucherTypeDtoList?.filter(
      (p) => p?.id == id
    )[0];
    setVoucherTypeId(voucherType?.id);
    setVoucherTypeCode(voucherType?.code);
    setVoucherTypeName(voucherType?.name);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        voucherTypeCode: voucherTypeCode,
        voucherTypeName: voucherTypeName,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/voucher-type/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/voucher-type");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/voucher-type/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/voucher-type");
        }
      }
    } catch (error) {
      toast.error("Error");
    }
  };

  return (
    <>
      <div className="col-lg-12">
        <form onSubmit={handleSubmit}>
          <div className="card">
            <div className="card-header">
              <h1>{id == -1 ? "Create Voucher Type" : "Edit Voucher Type"}</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Type Id</label>
                    <input
                      value={voucherTypeId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Type Code</label>
                    <input
                      value={voucherTypeCode}
                      onChange={(e) => setVoucherTypeCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Type Name</label>
                    <input
                      value={voucherTypeName}
                      onChange={(e) => setVoucherTypeName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                {id == -1 ? "Create" : "Edit"}
              </button>
              <Link className="btn btn-danger" to={"/voucher-type"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};

export default VoucherTypeDetail;
