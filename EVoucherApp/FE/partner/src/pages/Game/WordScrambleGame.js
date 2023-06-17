import React, { useState, useEffect } from "react";
import { WordScrambleQuestion } from "../../commons/constant";
import { getRandomIndex } from "../../commons/utils";

const WordScrambleGame = ({ onCheckAnswer, onSetCorrectAnswer }) => {
  const [question, setQuestion] = useState();
  const [answer, setAnswer] = useState();

  const checkAnswer = (e) => {
    e.preventDefault();
    onCheckAnswer(question?.correct == answer?.toUpperCase());
    onSetCorrectAnswer(question?.correct);
  };

  useEffect(() => {
    const id = getRandomIndex(WordScrambleQuestion?.length);
    const question = WordScrambleQuestion?.find((q) => q?.id == id);
    setQuestion(question);
  }, []);

  return (
    <form onSubmit={checkAnswer}>
      <div class="container mt-5">
        <div class="d-flex justify-content-center row">
          <div class="col-md-10 col-lg-10">
            <div class="border">
              <div class="question bg-white p-3 border-bottom">
                <div class="d-flex flex-row justify-content-between align-items-center mcq">
                  <h4>Word guessing</h4>
                </div>
              </div>
              <div class="question bg-white p-3 border-bottom">
                <div class="d-flex flex-row align-items-center question-title">
                  <h3 class="text-danger">Scrambled word:&nbsp;&nbsp;&nbsp;</h3>
                  <h5 class="mt-1 ml-2">{question?.scrambledWord}</h5>
                </div>
                <h6 style={{ fontStyle: "italic" }}>Hint: {question?.hint}</h6>
                <div className="form-group">
                  <label>Your answer</label>
                  <input
                    value={answer}
                    onChange={(e) => setAnswer(e.target.value)}
                    className="form-control"
                  ></input>
                </div>
              </div>
              <div class="d-flex flex-row justify-content-between align-items-center p-3 bg-white">
                <button
                  class="btn btn-primary border-success align-items-center btn-success"
                  type="submit"
                >
                  Answer<i class="fa fa-angle-right ml-2"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default WordScrambleGame;
