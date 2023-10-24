package com.ssafy.seniornaver.auth.service;

import com.ssafy.seniornaver.auth.dto.*;
import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.auth.entity.enumType.AuthProvider;
import com.ssafy.seniornaver.auth.entity.enumType.Role;
import com.ssafy.seniornaver.auth.jwt.JwtProvider;
import com.ssafy.seniornaver.auth.repository.MemberRepository;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public String signUp(SignUpRequestDto signUpRequestDto) {
		if (memberRepository.findByMemberId(signUpRequestDto.getMemberId()).isPresent()) {
			throw new BadRequestException(ErrorCode.ALREADY_REGISTERED_USER_ID);
		}

		if (signUpRequestDto.getNickname().toLowerCase().contains("admin") ||
			signUpRequestDto.getMemberId().toLowerCase().contains("admin")) {
			throw new BadRequestException(ErrorCode.INVALID_ADMIN);
		}


		Member member = Member.builder()
			.memberId(signUpRequestDto.getMemberId())
			.name(signUpRequestDto.getName())
			.mobile(signUpRequestDto.getMobile())
			.email(signUpRequestDto.getEmail())
			.password(signUpRequestDto.getPassword())
			.nickname(signUpRequestDto.getNickname())
			.region(signUpRequestDto.getRegion())
			.role(Role.USER)
			.authProvider(AuthProvider.EMPTY)
			.build();

		member.passwordEncode(bCryptPasswordEncoder);
		return memberRepository.save(member).getMemberId();
	}

	@Transactional
	public LogInResponseDto logIn(LogInRequestDto logInRequestDto)  {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (!memberRepository.existsByMemberId(logInRequestDto.getMemberId())) {
			throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
		}
		Member member = memberRepository.findByMemberId(logInRequestDto.getMemberId()).get();
		if (!bCryptPasswordEncoder.matches(logInRequestDto.getPassword(), member.getPassword())) {
			throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_PASSWORD);
		}

		// 토큰 발급
		TokenDto accessTokenDto = jwtProvider.createAccessToken(logInRequestDto.getMemberId(), member.getAuthProvider());
		TokenDto refreshTokenDto = jwtProvider.createRefreshToken(logInRequestDto.getMemberId(), member.getAuthProvider());

		member.updateRefreshToken(refreshTokenDto.getToken(), refreshTokenDto.getTokenExpirationTime());

		return LogInResponseDto.builder()
			.memberId(member.getMemberId())
			.nickname(member.getNickname())
			.email(member.getEmail())
			.mobile(member.getMobile())
			.accessToken(accessTokenDto.getToken())
			.refreshToken(refreshTokenDto.getToken())
			.refreshTokenExpirationTime(refreshTokenDto.getTokenExpirationTime())
			.build();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Scheduled(cron = "0 0 0 * * ?")
	public void resetIsChecked() {
		List<Member> allMembers = memberRepository.findAll();
		for (Member member : allMembers) {
			System.out.println(member.getMemberId());
		}
		memberRepository.saveAllAndFlush(allMembers);
	}


	/*
	 ** 로그아웃 -> DB에 저장된 리프레쉬 토큰 최신화
	 */
	public void logOut(LogOutRequestDto logOutRequestDto) {
		Member member = memberRepository.findByRefreshToken(logOutRequestDto.getRefreshToken()).get();
		member.expireRefreshToken(new Date());
	}

	@Transactional(readOnly = true)
	public TokenDto getAccessToken(String refreshToken) {
		String userId = (String)jwtProvider.get(refreshToken).get("userId");
		String provider = (String)jwtProvider.get(refreshToken).get("provider");
		System.out.println("in getAccessToken " + userId + "  " + provider);

		if (!memberRepository.existsByMemberIdAndAuthProvider(userId, AuthProvider.findByCode(provider.toLowerCase()))) {
			throw new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID);
		} else if (jwtProvider.isExpiration(refreshToken)) {
			throw new BadRequestException(ErrorCode.TOKEN_EXPIRED);
		}

		return jwtProvider.createAccessToken(userId, AuthProvider.findByCode(provider));
	}

	public Boolean validUserId(String userId) {
		Optional<Member> user = memberRepository.findByMemberId(userId);
		return getValid(user, userId);
	}

	public Boolean validNickname(String nickname) {
		Optional<Member> user = memberRepository.findByNickname(nickname);
		return getValid(user, nickname);
	}

	private static Boolean getValid(Optional<Member> user, String userinfo) {
		if (userinfo.toLowerCase().contains("admin"))
			return false;
		return !user.isPresent();
	}


}
