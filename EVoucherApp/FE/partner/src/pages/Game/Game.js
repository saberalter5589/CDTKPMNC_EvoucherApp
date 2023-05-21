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
import { useLocation } from "react-router-dom";

const Game = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { state } = useLocation();
  const { campaignId, gameId } = state;
  const [gameInfo, setGameInfo] = useState();
  const [isFinished, setIsFinished] = useState(false);
  const [voucher, setVoucher] = useState();

  useEffect(() => {
    loadGame();
  }, [state]);

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
    setGameInfo(gameList?.find((g) => g.gameId == gameId));
  };

  const getVoucher = async () => {
    setIsFinished(true);
    try {
      const payload = {
        authentication: {
          userId: userInfo?.userId,
          password: userInfo?.password,
        },
        campaignId: campaignId ? campaignId : "",
      };

      const response = await axios.post(
        "http://localhost:8080/api/voucher/create",
        payload
      );
      const voucher = response?.data?.voucher;
      setVoucher(voucher);
    } catch (error) {
      setIsFinished(false);
    }
  };

  useEffect(() => {
    console.log(voucher);
  }, [voucher]);

  const renderVoucher = () => {
    if (voucher?.voucherId == -1) {
      return (
        <>
          <div>Lucky next time</div>
        </>
      );
    } else {
      return (
        <>
          <h1>Congratulation !! You have won voucher below</h1>
          <h5>Voucher code: {voucher?.voucherCode}</h5>
          <h5>Voucher name: {voucher?.voucherName}</h5>
          <h5>Description: {voucher?.description}</h5>
          <Link
            className="btn btn-info "
            to={`/voucher-detail/${voucher?.voucherId}`}
          >
            Go to your voucher
          </Link>
          <Link className="btn btn-danger " to={"/campaign"}>
            Back
          </Link>
        </>
      );
    }
  };

  const renderGame = () => {
    if (gameInfo?.gameId == 1) {
      return (
        <>
          <button
            type="button"
            class="btn btn-primary btn-lg btn-block"
            onClick={() => getVoucher()}
          >
            Get voucher
          </button>
        </>
      );
    } else {
      return (
        <>
          <ol class="quiz" type="1">
            <li>
              <h4>How many letters are there in "JS"?</h4>
              <ol class="choices" type="A">
                <li>
                  <label>
                    <input type="radio" name="question0" value="A" />
                    <span>2</span>
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="question0" value="B" />
                    <span>1</span>
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="question0" value="C" />
                    <span>3</span>
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="question0" value="D" />
                    <span>4</span>
                  </label>
                </li>
              </ol>
            </li>
            <li>
              <h4>2 + 3 = ?</h4>
              <ol class="choices" type="A">
                <li>
                  <label>
                    <input type="radio" name="question1" value="A" />
                    <span>2</span>
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="question1" value="B" />
                    <span>3</span>
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="question1" value="C" />
                    <span>4</span>
                  </label>
                </li>
                <li>
                  <label>
                    <input type="radio" name="question1" value="D" />
                    <span>5</span>
                  </label>
                </li>
              </ol>
            </li>
          </ol>
          <button
            type="button"
            class="btn btn-primary btn-lg btn-block"
            onClick={() => getVoucher()}
          >
            Submit
          </button>
        </>
      );
    }
  };

  return (
    <>
      {!isFinished && (
        <>
          <h1>{gameInfo?.gameName}</h1>
          <h3>Description: {gameInfo?.description}</h3>
        </>
      )}

      {!isFinished && renderGame()}
      {isFinished && renderVoucher()}
    </>
  );
};

export default Game;
