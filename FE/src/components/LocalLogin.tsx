import React from "react";
import styled from "styled-components";
import NaverLogin from "./NaverLogin";

export const LoginFormWrapper = styled.div`
  border-radius: 30px;
  border: 1px solid rgba(0, 0, 0, 0.5);
  width: 700px;
  height: 450px;
  left: 370px;
  top: 317px;
  bottom: 3px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
`;

export const LoginForm = styled.form`
  position: relative;
  top: 20%;
  left: 15%;
`;

export const IDInput = styled.input`
  width: 460px;
  height: 80px;
  border-radius: 10px;
  border-color: rgba(0, 0, 0, 0.25);
  font-size: 40px;
  font-weight: 500;
  font-family: "NanumSquare Neo";
  padding: 0 15px 0 15px;
`;

export const PasswordInput = styled.input`
  width: 460px;
  height: 80px;
  border-radius: 10px;
  border-color: rgba(0, 0, 0, 0.25);
  font-size: 40px;
  font-weight: 500;
  font-family: "NanumSquare Neo";
  padding: 0 15px 0 15px;
`;

export const LoginSubmit = styled.input`
  margin-top: 40px;
  width: 240px;
  height: 80px;
  font-size: 30px;
  border-radius: 30px;
  background: #2e2e2e;
  color: white;
`;

function LocalLogin() {
  return (
    <LoginFormWrapper>
      <LoginForm>
        <IDInput type="text" placeholder="아이디" />
        <PasswordInput type="text" placeholder="비밀번호" />
        <LoginSubmit className="login" type="submit" value={"로그인"}></LoginSubmit>
        <NaverLogin />
      </LoginForm>
    </LoginFormWrapper>
  );
}

export default LocalLogin;
