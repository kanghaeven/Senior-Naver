import { useState, useEffect } from "react";
import styled from "styled-components";
// import { memeMineCurrentPracticeState } from "../states/useMeme";
// import { useRecoilState,useSetRecoilState } from "recoil";
import posefileformbutton from "./../assets/images/posefileformbutton.png"


const MemeDictionaryPracticeWraaper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`
// case 0
const MemeDictionaryPracticeText = styled.div`
  display: flex;
  font-family: "NanumSquareNeoBold";
  font-size: 44px;
  text-align: center;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
`

const MemeDictionaryPracticeHeader = styled.div`
  display: flex;
  font-family: "NanumSquareNeoHeavy";
  font-size: 50px;
  text-align: center;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
`
const LockedNextButton = styled.div`
  margin-top: 60px;
  width: 550px;
  height: 80px;
  background: #bcbcbc;
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
`;
const NextButton = styled.div`
  margin-top: 60px;
  width: 550px;
  height: 80px;
  background: #2e2e2e;
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  &:hover {
    border: 2px solid transparent;
    padding: 5px;
    background: linear-gradient(97.76deg, #3fd5de 3.15%, #2deea8 76.87%) transparent;
  }
  &:active {
    border: 3px solid rgba(0, 0, 0, 0.3);
  }
`;

const NextButtonText = styled.div`
  user-select: none;
  border-radius: 30px;
  text-align: center;
  font-family: "NanumSquareNeoBold";
  font-size: 44px;
  color: #ffffff;
`;

/// case 1
const MemeDictionaryPracticeForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`

const MemeDictionaryPracticeFileFormArea = styled.div`
  margin-top: 20px;
  width: 770px;
  height: 500px;
  border-radius: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--gray03);
  margin-bottom: 150px;
`

const MemeDictionaryPracticeFileFormImage = styled.img`
  width: 440px;
  height: 366px;
`

const MemeDictionaryPracticeFileFormInput = styled.input`
  width: 980px;
  font-family: "NanumSquareNeoExtraBold";
  font-size: 32px;
  border: 1px solid var(--dark10);
  border-radius: 20px;
  padding: 15px;
`

const MemeDictionaryPracticeProblemInput = styled.input`
  width: 1100px;
  text-align: center;
  font-family: "NanumSquareNeoExtraBold";
  font-size: 32px;
  border: 1px solid var(--dark50);
  border-radius: 20px;
  padding: 15px;
  margin-bottom: 20px;
`

const MemeDictionaryPracticeOptionsInput = styled.input<{ isSelected: boolean }>`
  width: 980px;
  font-family: "NanumSquareNeoBold";
  font-size: 28px;
  border: 1px solid var(--dark10);
  border-radius: 20px;
  padding: 15px;
  background: ${props => props.isSelected ? 'var(--dark01)' : 'white'};
`

const MemeDictionaryPracticeOptionsWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
`

const MemeDictionaryPracticeOptionsCircle = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  font-family: "NanumSquareNeoHeavy";
  text-align: center;
  font-size: 28px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: 4px solid var(--dark01);
  margin-right: 10px;
  user-select: none;
  cursor: pointer;
`

//case2
const MemeDictionaryPracticeCompletedProblem = styled.div`
display: flex;
font-family: "NanumSquareNeoExtraBold";
font-size: 62px;
text-align: center;
justify-content: center;
align-items: center;
color : var(--emerald);
margin-bottom: 100px;

`

function MemeDictionaryPracticeRandomSolvingProcessBox() {
  const [selectedOption, setSelectedOption] = useState<number | null>(null);
  const [completed, setCompleted] = useState(true); 
  const [currentStep, setCurrentStep] = useState(1); 
  const handleNextButtononClick = () => {
  
  };
  
  useEffect(()=>{
    window.scrollTo(0, 0);
  },[currentStep])

  switch (currentStep) {
  case 1:
    return (
      <MemeDictionaryPracticeWraaper> 
        <MemeDictionaryPracticeForm>
          <MemeDictionaryPracticeFileFormArea>
          <MemeDictionaryPracticeFileFormImage src={posefileformbutton}/>
          </MemeDictionaryPracticeFileFormArea>
            
            <MemeDictionaryPracticeProblemInput placeholder="문제를 입력해주세요"/>

            <MemeDictionaryPracticeOptionsWrapper>
              <MemeDictionaryPracticeOptionsCircle onClick={() => setSelectedOption(1)}>1</MemeDictionaryPracticeOptionsCircle>
              <MemeDictionaryPracticeOptionsInput isSelected={selectedOption === 1} placeholder="첫 번째 선지를 입력해주세요"/>
            </MemeDictionaryPracticeOptionsWrapper>

            <MemeDictionaryPracticeOptionsWrapper>
              <MemeDictionaryPracticeOptionsCircle onClick={() => setSelectedOption(2)}>2</MemeDictionaryPracticeOptionsCircle>
              <MemeDictionaryPracticeOptionsInput isSelected={selectedOption === 2} placeholder="두 번째 선지를 입력해주세요"/>
            </MemeDictionaryPracticeOptionsWrapper>

            <MemeDictionaryPracticeOptionsWrapper>
              <MemeDictionaryPracticeOptionsCircle onClick={() => setSelectedOption(3)}>3</MemeDictionaryPracticeOptionsCircle>
              <MemeDictionaryPracticeOptionsInput isSelected={selectedOption === 3} placeholder="세 번째 선지를 입력해주세요"/>
            </MemeDictionaryPracticeOptionsWrapper>

            {completed ? (
              <NextButton onClick={()=> setCurrentStep(2)}>
                <NextButtonText>출제완료</NextButtonText>
              </NextButton>
            ) : (
              <LockedNextButton >
                <NextButtonText >출제완료</NextButtonText>
              </LockedNextButton>
            )}
        </MemeDictionaryPracticeForm>
      </MemeDictionaryPracticeWraaper>
    )
    case 2:
      return (
        <MemeDictionaryPracticeWraaper>
            <MemeDictionaryPracticeCompletedProblem>"하이퍼 우짤래미"</MemeDictionaryPracticeCompletedProblem>
                    <MemeDictionaryPracticeHeader>출제 완료!</MemeDictionaryPracticeHeader>
                    <MemeDictionaryPracticeText>출제한 문제는 나의 단어장에서 확인 가능합니다.</MemeDictionaryPracticeText>
        </MemeDictionaryPracticeWraaper>
      )
    default:
      break;
    }
}
export default MemeDictionaryPracticeRandomSolvingProcessBox;