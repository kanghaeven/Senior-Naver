package com.ssafy.seniornaver.mz.service;

import com.ssafy.seniornaver.auth.entity.Member;
import com.ssafy.seniornaver.error.code.ErrorCode;
import com.ssafy.seniornaver.error.exception.BadRequestException;
import com.ssafy.seniornaver.mz.dto.request.ProblemCreateRequestDto;
import com.ssafy.seniornaver.mz.dto.request.ProblemEvaluationRequestDto;
import com.ssafy.seniornaver.mz.dto.request.ProblemListRequestDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemDetailResponseDto;
import com.ssafy.seniornaver.mz.dto.response.ProblemListResponseDto;
import com.ssafy.seniornaver.mz.dto.response.RandomProblemResponseDto;
import com.ssafy.seniornaver.mz.dto.response.TotalEvaluationResponseDto;
import com.ssafy.seniornaver.mz.entity.*;
import com.ssafy.seniornaver.mz.repository.*;
import com.ssafy.seniornaver.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SituationProblemServiceImpl implements SituationProblemService{

    private final DictionaryRepository dictionaryRepository;
    private final SituationRepository situationRepository;
    private final CompleteProblemRepository completeProblemRepository;
    private final VocabularyListRepository vocabularyListRepository;
    private final SaveProblemRepository saveProblemRepository;
    private final ChoiceRepository choiceRepository;
    private final EvaluationRepository evaluationRepository;
    private final MakeProblemRepository makeProblemRepository;

    private final TagRepository tagRepository;
    private final TagService tagService;

    private final S3Uploader s3Uploader;

    @Override
    public boolean wordCheck(String word, int year) {
        Dictionary dict = dictionaryRepository.findByWord(word).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_WORD);
        });

        return dict.getUseYear() >= year && dict.getUseYear() < year + 10;
    }

    @Override
    @Transactional
    public SituationProblem createProblem(ProblemCreateRequestDto problemCreateRequestDto,
                                          MultipartFile multipartFile,
                                          Member member) throws IOException {
        if (situationRepository.existsByTitle(problemCreateRequestDto.getTitle())){
            throw new BadRequestException(ErrorCode.ALREADY_REGISTERED_PROBLEM);
        }

        String url = s3Uploader.uploadFiles(multipartFile, "SituationProblem");

        SituationProblem situationProblem =  situationRepository.saveAndFlush(SituationProblem.builder()
                        .title(problemCreateRequestDto.getTitle())
                        .review(problemCreateRequestDto.getReview())
                        .problemExplanation(problemCreateRequestDto.getProblemExplanation())
                        .answer(problemCreateRequestDto.getAnswer())
                        .makeMember(member.getMemberId())
                        .image(url)
                        .useYear(problemCreateRequestDto.getUseYear())
                .build());

        for (int i = 0; i < problemCreateRequestDto.getChoices().size(); i++) {
            Choice choice = Choice.builder()
                    .content(problemCreateRequestDto.getChoices().get(i).getContent())
                    .choiceNum(problemCreateRequestDto.getChoices().get(i).getChoiceNum())
                    .situationProblem(situationProblem)
                    .build();

            choiceRepository.saveAndFlush(choice);
            situationProblem.getChoiceList().add(choice);
        }

        VocabularyList vocabularyList = vocabularyListRepository.findByVocaId(member.getVocaId()).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_WORD);
        });

        vocabularyList.getMakeProblems().add(makeProblemRepository.save(MakeProblem.builder()
                        .problemId(situationProblem)
                        .vocaId(vocabularyList)
                .build()));

        return situationProblem;
    }

    @Override
    public void relTagToProblem(SituationProblem situationProblem, ProblemCreateRequestDto problemCreateRequestDto) {
        // 태그 생성, 이미 있다면 return 하여 기존의 태그에 추가
        problemCreateRequestDto.getTags().add(situationProblem.getTitle());

        for (int i = 0; i < problemCreateRequestDto.getTags().size(); i++) {
            log.info("크기 : {}", problemCreateRequestDto.getTags().size());
            tagService.createTag(problemCreateRequestDto.getTags().get(i));
            tagService.relationProblemTag(situationProblem,
                    tagRepository.findByTag(problemCreateRequestDto.getTags().get(i)).get());
        }
    }

    @Override
    public List<ProblemListResponseDto> getMemberProblemList(ProblemListRequestDto problemListRequestDto, Member member) {
        VocabularyList vocabularyList = vocabularyListRepository.findByVocaId(member.getVocaId()).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_VOCA_LIST);
        });

        Pageable pageable = PageRequest.of(problemListRequestDto.getPage(), 5, Sort.by("title").ascending());

        // 키워드 검색시
        if (problemListRequestDto.getKeyword() != null) {
            List<ProblemListResponseDto> problems = situationRepository.findAllByTitleContaining(problemListRequestDto.getKeyword(), pageable).stream()
                    .map(problem -> ProblemListResponseDto.builder()
                            .problemId(problem.getProblemId())
                            .title(problem.getTitle())
                            .problemExplanation(problem.getProblemExplanation())
                            .save(saveProblemRepository.findAllByVocaId(vocabularyList).stream()
                                    .anyMatch(saveProblem -> saveProblem.getProblemId().getProblemId() == problem.getProblemId()))
                            .useYear(problem.getUseYear())
                            .complete(completeProblemRepository.findAllByVocaId(vocabularyList.getVocaId()).stream()
                                    .anyMatch(completeProblem -> completeProblem.getProblemId().getProblemId() == problem.getProblemId()))
                            .build())
                    .collect(Collectors.toList());

            return problems;

        } else {
            List<ProblemListResponseDto> problems = situationRepository.findAll(pageable).stream()
                    .map(problem -> ProblemListResponseDto.builder()
                            .problemId(problem.getProblemId())
                            .title(problem.getTitle())
                            .problemExplanation(problem.getProblemExplanation())
                            .save(saveProblemRepository.findAllByVocaId(vocabularyList).stream()
                                    .anyMatch(saveProblem -> saveProblem.getProblemId().getProblemId() == problem.getProblemId()))
                            .useYear(problem.getUseYear())
                            .complete(completeProblemRepository.findAllByVocaId(vocabularyList.getVocaId()).stream()
                                    .anyMatch(completeProblem -> completeProblem.getProblemId().getProblemId() == problem.getProblemId()))
                            .build()).collect(Collectors.toList());

            return problems;
        }
    }

    @Override
    public List<ProblemListResponseDto> getProblemList(ProblemListRequestDto problemListRequestDto) {
        Pageable pageable = PageRequest.of(problemListRequestDto.getPage(), 5, Sort.by("title").ascending());

        // 키워드 검색시
        if (problemListRequestDto.getKeyword() != null) {
            List<ProblemListResponseDto> problems = situationRepository.findAllByTitleContaining(problemListRequestDto.getKeyword(), pageable).stream()
                    .map(problem -> ProblemListResponseDto.builder()
                            .problemId(problem.getProblemId())
                            .title(problem.getTitle())
                            .problemExplanation(problem.getProblemExplanation())
                            .save(false)
                            .useYear(problem.getUseYear())
                            .complete(false)
                            .build())
                    .collect(Collectors.toList());

            return problems;

        } else {
            List<ProblemListResponseDto> problems = situationRepository.findAll(pageable).stream()
                    .map(problem -> ProblemListResponseDto.builder()
                            .problemId(problem.getProblemId())
                            .title(problem.getTitle())
                            .problemExplanation(problem.getProblemExplanation())
                            .save(false)
                            .useYear(problem.getUseYear())
                            .complete(false)
                            .build()).collect(Collectors.toList());

            return problems;
        }
    }

    @Override
    public ProblemDetailResponseDto getProblemDetail(Long problemId) {
        SituationProblem situationProblem = situationRepository.findById(problemId).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_PROBLEM);
        });

        List<Choice> choices = choiceRepository.findAllBySituationProblem(situationProblem);

        return ProblemDetailResponseDto.builder()
                .problemId(situationProblem.getProblemId())
                .title(situationProblem.getTitle())
                .answer(situationProblem.getAnswer())
                .problemExplanation(situationProblem.getProblemExplanation())
                .useYear(situationProblem.getUseYear())
                .image(situationProblem.getImage())
                .review(situationProblem.getReview())
                .answer(situationProblem.getAnswer())
                .choices(choices)
                .build();
    }

    @Override
    public RandomProblemResponseDto getRandomProblem(int year) {
        return RandomProblemResponseDto.builder()
                .problemList(situationRepository.findCustom(year).stream()
                        .map(problem -> problem.getProblemId())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void saveProblem(Long id, Member member) {
        VocabularyList vocabularyList = vocabularyListRepository.findByVocaId(member.getVocaId()).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_VOCA_LIST);
        });

        vocabularyList.getSaveProblems().add(saveProblemRepository.save(SaveProblem.builder()
                .problemId(situationRepository.findById(id).orElseThrow(() -> {
                    throw new BadRequestException(ErrorCode.NOT_EXIST_PROBLEM);
                }))
                .vocaId(vocabularyList)
                .build()));
    }

    @Override
    public void cancelProblem(Long id, Member member) {
        VocabularyList vocabularyList = vocabularyListRepository.findByVocaId(member.getVocaId()).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_VOCA_LIST);
        });

        SituationProblem problem = situationRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_PROBLEM);
        });

        saveProblemRepository.delete(saveProblemRepository.findByProblemIdAndVocaId(problem, vocabularyList).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_PROBLEM_IN_VOCA_LIST);
        }));
    }

    @Override
    public void deleteProblem(Long id, Member member) {
        if (member.getMemberId() != "test1234") {
            throw new BadRequestException(ErrorCode.DONT_AUTHENTICATION_ROLE);
        }

        situationRepository.delete(situationRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_PROBLEM);
        }));
    }

    @Override
    @Transactional
    public void problemEvaluation(ProblemEvaluationRequestDto problemEvaluationRequestDto, Member member) {
        VocabularyList vocabularyList = vocabularyListRepository.findByVocaId(member.getVocaId()).orElseThrow(() -> {
           throw new BadRequestException(ErrorCode.NOT_EXIST_VOCA_LIST);
        });

        SituationProblem situationProblem = situationRepository.findByProblemIdAndTitle(problemEvaluationRequestDto.getProblemId(),
                problemEvaluationRequestDto.getTitle()).orElseThrow(() -> {
                    throw new BadRequestException(ErrorCode.NOT_EXIST_PROBLEM);
        });

        if (problemEvaluationRequestDto.getChoice() == 0 || problemEvaluationRequestDto.getAnswer() == 0) {
            throw new BadRequestException(ErrorCode.NOT_EXIST_ANSWER);
        }

        if (problemEvaluationRequestDto.getAnswer() == problemEvaluationRequestDto.getChoice()) {
            CompleteProblem completeProblem = completeProblemRepository.save(CompleteProblem.builder()
                    .problemId(situationProblem)
                    .vocaId(vocabularyList)
                    .build());
            vocabularyList.getCompleteProblems().add(completeProblem);
            situationProblem.getCompleteVocaList().add(completeProblem);
        }

        vocabularyList.getEvaluationResults().add(evaluationRepository.saveAndFlush(EvaluationResult.builder()
                        .problemId(problemEvaluationRequestDto.getProblemId())
                        .vocabularyList(vocabularyList)
                        .title(problemEvaluationRequestDto.getTitle())
                        .choice(problemEvaluationRequestDto.getChoice())
                        .answer(problemEvaluationRequestDto.getAnswer() == problemEvaluationRequestDto.getChoice())
                .build()));
    }

    @Override
    public TotalEvaluationResponseDto totalEvaluation(Member member) {
        VocabularyList vocabularyList = vocabularyListRepository.findByVocaId(member.getVocaId()).orElseThrow(() -> {
            throw new BadRequestException(ErrorCode.NOT_EXIST_VOCA_LIST);
        });

        return TotalEvaluationResponseDto.builder()
                .problemList(evaluationRepository.findAllByVocaId(vocabularyList).stream()
                        .sorted(Comparator.comparing(EvaluationResult::getResultId).reversed())
                        .limit(5)
                        .map(evaluationResult -> TotalEvaluationResponseDto.Problem.builder()
                                .id(evaluationResult.getProblemId())
                                .title(evaluationResult.getTitle())
                                .choice(evaluationResult.getChoice())
                                .answer(evaluationResult.isAnswer())
                                .build()).collect(Collectors.toList()))
                .build();
    }
}