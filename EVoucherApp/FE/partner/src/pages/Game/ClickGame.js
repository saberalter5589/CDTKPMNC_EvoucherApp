import React, { useState, useEffect } from "react";
const ClickGame = ({ onCheckAnswer }) => {
  const checkAnswer = (e) => {
    e.preventDefault();
    onCheckAnswer(true);
  };
  return (
    <>
      <form>
        <div class="container mt-5">
          <div class="d-flex justify-content-center row">
            <button
              type="button"
              class="btn btn-primary btn-lg btn-block"
              onClick={(e) => checkAnswer(e)}
            >
              Click here to see if you is lucky enough
            </button>
          </div>
        </div>
      </form>
    </>
  );
};

export default ClickGame;
