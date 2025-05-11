import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

const OAuth2RedirectHandler = () => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const token = params.get("token");
    if (token) {
      localStorage.setItem("token", token);
      navigate("/"); // chuyển hướng về trang chính
    } else {
      navigate("/login?error");
    }
  }, [location, navigate]);

  return (
    <>
      <p>Đang xử lý đăng nhập...</p>
    </>
  );
};

export default OAuth2RedirectHandler;