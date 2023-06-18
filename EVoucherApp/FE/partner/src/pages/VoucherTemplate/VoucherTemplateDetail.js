import { Link, useNavigate, useParams } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  getUserInfoFromLocalStorage,
  convertFromDateToString,
} from "../../commons/utils";
import { CAMPAIGN_STATUS } from "../../commons/constant";
import { convertFromStringToDate } from "../../commons/utils";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Select from "react-select";

const VoucherTemplateDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [voucherTemplateId, setVoucherTemplateId] = useState(null);
  const [voucherTemplateCode, setVoucherTemplateCode] = useState();
  const [voucherTemplateName, setVoucherTemplateName] = useState();
  const [campaignId, setCampaignId] = useState();
  const [voucherTypeId, setVoucherTypeId] = useState();
  const [dateStart, setDateStart] = useState();
  const [dateEnd, setDateEnd] = useState();
  const [amount, setAmount] = useState(0);
  const [note, setNote] = useState("");
  const [description, setDescription] = useState("");
  const [selectedBranchList, setSelectedBranchList] = useState([]);
  const [voucherList, setVoucherList] = useState([]);
  const [voucherTypeList, setVoucherTypeList] = useState([]);
  const [campaignList, setCampaignList] = useState([]);
  const [branchList, setBranchList] = useState([]);

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
    loadVoucherType();
    loadCampaignList();
    loadBranchList();
    if (id != -1) {
      const params = {
        voucherTemplateId: id,
      };
      loadVoucherTemplateList(params);
    }
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
    setVoucherTypeId(response?.data?.voucherTypeDtoList[0]?.id);
  };

  const loadCampaignList = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/campaign/search",
      config
    );
    setCampaignList(response?.data?.campaignDtoList);
    setCampaignId(response?.data?.campaignDtoList[0]?.campainId);
  };

  const loadBranchList = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      partnerId: userInfo?.userId,
    };
    const response = await axios.get(
      "http://localhost:8080/api/branch/search",
      config
    );

    setBranchList(
      response?.data?.branchList?.map((br) => ({
        value: br?.branchId,
        label: br?.branchName,
      }))
    );
  };

  const handleSelectBranch = (data) => {
    setSelectedBranchList(data);
  };

  const loadVoucherTemplateList = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/voucher-template/search",
      config
    );
    const vtDetail = response?.data?.voucherTemplateDtoList[0];
    setVoucherTemplateId(vtDetail?.voucherTemplateId);
    setVoucherTemplateCode(vtDetail?.voucherTemplateCode);
    setVoucherTemplateName(vtDetail?.voucherTemplateName);
    setCampaignId(vtDetail?.campaign?.campainId);
    setVoucherTypeId(vtDetail?.voucherType?.id);
    const selectedBranchList = vtDetail?.branchList?.map((br) => ({
      value: br?.branchId,
      label: br?.branchName,
    }));
    setSelectedBranchList(selectedBranchList);
    setDateStart(
      vtDetail?.dateStart ? convertFromStringToDate(vtDetail?.dateStart) : null
    );
    setDateEnd(
      vtDetail?.dateEnd ? convertFromStringToDate(vtDetail?.dateEnd) : null
    );
    setDescription(vtDetail?.description);
    setNote(vtDetail?.note);
    setAmount(vtDetail?.amount);
    setVoucherList(vtDetail?.voucherList);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        campaignId: campaignId ? campaignId : "",
        voucherTemplateCode: voucherTemplateCode ? voucherTemplateCode : "",
        voucherTemplateName: voucherTemplateName ? voucherTemplateName : "",
        amount: amount ? amount : 1,
        description: description ? description : "",
        note: note ? note : "",
        dateStart: dateStart ? convertFromDateToString(dateStart) : "",
        dateEnd: dateEnd ? convertFromDateToString(dateEnd) : "",
        voucherTypeId: voucherTypeId,
        branchIds: selectedBranchList?.map((br) => br.value),
      };

      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/voucher-template/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/voucher-template");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/voucher-template/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/voucher-template");
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
              <h1>
                {id == -1 ? "Create Voucher Template" : "Edit Voucher Template"}
              </h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Template Id</label>
                    <input
                      value={voucherTemplateId}
                      disabled
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Template code</label>
                    <input
                      value={voucherTemplateCode}
                      onChange={(e) => setVoucherTemplateCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Template name</label>
                    <input
                      value={voucherTemplateName}
                      onChange={(e) => setVoucherTemplateName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Campaign</label>
                    <select
                      disabled={id == -1 ? false : true}
                      value={campaignId}
                      onChange={(e) => setCampaignId(e.target.value)}
                      className="form-control"
                    >
                      {campaignList?.map((cp) => (
                        <option value={cp?.campainId}>
                          {`${cp?.campainName}`}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                <div className="row">
                  <div className="col-lg-12">
                    <div className="form-group">
                      <label>Voucher Type</label>
                      <select
                        value={voucherTypeId}
                        onChange={(e) => setVoucherTypeId(e.target.value)}
                        className="form-control"
                      >
                        {voucherTypeList?.map((vt) => (
                          <option value={vt?.id}>{`${vt?.name}`}</option>
                        ))}
                      </select>
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="col-lg-12">
                    <div className="form-group">
                      <label>Branch</label>
                      <Select
                        options={branchList}
                        placeholder="Select branches"
                        onChange={handleSelectBranch}
                        value={selectedBranchList}
                        isMulti
                      />
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="col-lg-12">
                    <div className="form-group">
                      <label>Amount</label>
                      <input
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        className="form-control"
                        type="number"
                        min="1"
                      ></input>
                    </div>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Start Date</label>
                    <DatePicker
                      selected={dateStart}
                      onChange={(date) => setDateStart(date)}
                      dateFormat="yyyy-MM-dd"
                      className="form-control"
                    />
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>End Date</label>
                    <DatePicker
                      selected={dateEnd}
                      onChange={(date) => setDateEnd(date)}
                      dateFormat="yyyy-MM-dd"
                      className="form-control"
                    />
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Description</label>
                    <textarea
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      className="form-control"
                    ></textarea>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Note</label>
                    <textarea
                      value={note}
                      onChange={(e) => setNote(e.target.value)}
                      className="form-control"
                    ></textarea>
                  </div>
                </div>
              </div>
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                {id == -1 ? "Create" : "Edit"}
              </button>
              <Link className="btn btn-danger" to={"/voucher-template"}>
                Back
              </Link>
            </div>
          </div>
        </form>
        <h2>Voucher List</h2>
        <div
          className="py-4 table-responsive"
          style={{ "max-height": "500px" }}
        >
          <table className="table border shadow">
            <thead>
              <tr>
                <th scope="col">Id</th>
                <th scope="col">Code</th>
                <th scope="col">Name</th>
              </tr>
            </thead>
            <tbody>
              {voucherList?.map((vc, index) => (
                <>
                  <tr>
                    <th scope="row" key={vc?.voucherId}>
                      {vc?.voucherId}
                    </th>
                    <td>{vc?.voucherCode}</td>
                    <td>{vc?.voucherName}</td>
                  </tr>
                </>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
};

export default VoucherTemplateDetail;
