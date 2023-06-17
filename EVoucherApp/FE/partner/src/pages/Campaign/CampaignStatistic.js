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

const CampaignStatistic = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const [campaigns, setCampaigns] = useState([]);
  const [campainId, setCampainId] = useState(null);
  const [campaignStatistics, setCampaignStatistics] = useState([]);

  useEffect(() => {
    if (userInfo == null) {
      navigate("/login");
    }
    loadCampaign({
      partnerId: userInfo?.userId,
    });
    loadCampaignStatistic();
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
    const campaignList = [
      {
        campainId: -1,
        campainCode: "All",
        campainName: "All",
      },
      ...response?.data?.campaignDtoList,
    ];
    setCampaigns(campaignList);
  };

  const loadCampaignStatistic = async (params) => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
      params: params,
    };
    const response = await axios.get(
      "http://localhost:8080/api/campaign/statistic",
      config
    );
    setCampaignStatistics(response?.data?.campaignStatisticDtoList);
  };

  const handleSearch = (e) => {
    e.preventDefault();
    let params = {
      campaignId: campainId && campainId != -1 ? campainId : null,
    };
    console.log(params);
    loadCampaignStatistic(params);
  };

  return (
    <div className="col-lg-12">
      <div>
        <form onSubmit={handleSearch}>
          <div className="card">
            <div className="card-header">
              <h1>Search Campaign Statistic</h1>
            </div>
            <div className="card-body">
              <div className="row">
                <div className="col-lg-12">
                  <div className="form-group">
                    <label>Campaign</label>
                    <select
                      value={campainId}
                      onChange={(e) => setCampainId(e.target.value)}
                      className="form-control"
                    >
                      {campaigns?.map((campaign) => (
                        <option value={campaign?.campainId}>
                          {`${campaign?.campainCode} - ${campaign?.campainName}`}
                        </option>
                      ))}
                    </select>
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
      <h2>Campaign List</h2>
      <div className="py-4 table-responsive" style={{ "max-height": "500px" }}>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">Campaign Id</th>
              <th scope="col">Campaign Code</th>
              <th scope="col">Campaign name</th>
              <th scope="col">Total Customer</th>
              <th scope="col">Total Voucher</th>
              <th scope="col">Total used Voucher</th>
            </tr>
          </thead>
          <tbody>
            {campaignStatistics?.map((campaignStatistic, index) => (
              <>
                <tr>
                  <th scope="row" key={campaignStatistic?.campaignId}>
                    {campaignStatistic?.campaignId}
                  </th>
                  <td>{campaignStatistic?.campaignCode}</td>
                  <td>{campaignStatistic?.campaignName}</td>
                  <td>{campaignStatistic?.totalCustomer}</td>
                  <td>{campaignStatistic?.totalVoucher}</td>
                  <td>{campaignStatistic?.totalUsedVoucher}</td>
                </tr>
              </>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CampaignStatistic;
