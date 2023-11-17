package com.ssafy.seniornaver.jobposting.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobDetailResponseDto {
    private String wantedTitle;
    private String acptMthdCd;
    private String age;
    private String ageLim;
    private String clerk;
    private String clerkContt;
    private String clltPrnnum;
    private StringBuilder frAcptDd;
    private StringBuilder toAcptDd;
    private String detCnts;
    private String etcItm;
    private String homepage;
    private String plDetAddr;
    private String plbizNm;

    public void updateAcptMthdCd(String acptMthdCd) {
        this.acptMthdCd = acptMthdCd;
    }
}
