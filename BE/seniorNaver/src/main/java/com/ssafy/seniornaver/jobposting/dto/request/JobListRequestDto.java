package com.ssafy.seniornaver.jobposting.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
public class JobListRequestDto {

    private List<item> item;

    @Getter
    @ToString
    public static class item {
        private String acptMthd;
        private String deadline;
        private String emplymShp;
        private String jobId;
        private String jobclsNm;
        private String recrtTitle;
        private String workPlcNm;
        private StringBuilder frDd;
        private StringBuilder toDd;
    }
}
