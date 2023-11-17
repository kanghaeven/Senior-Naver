package com.ssafy.seniornaver.auth.service.OAuth;


import com.ssafy.seniornaver.auth.dto.OAuth.OAuthSignInResponse;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenRequest;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenResponse;
import com.ssafy.seniornaver.auth.dto.TokenDto;
import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final NaverRequestServiceImpl naverRequestServiceImpl;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public OAuthSignInResponse redirect(TokenRequest tokenRequest){
        if(AuthProvider.NAVER.getAuthProvider().equals(tokenRequest.getRegistrationId())){
            return naverRequestServiceImpl.redirect(tokenRequest);
        }
        throw new BadRequestException(ErrorCode.NOT_EXISTS_PROVIDER);
    }

    public OAuthSignInResponse refreshToken(TokenRequest tokenRequest){
        String memberId = (String) jwtProvider.get(tokenRequest.getRefreshToken()).get("memberId");
        String provider = (String) jwtProvider.get(tokenRequest.getRefreshToken()).get("provider");
        String oldRefreshToken = (String) jwtProvider.get(tokenRequest.getRefreshToken()).get("refreshToken");

        if(!memberRepository.existsByMemberIdAndAuthProvider(memberId, AuthProvider.findByCode(provider.toLowerCase()))){
            throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
        }

        TokenResponse tokenResponse = null;
        if(AuthProvider.NAVER.getAuthProvider().equals(provider.toLowerCase())){
            tokenResponse = naverRequestServiceImpl.getRefreshToken(provider, oldRefreshToken);
        }
        // access 토큰 생성
        TokenDto accessTokenDto = jwtProvider.createAccessToken(
                memberId, AuthProvider.findByCode(provider.toLowerCase()));

        return OAuthSignInResponse.builder()
                .authProvider(AuthProvider.findByCode(provider.toLowerCase()))
                .accessToken(accessTokenDto.getAccessToken())
                .refreshToken(null)
                .build();
    }
}
