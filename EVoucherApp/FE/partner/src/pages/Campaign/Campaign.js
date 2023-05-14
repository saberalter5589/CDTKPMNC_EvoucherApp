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

const Campaign = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [campaigns, setCampaigns] = useState([]);
  const [campaignId, setCampaignId] = useState(null);
  const [campaignCode, setCampaignCode] = useState("");
  const [campaignName, setCampaignName] = useState("");
  const [campaignStatus, setCampaignStatus] = useState(
    CAMPAIGN_STATUS.NOT_START
  );
  const [dateStart, setDateStart] = useState();
  const [dateEnd, setDateEnd] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
    loadCampaign(null);
  }, []);

  const loadCampaign = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/campaign/search",
      config
    );
    setCampaigns(response?.data?.campaignDtoList);
  };

  const renderStatus = (status) => {
    console.log(status);
    switch (status) {
      case CAMPAIGN_STATUS.NOT_START:
        return "Not started";
      case CAMPAIGN_STATUS.IN_PROGRESS:
        return "In progress";
      case CAMPAIGN_STATUS.END:
        return "End";
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      campaignId: campaignId ? campaignId : null,
      campaignCode: campaignCode ? campaignCode : "",
      campaignName: campaignName ? campaignName : "",
      status: campaignStatus ? campaignStatus : null,
      dateStart: dateStart ? convertFromDateToString(dateStart) : "",
      dateEnd: dateEnd ? convertFromDateToString(dateEnd) : "",
    };
    loadCampaign(params);
  };

  return (
    <div className="col-lg-12">
      <div>
        <form onSubmit={handleSearch}>
          <div className="card">
            <div className="card-header">
              <h1>Search Campaign</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Campaign Id</label>
                    <input
                      value={campaignId}
                      onChange={(e) => setCampaignId(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Campaign code</label>
                    <input
                      value={campaignCode}
                      onChange={(e) => setCampaignCode(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Campaign Name</label>
                    <input
                      value={campaignName}
                      onChange={(e) => setCampaignName(e.target.value)}
                      className="form-control"
                    ></input>
                  </div>
                </div>
                <div className="col-lg-6">
                  <div className="form-group">
                    <label>Status</label>
                    <select
                      value={campaignStatus}
                      onChange={(e) => setCampaignStatus(e.target.value)}
                      className="form-control"
                    >
                      <option value={CAMPAIGN_STATUS.NOT_START}>
                        Not Started
                      </option>
                      <option value={CAMPAIGN_STATUS.IN_PROGRESS}>
                        In progress
                      </option>
                      <option value={CAMPAIGN_STATUS.END}>End</option>
                    </select>
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
            </div>
            <div className="card-footer text-center">
              <button type="submit" className="btn btn-primary">
                Search
              </button>
              <Link className="btn btn-success" to={`/campaign-detail/${-1}`}>
                Create new
              </Link>
            </div>
          </div>
        </form>
      </div>
      <h2>Campaign List</h2>
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Campaign Id</th>
              <th scope="col">Campaign Code</th>
              <th scope="col">Campaign name</th>
              <th scope="col">Status</th>
              <th scope="col">Date start</th>
              <th scope="col">Date end</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {campaigns?.map((campaign, index) => (
              <>
                <tr>
                  <th scope="row" key={campaign?.campainId}>
                    {campaign?.campainId}
                  </th>
                  <td>{campaign?.campainCode}</td>
                  <td>{campaign?.campainName}</td>
                  <td>{renderStatus(campaign?.status)}</td>
                  <td>{campaign?.dateStart}</td>
                  <td>{campaign?.dateEnd}</td>
                  <td>
                    <Link
                      className="btn btn-success"
                      to={`/campaign-detail/${campaign?.campainId}`}
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

export default Campaign;
