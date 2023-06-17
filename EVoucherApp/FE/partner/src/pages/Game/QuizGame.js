import React, { useState, useEffect } from "react";
import { QuizGameQuestions } from "../../commons/constant";
import { getRandomIndex } from "../../commons/utils";
const QuizGame = ({ onCheckAnswer, onSetCorrectAnswer }) => {
  const [quiz, setQuiz] = useState();
  const [choice, setChoice] = useState();
  const checkAnswer = (e) => {
    e.preventDefault();
    onCheckAnswer(choice == quiz?.correct);
    onSetCorrectAnswer(quiz?.correct + ". " + quiz?.correctAnswer);
  };
  useEffect(() => {
    const id = getRandomIndex(QuizGameQuestions?.length);
    const quiz = QuizGameQuestions?.find((q) => q?.quizId == id);
    setQuiz(quiz);
  }, []);

  return (
    <>
      <form onSubmit={checkAnswer}>
        <div class="container mt-5">
          <div class="d-flex justify-content-center row">
            <div class="col-md-10 col-lg-10">
              <div class="border">
                <div class="question bg-white p-3 border-bottom">
                  <div class="d-flex flex-row justify-content-between align-items-center mcq">
                    <h4>Quiz</h4>
                  </div>
                </div>
                <div class="question bg-white p-3 border-bottom">
                  {
                    <div class="d-flex flex-row align-items-center question-title">
                      <h3 class="text-danger">Q.</h3>
                      <h5 class="mt-1 ml-2">{quiz?.question}</h5>
                    </div>
                  }
                  <ol class="choices" type="A">
                    {quiz?.answers?.map((answer) => (
                      <li>
                        <label>
                          <div className="form-group">
                            <input
                              type="radio"
                              name="question0"
                              value={answer?.value}
                              onChange={(e) => setChoice(e?.target?.value)}
                            />
                            <span>{answer?.content}</span>
                          </div>
                        </label>
                      </li>
                    ))}
                  </ol>
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
    </>
  );
};

export default QuizGame;
