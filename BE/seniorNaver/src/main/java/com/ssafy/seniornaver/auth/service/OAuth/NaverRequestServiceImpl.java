package com.ssafy.seniornaver.auth.service.OAuth;

import com.ssafy.seniornaver.auth.dto.OAuth.NaverMemberInfo;
import com.ssafy.seniornaver.auth.dto.OAuth.OAuthSignInResponse;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenRequest;
import com.ssafy.seniornaver.auth.dto.OAuth.TokenResponse;
import com.ssafy.seniornaver.auth.dto.TokenDto;
import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class NaverRequestServiceImpl implements RequestService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String USER_INFO_URI;

    @Override
    public OAuthSignInResponse redirect(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = getToken(tokenRequest);
        NaverMemberInfo naverMemberInfo = getUserInfo(tokenResponse.getAccessToken());

        TokenDto accessTokenDto = jwtProvider.createAccessToken(
                naverMemberInfo.getResponse().getId(), AuthProvider.NAVER);
        TokenDto refreshTokenDto = jwtProvider.createRefreshToken(
                naverMemberInfo.getResponse().getId(), AuthProvider.NAVER);

        OAuthSignInResponse oAuthSignInResponse = OAuthSignInResponse.builder()
                .authProvider(AuthProvider.NAVER)
                .memberId(naverMemberInfo.getResponse().getId())
                .nickname(naverMemberInfo.getResponse().getName())
                .email(naverMemberInfo.getResponse().getEmail())
                .mobile(naverMemberInfo.getResponse().getMobile())
                .accessToken(accessTokenDto.getToken())
                .refreshToken(refreshTokenDto.getToken())
                .refreshTokenExpirationTime(refreshTokenDto.getTokenExpirationTime())
                .build();

        Member memberEntity;
        if(!memberRepository.existsById(naverMemberInfo.getResponse().getId())){
            memberEntity = oAuthSignInResponse.toEntity();
        } else {
            memberEntity = memberRepository.findByMemberId(String.valueOf(naverMemberInfo.getResponse().getId()))
                    .orElseThrow(() -> new IllegalStateException("유저 아이디가 없습니다."));
            memberEntity.updateRefreshToken(refreshTokenDto.getToken(), refreshTokenDto.getTokenExpirationTime());
        }
        memberRepository.save(memberEntity);
        return oAuthSignInResponse;

    }

    @Override
    public TokenResponse getToken(TokenRequest tokenRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GRANT_TYPE);
        formData.add("client_id", CLIENT_ID);
        formData.add("client_secret", CLIENT_SECRET);
        formData.add("code", tokenRequest.getCode());
        formData.add("state", tokenRequest.getState());

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    @Override
    public NaverMemberInfo getUserInfo(String accessToken) {
        return webClient.mutate()
                .baseUrl(USER_INFO_URI)
                .build()
                .get()
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(NaverMemberInfo.class)
                .block();
    }

    @Override
    public TokenResponse getRefreshToken(String provider, String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", CLIENT_ID);
        formData.add("refresh_token", refreshToken);

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
