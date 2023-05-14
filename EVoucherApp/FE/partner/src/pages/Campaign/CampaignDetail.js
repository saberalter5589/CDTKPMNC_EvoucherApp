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

const CampaignDetail = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { id } = useParams();
  const [campaignId, setCampaignId] = useState(null);
  const [campaignCode, setCampaignCode] = useState("");
  const [campaignName, setCampaignName] = useState("");
  const [campaignStatus, setCampaignStatus] = useState("");
  const [dateStart, setDateStart] = useState();
  const [dateEnd, setDateEnd] = useState();
  const [description, setDescription] = useState("");
  const [note, setNote] = useState("");
  const [gameOptionList, setGameOptionList] = useState([]);
  const [selectedGameOptions, setSelectedGameOptions] = useState();

  useEffect(() => {
    if (userInfo == null || userInfo?.userTypeId != PARTNER) {
      navigate("/login");
    }
    loadGame();
    if (id != -1) {
      const params = {
        campaignId: id,
      };
      loadCampaign(params);
    }
  }, []);

  const loadGame = async () => {
    let config = {
      headers: {
        userId: userInfo?.userId,
        password: userInfo?.password,
      },
    };
    const response = await axios.get(
      "http://localhost:8080/api/game/search",
      config
    );
    const gameList = response?.data?.gameList;
    const gameOptionList = gameList?.map((gm) => ({
      value: gm?.gameId,
      label: gm?.gameCode + " - " + gm?.gameName,
    }));
    setGameOptionList(gameOptionList);
  };

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
    setCampaignStatus(campaign?.campaignStatus);
    setDateStart(
      campaign?.dateStart ? convertFromStringToDate(campaign?.dateStart) : null
    );
    setDateEnd(
      campaign?.dateEnd ? convertFromStringToDate(campaign?.dateEnd) : null
    );
    const selectedGameList = campaign?.gameList;
    const selectedGameOptions = selectedGameList?.map((gm) => ({
      value: gm?.id,
      label: gm?.code + " - " + gm?.name,
    }));
    setSelectedGameOptions(selectedGameOptions);
    setDescription(campaign?.description);
    setNote(campaign?.note);
  };

  const handleSelectGame = (data) => {
    setSelectedGameOptions(data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        campainCode: campaignCode ? campaignCode : "",
        campainName: campaignName ? campaignName : "",
        dateStart: dateStart ? convertFromDateToString(dateStart) : "",
        dateEnd: dateEnd ? convertFromDateToString(dateEnd) : "",
        description: description ? description : "",
        note: note ? note : "",
        gameIds: selectedGameOptions?.map((game) => game.value),
        status: campaignStatus,
      };
      if (id != -1) {
        const res = await axios.put(
          `http://localhost:8080/api/campaign/${id}`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/campaign");
        }
      } else {
        const res = await axios.post(
          `http://localhost:8080/api/campaign/create`,
          payload
        );
        if (res.status == 200) {
          toast.success("Success");
          navigate("/campaign");
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
              <h1>{id == -1 ? "Create Campaign" : "Edit Campaign"}</h1>
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
                    <label>Campaign name</label>
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
                    <label>Game</label>
                    <Select
                      options={gameOptionList}
                      placeholder="Select game"
                      onChange={handleSelectGame}
                      value={selectedGameOptions}
                      isMulti
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
              <Link className="btn btn-danger" to={"/campaign"}>
                Back
              </Link>
            </div>
          </div>
        </form>
      </div>
    </>
  );
};
export default CampaignDetail;
