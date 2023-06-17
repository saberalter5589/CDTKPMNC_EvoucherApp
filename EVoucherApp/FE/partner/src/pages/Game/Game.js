import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useState, useEffect } from "react";
import "react-toastify/dist/ReactToastify.css";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
import "react-datepicker/dist/react-datepicker.css";
import { useLocation } from "react-router-dom";
import QuizGame from "./QuizGame";
import ClickGame from "./ClickGame";
import WordScrambleGame from "./WordScrambleGame";
import VoucherComponent from "./VoucherComponent";

const Game = () => {
  const [userInfo, setUserInfo] = useState(getUserInfoFromLocalStorage());
  const navigate = useNavigate();
  const { state } = useLocation();
  const { campaignId, gameId } = state;
  const [gameInfo, setGameInfo] = useState();
  const [isFinished, setIsFinished] = useState(false);
  const [voucher, setVoucher] = useState();
  const [isWin, setIsWin] = useState(true);
  const [correctAnswer, setCorrectAnswer] = useState();

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

  const checkAnswer = (result) => {
    if (result == true) {
      getVoucher();
    } else {
      setIsWin(false);
    }
  };

  const renderVoucher = () => {
    if (voucher?.voucherId == -1) {
      return (
        <>
          <div class="container mt-5 justify-content-center">
            <h1 class="d-flex justify-content-center row">
              Sorry, Wish you luck next time
            </h1>
            <div className="row">
              <div class="col-4 d-flex justify-content-center"></div>
              <div class="col-4 d-flex justify-content-center">
                <Link className=" btn btn-danger " to={"/campaign"}>
                  Back
                </Link>
              </div>
              <div class="col-4 d-flex justify-content-center"></div>
            </div>
          </div>
        </>
      );
    } else {
      return (
        <>
          <VoucherComponent voucher={voucher} />
        </>
      );
    }
  };

  const renderLoseGame = () => {
    return (
      <>
        <div class="container mt-5 justify-content-center">
          <h1 class="d-flex justify-content-center row">
            Sorry, You lose the game
          </h1>
          {correctAnswer && (
            <>
              <h4 class="d-flex text-info justify-content-center row">
                Correct answer is: {correctAnswer}
              </h4>
            </>
          )}

          <div className="row">
            <div class="col-4 d-flex justify-content-center"></div>
            <div class="col-4 d-flex justify-content-center">
              <Link className=" btn btn-danger " to={"/campaign"}>
                Back
              </Link>
            </div>
            <div class="col-4 d-flex justify-content-center"></div>
          </div>
        </div>
      </>
    );
  };

  const renderGame = () => {
    if (gameInfo?.gameId == 1) {
      return (
        <>
          {/* <ClickGame onCheckAnswer={checkAnswer} /> */}
          <WordScrambleGame
            onCheckAnswer={checkAnswer}
            onSetCorrectAnswer={setCorrectAnswer}
          />
        </>
      );
    } else if (gameInfo?.gameId == 4) {
      return (
        <>
          <QuizGame
            onCheckAnswer={checkAnswer}
            onSetCorrectAnswer={setCorrectAnswer}
          />
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
      {!isFinished && isWin && renderGame()}
      {isFinished && renderVoucher()}
      {!isWin && renderLoseGame()}
    </>
  );
};

export default Game;
