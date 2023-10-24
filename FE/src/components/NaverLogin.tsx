import React from "react";
import styled from "styled-components";
import naverlogo from "./../assets/images/naversmalllogo.png";
export const NaverLoginWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 240px;
  height: 80px;
  background: #03c75a;
  border-radius: 30px;
  font-size: 24px;
  color: white;
`;
function NaverLogin() {
  return <NaverLoginWrapper>네이버 로그인</NaverLoginWrapper>;
}

export default NaverLogin;
