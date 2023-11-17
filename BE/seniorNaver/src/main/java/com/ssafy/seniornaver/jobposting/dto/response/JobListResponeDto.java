package com.ssafy.seniornaver.jobposting.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class JobListResponeDto {

    private int page;
    private long totalPage;
    private List<Item> items;

    @Getter
    public static class Item {
        private String acceptMethod;
        private String deadline;
        private String employShape;
        private String jobId;
        private String jobClass;
        private String title;
        private String workPlace;
        private String thumbnail;
        private LocalDate startDate;
        private LocalDate endDate;

        @Builder
        public Item(String acceptMethod, String deadline, String employShape, String jobId, String jobClass,
                    String title, String workPlace, LocalDate startDate, LocalDate endDate, String thumbnail) {

            this.acceptMethod = acceptMethod;
            this.deadline = deadline;
            this.employShape = employShape;
            this.jobId = jobId;
            this.jobClass = jobClass;
            this.title = title;
            this.workPlace = workPlace;
            this.startDate = startDate;
            this.endDate = endDate;
            this.thumbnail = thumbnail;
        }
    }

    @Builder
    public JobListResponeDto(int page, int totalPage, List<Item> items) {
        this.page = page;
        this.totalPage = totalPage;
        this.items = items;
    }

}
