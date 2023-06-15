import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { CUSTOMER, PARTNER } from "../../commons/constant";
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
  const [campaignStatus, setCampaignStatus] = useState(-1);
  const [dateStart, setDateStart] = useState();
  const [dateEnd, setDateEnd] = useState();
  const [partnerList, setPartnerList] = useState();
  const [partnerId, setPartnerId] = useState(-1);

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    if (userInfo?.userTypeId == CUSTOMER) {
      loadPartner();
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

  const renderStatus = (status) => {
    switch (status) {
      case CAMPAIGN_STATUS.NOT_START:
        return "Not started";
      case CAMPAIGN_STATUS.IN_PROGRESS:
        return "In progress";
      case CAMPAIGN_STATUS.END:
        return "End";
      default:
        return "All";
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      campaignId: campaignId ? campaignId : null,
      campaignCode: campaignCode ? campaignCode : "",
      campaignName: campaignName ? campaignName : "",
      status: campaignStatus && campaignStatus != -1 ? campaignStatus : null,
      dateStart: dateStart ? convertFromDateToString(dateStart) : "",
      dateEnd: dateEnd ? convertFromDateToString(dateEnd) : "",
      partnerId:
        userInfo?.userTypeId == CUSTOMER && partnerId != -1 ? partnerId : "",
    };
    loadCampaign(params);
  };

  const deleteCampaign = async (e, id) => {
    e.preventDefault();
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    try {
      const res = await axios.delete(
        `http://localhost:8080/api/campaign/${id}`,
        config
      );
      if (res.status == 200) {
        toast.success("Success");
        loadCampaign();
      }
    } catch (error) {
      toast.error("Error");
    }
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
                      <option value={-1}>All</option>
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
                {userInfo?.userTypeId == CUSTOMER && (
                  <>
                    <div className="row">
                      <div className="col-lg-12">
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
                    </div>
                  </>
                )}
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
              {userInfo?.userTypeId == PARTNER && (
                <Link className="btn btn-success" to={`/campaign-detail/${-1}`}>
                  Create new
                </Link>
              )}
            </div>
          </div>
        </form>
      </div>
      <h2>Campaign List</h2>
      <div className="py-4 table-responsive" style={{ "max-height": "500px" }}>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Campaign Id</th>
              <th scope="col">Campaign Code</th>
              <th scope="col">Campaign name</th>
              {userInfo?.userTypeId == CUSTOMER && <th scope="col">Partner</th>}
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
                  {userInfo?.userTypeId == CUSTOMER && (
                    <td>{campaign?.partner?.partnerName}</td>
                  )}
                  <td>{renderStatus(campaign?.status)}</td>
                  <td>{campaign?.dateStart}</td>
                  <td>{campaign?.dateEnd}</td>
                  <td>
                    {userInfo?.userTypeId == PARTNER && (
                      <Link
                        className="btn btn-success"
                        to={`/campaign-detail/${campaign?.campainId}`}
                      >
                        Edit
                      </Link>
                    )}
                    {userInfo?.userTypeId == PARTNER && (
                      <button
                        className="btn btn-danger mx-2"
                        onClick={(e) => deleteCampaign(e, campaign?.campainId)}
                      >
                        Delete
                      </button>
                    )}
                    {userInfo?.userTypeId == CUSTOMER &&
                      campaign?.status == CAMPAIGN_STATUS.IN_PROGRESS && (
                        <Link
                          className="btn btn-success"
                          to={`/campaign-view/${campaign?.campainId}`}
                        >
                          View detail
                        </Link>
                      )}
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
