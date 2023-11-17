package com.ssafy.seniornaver.auth.dto.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateProfilePictureDto {
	private String profileImageUrl;

	@Builder
	public UpdateProfilePictureDto(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
