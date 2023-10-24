import styled from "styled-components";
import LocalLogin from "../components/LocalLogin";
export const LoginWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(180deg, #46d780 0%, #5cbad8 100%);
`;

export const Logo = styled.div`
  font-family: "NanumSquare Neo Heavy";
  font-style: normal;
  font-size: 40px;
  font-weight: 1000;
  line-height: 99px;
  letter-spacing: 0.05em;
  color: #000000;
`;

export const LoginCopylight = styled.div`
  display: flex;
  margin-top: 30px;
  flex-direction: column;
  justify-content: end;
  font-family: "NanumSquare Neo";
  font-style: normal;
  font-weight: bolder;
  font-size: 12px;
`;
function login() {
  return (
    <LoginWrapper>
      <Logo>
        <h1>SENIOR NAVER</h1>
      </Logo>
      <LocalLogin />
      <LoginCopylight>
        <h3>Copyright © NAVER Corp. All Rights Reserved.</h3>
      </LoginCopylight>
    </LoginWrapper>
  );
}
export default login;
