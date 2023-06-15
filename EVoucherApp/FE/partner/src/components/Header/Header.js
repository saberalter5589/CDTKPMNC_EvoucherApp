import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { PARTNER } from "../../commons/constant";
import { CUSTOMER } from "../../commons/constant";
import { ADMIN } from "../../commons/constant";

const Header = (props) => {
  const { userInfo } = props;
  const [menuList, setMenuList] = useState([]);
  const [title, setTitle] = useState("");
  const renderMenu = () => {
    switch (userInfo?.userTypeId) {
      case ADMIN:
        setMenuList([
          {
            to: "/",
            name: "Home",
            class: "nav-link active",
          },
          {
            to: "/partner-type",
            name: "Partner Type",
            class: "nav-link",
          },
          {
            to: "/voucher-type",
            name: "Voucher Type",
            class: "nav-link",
          },
          {
            to: "/partner",
            name: "Partner",
            class: "nav-link",
          },
          {
            to: "/customer",
            name: "Customer",
            class: "nav-link",
          },
          {
            to: "/login",
            name: "Logout",
            class: "nav-link",
          },
        ]);
        break;
      case PARTNER:
        setMenuList([
          {
            to: "/",
            name: "Home",
            class: "nav-link active",
          },
          {
            to: `/partner-detail/${userInfo?.userId}`,
            name: "My Info",
            class: "nav-link",
          },
          {
            to: "/branch",
            name: "Branch",
            class: "nav-link",
          },
          {
            to: "/campaign",
            name: "Campaign",
            class: "nav-link",
          },
          {
            to: "/voucher-template",
            name: "Voucher Template",
            class: "nav-link",
          },
          {
            to: "/login",
            name: "Logout",
            class: "nav-link",
          },
        ]);
        break;
      case CUSTOMER:
        setMenuList([
          {
            to: "/",
            name: "Home",
            class: "nav-link active",
          },
          {
            to: `/customer-detail/${userInfo?.userId}`,
            name: "My Info",
            class: "nav-link",
          },
          {
            to: "/campaign",
            name: "Campaign",
            class: "nav-link",
          },
          {
            to: "/voucher",
            name: "Voucher",
            class: "nav-link",
          },
          {
            to: "/login",
            name: "Logout",
            class: "nav-link",
          },
        ]);
    }
  };

  const renderTitle = () => {
    switch (userInfo?.userTypeId) {
      case ADMIN:
        setTitle("Administrator Page");
        break;
      case PARTNER:
        setTitle("Partner Page");
        break;
      case CUSTOMER:
        setTitle("Customer Page");
        break;
    }
  };

  useEffect(() => {
    renderMenu();
    renderTitle();
  }, [userInfo]);

  const onChangeActiveTab = (to) => {
    const newMenu = menuList?.map((m) => ({
      ...m,
      class: m?.to == to ? "nav-link active" : "nav-link",
    }));
    setMenuList(newMenu);
  };

  return (
    <div>
      {props?.isLogin && (
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
          <a class="navbar-brand" href="#">
            {title}
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
            <ul class="nav nav-pills">
              {menuList?.map((menu) => (
                <li
                  class="nav-item"
                  onClick={() => onChangeActiveTab(menu?.to)}
                >
                  <Link class={menu?.class} to={menu?.to}>
                    {menu?.name}
                  </Link>
                </li>
              ))}
            </ul>
          </div>
        </nav>
      )}
    </div>
  );
};

export default Header;
