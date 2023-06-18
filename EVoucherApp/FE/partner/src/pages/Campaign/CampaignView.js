import { Link, useNavigate, useParams } from "react-router-dom";
import { CUSTOMER, PARTNER } from "../../commons/constant";
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

const CampaignView = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [campaignId, setCampaignId] = useState(null);
  const [campaignCode, setCampaignCode] = useState("");
  const [campaignName, setCampaignName] = useState("");
  const [dateStart, setDateStart] = useState();
  const [dateEnd, setDateEnd] = useState();
  const [description, setDescription] = useState("");
  const [note, setNote] = useState("");
  const [gameOptionList, setGameOptionList] = useState([]);
  const [gameId, setGameId] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != CUSTOMER) {
      navigate("/login");
    }
    if (id != -1) {
      const params = {
        campaignId: id,
      };
      loadCampaign(params);
    }
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
    const campaign = response?.data?.campaignDtoList[0];
    setCampaignId(campaign?.campainId);
    setCampaignCode(campaign?.campainCode);
    setCampaignName(campaign?.campainName);
    setDateStart(
      campaign?.dateStart ? convertFromStringToDate(campaign?.dateStart) : null
    );
    setDateEnd(
      campaign?.dateEnd ? convertFromStringToDate(campaign?.dateEnd) : null
    );
    setDescription(campaign?.description);
    setNote(campaign?.note);
    setGameOptionList(campaign?.gameList);
    setGameId(campaign?.gameList[0]?.id);
  };

  return (
    <div className="col-lg-12">
      <div className="card">
        <div className="card-header">
          <h1>View Campaign</h1>
        </div>
        <div className="card-body">
          <div className="row">
            <div className="col-lg-6">
              <div className="form-group">
                <label>Campaign Id</label>
                <input
                  value={campaignId}
                  disabled
                  className="form-control"
                ></input>
              </div>
            </div>
            <div className="col-lg-6">
              <div className="form-group">
                <label>Campaign code</label>
                <input
                  disabled
                  value={campaignCode}
                  className="form-control"
                ></input>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-lg-12">
              <div className="form-group">
                <label>Campaign name</label>
                <input
                  disabled
                  value={campaignName}
                  className="form-control"
                ></input>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-lg-6">
              <div className="form-group">
                <label>Start Date</label>
                <DatePicker
                  disabled
                  selected={dateStart}
                  dateFormat="yyyy-MM-dd"
                  className="form-control"
                />
              </div>
            </div>
            <div className="col-lg-6">
              <div className="form-group">
                <label>End Date</label>
                <DatePicker
                  disabled
                  selected={dateEnd}
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
                  disabled
                  value={description}
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
                  disabled
                  value={note}
                  className="form-control"
                ></textarea>
              </div>
            </div>
          </div>
          <div className="row">
            <h2> Choose game to play </h2>
            <div className="col-lg-12 text-center">
              <div className="form-group">
                <h3>Game list</h3>
                <select
                  value={gameId}
                  onChange={(e) => setGameId(e.target.value)}
                  className="form-control"
                >
                  {gameOptionList?.map((gm) => (
                    <option value={gm?.id}>
                      {`${
                        gm?.code + " - " + gm?.name + " => " + gm?.description
                      }`}
                    </option>
                  ))}
                </select>
                <Link
                  className="btn btn-info "
                  to={"/game"}
                  state={{ campaignId: id, gameId: gameId }}
                >
                  PLAY GAME
                </Link>
                <Link className="btn btn-danger " to={"/campaign"}>
                  Back
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CampaignView;
