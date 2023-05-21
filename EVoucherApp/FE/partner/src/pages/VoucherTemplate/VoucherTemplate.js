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

const VoucherTemplate = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [voucherTemplates, setVoucherTemplates] = useState([]);
  const [voucherTemplateId, setVoucherTemplateId] = useState();
  const [voucherTypeId, setVoucherTypeId] = useState();
  const [campaignId, setCampaignId] = useState();
  const [voucherTemplateCode, setVoucherTemplateCode] = useState();
  const [voucherTemplateName, setVoucherTemplateName] = useState();
  const [dateStart, setDateStart] = useState();
  const [dateEnd, setDateEnd] = useState();
  const [voucherTypeList, setVoucherTypeList] = useState([]);
  const [campaignList, setCampaignList] = useState([]);

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
    loadVoucherType();
    loadCampaignList();
    loadVoucherTemplateList();
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
    const voucherTypeList = [
      {
        id: -1,
        code: "All",
        name: "All",
      },
      ...response?.data?.voucherTypeDtoList,
    ];
    setVoucherTypeList(voucherTypeList);
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
    const campaignList = [
      {
        campainId: -1,
        campainName: "All",
      },
      ...response?.data?.campaignDtoList,
    ];
    setCampaignList(campaignList);
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
    setVoucherTemplates(response?.data?.voucherTemplateDtoList);
  };

  const handleSearch = (e) => {
    e.preventDefault();

    let params = {
      voucherTemplateId: voucherTemplateId ? voucherTemplateId : null,
      voucherTemplateCode: voucherTemplateCode ? voucherTemplateCode : "",
      voucherTemplateName: voucherTemplateName ? voucherTemplateName : "",
      campaignId: campaignId && campaignId != -1 ? campaignId : null,
      voucherTypeId:
        voucherTypeId && voucherTypeId != -1 ? voucherTypeId : null,
      dateStart: dateStart ? convertFromDateToString(dateStart) : "",
      dateEnd: dateEnd ? convertFromDateToString(dateEnd) : "",
    };
    loadVoucherTemplateList(params);
  };

  return (
    <div className="col-lg-12">
      <div>
        <form onSubmit={handleSearch}>
          <div className="card">
            <div className="card-header">
              <h1>Search Voucher Template</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Template Id</label>
                    <input
                      value={voucherTemplateId}
                      onChange={(e) => setVoucherTemplateId(e.target.value)}
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
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Voucher Template Name</label>
                    <input
                      value={voucherTemplateName}
                      onChange={(e) => setVoucherTemplateName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Voucher Type</label>
                    <select
                      value={voucherTypeId}
                      onChange={(e) => setVoucherTypeId(e.target.value)}
                      className="form-control"
                    >
                      {voucherTypeList?.map((vt) => (
                        <option value={vt?.id}>
                          {`${vt?.code} - ${vt?.name}`}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Campaign</label>
                    <select
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
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Search
              </button>
              <Link className="btn btn-success" to={`/voucher-template/${-1}`}>
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Voucher Template List</h2>
      <div className="py-4 table-responsive" style={{ "max-height": "500px" }}>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Code</th>
              <th scope="col">Name</th>
              <th scope="col">Type</th>
              <th scope="col">Campaign</th>
              <th scope="col">Amount</th>
              <th scope="col">Date start</th>
              <th scope="col">Date end</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {voucherTemplates?.map((vt, index) => (
              <>
                <tr>
                  <th scope="row" key={vt?.voucherTemplateId}>
                    {vt?.voucherTemplateId}
                  </th>
                  <td>{vt?.voucherTemplateCode}</td>
                  <td>{vt?.voucherTemplateName}</td>
                  <td>{vt?.voucherType?.name}</td>
                  <td>{vt?.campaign?.campainName}</td>
                  <td>{vt?.amount}</td>
                  <td>{vt?.dateStart}</td>
                  <td>{vt?.dateEnd}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/voucher-template/${vt?.voucherTemplateId}`}
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

export default VoucherTemplate;
