import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import { getUserInfoFromLocalStorage } from "../../commons/utils";
const Header = (props) => {
  return (
    <div>
      {props?.isLogin && (
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
          <a class="navbar-brand" href="#">
            Partner navigation
          </a>
          <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
              <li class="nav-item active">
                <Link class="nav-link" to={"/"}>
                  Home
                </Link>
              </li>
              <li class="nav-item">
                <Link class="nav-link" to={"/branch"}>
                  Branch
                </Link>
              </li>
              <li class="nav-item">
                <Link class="nav-link" to={"/campaign"}>
                  Campaign
                </Link>
              </li>
              <li class="nav-item">
                <Link class="nav-link" to={"/login"}>
                  Logout
                </Link>
              </li>
            </ul>
          </div>
        </nav>
      )}
    </div>
  );
};

export default Header;
