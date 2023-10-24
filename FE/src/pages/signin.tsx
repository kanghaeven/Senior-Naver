import styled from "styled-components";

export const SiginInWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(180deg, #46d780 0%, #5cbad8 100%);
`;

export const Logo = styled.div`
  font-family: "NanumSquareNeo";
  font-style: normal;
  font-size: 40px;
  font-weight: 900;
  line-height: 99px;
  letter-spacing: 0.05em;
  color: #000000;
`;

export const LoginForm = styled.div`
  border-radius: 30px;
  border: 1px solid rgba(0, 0, 0, 0.5);
  width: 700px;
  height: 450px;
  left: 370px;
  top: 317px;
  bottom: 3px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  form {
    position: relative;
    top: 25%;
    left: 15%;
  }
  input {
    width: 460px;
    height: 80px;
    border-radius: 5px;
    border-color: rgba(0, 0, 0, 0.25);
    font-size: 40px;
    font-weight: 500;
    font-family: "NanumSquare Neo";
    padding: 0 15px 0 15px;
  }
  .login {
    background: #2e2e2e;
    color: white;
  }
`;

export const LoginCopylight = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: end;
  top: 100px;
  font-family: "NanumSquare Neo";
  font-style: normal;
  font-weight: bolder;
  font-size: 12px;
`;
function login() {
  return (
    <SiginInWrapper>
      <Logo>
        <h1>SENIOR NAVER</h1>
      </Logo>
      <LoginForm>
        <form></form>
      </LoginForm>
    </SiginInWrapper>
  );
}
export default login;
