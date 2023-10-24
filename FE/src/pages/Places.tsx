import { Container as MapDiv, NaverMap, Marker, useNavermaps } from "react-naver-maps";
import { styled } from "styled-components";
import useGeolocation from "../hooks/useGeolocation";
import { useState } from "react";

const screenWidth = window.screen.width;
const screenHeight = window.screen.height;

const SearchWrapper = styled.div`
  width: 100%;
`;
const SearchButton = styled.button``;

const SearchBar = styled.input``;

const ContentsWrapper = styled.div`
  width: 100%;
  background-color: pink;
`;
const Text = styled.p`
  color: black;
`;

const CategoryButtonWrapper = styled.div`
  width: 100%;
`;
const CategoryButton = styled.button``;
const PlacesWrapper = styled.div`
  background-color: blue;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const DrawableWrapper = styled.div<{ screenHeight: number; screenWidth: number; isShow: boolean }>`
  top: 0px;
  height: 100vh;
  width: 100%;
  transform: ${props => (props.isShow ? "translateX(0%)" : "translateX(-100%)")};
  transition: 0.5s;
  box-shadow:
    rgba(0, 0, 0, 0.2) 0px 0px 5px 0px,
    rgba(0, 0, 0, 0.1) 5px 0px 15px 0px;
`;
const Drawable = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: #ffffff;
  color: white;
`;
const DrawableButton = styled.button`
  position: absolute;
  top: 50%;
  left: 100%;
  transform: translateY(-50%);
  z-index: 10;
  overflow: hidden;
  display: inline-block;
  font-size: 20px;
  color: black;
  line-height: 1px;
  vertical-align: top;
  background-position: -339px -50px;
  width: fit-content;
  height: 49px;
  -webkit-mask-image: none;
`;
function Places() {
  const navermaps = useNavermaps();
  const location = useGeolocation();

  const [showDrawable, setShowDrawable] = useState(true);
  const handleDrawable = () => {
    setShowDrawable(!showDrawable);
  };

  return (
    <>
      <MapDiv
        style={{
          position: "absolute",
          top: 0,
          left: 0,
          width: "100%",
          height: "100%",
          overflow: "hidden",
        }}
      >
        {location.loaded && (
          <NaverMap
            defaultCenter={
              new navermaps.LatLng(location.coordinates!.lat, location.coordinates!.lng)
            }
            defaultZoom={18}
          >
            <Marker
              position={new navermaps.LatLng(location.coordinates!.lat, location.coordinates!.lng)}
            />
          </NaverMap>
        )}
      </MapDiv>
      <DrawableWrapper
        screenWidth={screenWidth / 3}
        screenHeight={screenHeight}
        isShow={showDrawable}
      >
        <Drawable>
          <SearchWrapper>
            <SearchBar />
            <SearchButton>검색</SearchButton>
          </SearchWrapper>
          <ContentsWrapper>
            <Text>이순자 님을 위한 추천 스팟이에요</Text>
            <CategoryButtonWrapper>
              <CategoryButton>맛집</CategoryButton>
              <CategoryButton>공연</CategoryButton>
              <CategoryButton>휴양지</CategoryButton>
            </CategoryButtonWrapper>
            <PlacesWrapper>
              검색한 결과 또는 카테고리 선택한 추천 장소가 렌더링될 공간
            </PlacesWrapper>
          </ContentsWrapper>
        </Drawable>
        <DrawableButton onClick={handleDrawable}>{showDrawable ? "<" : ">"}</DrawableButton>
      </DrawableWrapper>
    </>
  );
}

export default Places;
