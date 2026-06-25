package in.georgepaultony.progress.goal.service;

import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.common.exception.ForbiddenException;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.goal.dto.CreateGoalRequest;
import in.georgepaultony.progress.goal.dto.GoalFilterRequest;
import in.georgepaultony.progress.goal.dto.GoalResponse;
import in.georgepaultony.progress.goal.dto.UpdateGoalRequest;
import in.georgepaultony.progress.goal.entity.Goal;
import in.georgepaultony.progress.goal.mapper.GoalMapper;
import in.georgepaultony.progress.goal.repository.GoalRepository;
import in.georgepaultony.progress.goal.specification.GoalSpecification;
import in.georgepaultony.progress.user.entity.User;
import in.georgepaultony.progress.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl
        implements GoalService {

    private final GoalRepository goalRepository;

    private final UserRepository userRepository;

    private final GoalMapper goalMapper;

    private final CurrentUserProvider currentUserProvider;

    @Override
    public GoalResponse create(
            CreateGoalRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));

        Goal goal =
                goalMapper.toEntity(request);

        goal.setUser(user);

        if (goal.getStatus() == null) {
            goal.setStatus(
                    in.georgepaultony.progress.goal.enums.GoalStatus.NOT_STARTED
            );
        }

        if (goal.getProgressPercentage() == null) {
            goal.setProgressPercentage(0);
        }

        goal =
                goalRepository.save(goal);

        return goalMapper.toResponse(goal);
    }

    @Override
    public GoalResponse getById(
            UUID goalId
    ) {

        UUID userId =
                currentUserProvider.getId();

        Goal goal =
                goalRepository.findById(goalId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Goal not found"
                                ));

        if (!goal.getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException(
                    "Access denied"
            );
        }

        if (Boolean.TRUE.equals(
                goal.getIsDeleted()
        )) {

            throw new ResourceNotFoundException(
                    "Goal not found"
            );
        }

        return goalMapper.toResponse(goal);
    }

    @Override
    public PagedResponse<GoalResponse> getAll(
            GoalFilterRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        Pageable pageable =
                PageRequest.of(
                        request.getPage(),
                        request.getSize()
                );

        Specification<Goal> specification =
                GoalSpecification.belongsToUser(userId)
                        .and(
                                GoalSpecification.notDeleted()
                        )
                        .and(
                                GoalSpecification.hasStatus(
                                        request.getStatus()
                                )
                        )
                        .and(
                                GoalSpecification.containsKeyword(
                                        request.getKeyword()
                                )
                        );

        Page<Goal> goals =
                goalRepository.findAll(
                        specification,
                        pageable
                );

        return new PagedResponse<>(

                goals.getContent()
                        .stream()
                        .map(goalMapper::toResponse)
                        .toList(),

                goals.getNumber(),
                goals.getSize(),
                goals.getTotalElements(),
                goals.getTotalPages(),
                goals.isFirst(),
                goals.isLast()
        );
    }

    @Override
    public GoalResponse update(
            UUID goalId,
            UpdateGoalRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        Goal goal =
                goalRepository.findById(goalId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Goal not found"
                                ));

        if (!goal.getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException(
                    "Access denied"
            );
        }

        if (request.title() != null) {
            goal.setTitle(
                    request.title()
            );
        }

        if (request.description() != null) {
            goal.setDescription(
                    request.description()
            );
        }

        if (request.targetDate() != null) {
            goal.setTargetDate(
                    request.targetDate()
            );
        }

        if (request.status() != null) {
            goal.setStatus(
                    request.status()
            );
        }

        if (request.progressPercentage() != null) {
            goal.setProgressPercentage(
                    request.progressPercentage()
            );
        }

        goal =
                goalRepository.save(goal);

        return goalMapper.toResponse(goal);
    }

    @Override
    public void delete(
            UUID goalId
    ) {

        UUID userId =
                currentUserProvider.getId();

        Goal goal =
                goalRepository.findById(goalId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Goal not found"
                                ));

        if (!goal.getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException(
                    "Access denied"
            );
        }

        goal.setIsDeleted(true);

        goalRepository.save(goal);
    }
}