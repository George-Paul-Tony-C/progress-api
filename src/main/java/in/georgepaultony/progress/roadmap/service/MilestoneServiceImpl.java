package in.georgepaultony.progress.roadmap.service;

import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.exception.ForbiddenException;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.roadmap.dto.CreateMilestoneRequest;
import in.georgepaultony.progress.roadmap.dto.MilestoneResponse;
import in.georgepaultony.progress.roadmap.dto.UpdateMilestoneRequest;
import in.georgepaultony.progress.roadmap.entity.Roadmap;
import in.georgepaultony.progress.roadmap.entity.RoadmapMilestone;
import in.georgepaultony.progress.roadmap.mapper.RoadmapMilestoneMapper;
import in.georgepaultony.progress.roadmap.repository.RoadmapMilestoneRepository;
import in.georgepaultony.progress.roadmap.repository.RoadmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl
        implements MilestoneService {

    private final RoadmapRepository roadmapRepository;

    private final RoadmapMilestoneRepository milestoneRepository;

    private final RoadmapMilestoneMapper milestoneMapper;

    private final CurrentUserProvider currentUserProvider;

    @Override
    public MilestoneResponse create(
            UUID roadmapId,
            CreateMilestoneRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        Roadmap roadmap =
                roadmapRepository.findById(roadmapId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Roadmap not found"
                                ));

        validateRoadmapOwnership(
                roadmap,
                userId
        );

        RoadmapMilestone milestone =
                milestoneMapper.toEntity(request);

        milestone.setRoadmap(roadmap);

        if (milestone.getCompleted() == null) {
            milestone.setCompleted(false);
        }

        milestone =
                milestoneRepository.save(milestone);

        return milestoneMapper.toResponse(
                milestone
        );
    }

    @Override
    public List<MilestoneResponse> getByRoadmap(
            UUID roadmapId
    ) {

        UUID userId =
                currentUserProvider.getId();

        Roadmap roadmap =
                roadmapRepository.findById(roadmapId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Roadmap not found"
                                ));

        validateRoadmapOwnership(
                roadmap,
                userId
        );

        return milestoneRepository
                .findByRoadmapIdAndIsDeletedFalseOrderByOrderNumberAsc(
                        roadmapId
                )
                .stream()
                .map(
                        milestoneMapper::toResponse
                )
                .toList();
    }

    @Override
    public MilestoneResponse update(
            UUID milestoneId,
            UpdateMilestoneRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        RoadmapMilestone milestone =
                milestoneRepository.findById(milestoneId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Milestone not found"
                                ));

        validateMilestoneOwnership(
                milestone,
                userId
        );

        if (request.title() != null) {
            milestone.setTitle(
                    request.title()
            );
        }

        if (request.description() != null) {
            milestone.setDescription(
                    request.description()
            );
        }

        if (request.completed() != null) {
            milestone.setCompleted(
                    request.completed()
            );
        }

        if (request.orderNumber() != null) {
            milestone.setOrderNumber(
                    request.orderNumber()
            );
        }

        milestone =
                milestoneRepository.save(
                        milestone
                );

        return milestoneMapper.toResponse(
                milestone
        );
    }

    @Override
    public void delete(
            UUID milestoneId
    ) {

        UUID userId =
                currentUserProvider.getId();

        RoadmapMilestone milestone =
                milestoneRepository.findById(milestoneId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Milestone not found"
                                ));

        validateMilestoneOwnership(
                milestone,
                userId
        );

        milestone.setIsDeleted(
                true
        );

        milestoneRepository.save(
                milestone
        );
    }

    private void validateRoadmapOwnership(
            Roadmap roadmap,
            UUID userId
    ) {

        if (Boolean.TRUE.equals(
                roadmap.getIsDeleted()
        )) {

            throw new ResourceNotFoundException(
                    "Roadmap not found"
            );
        }

        if (!roadmap.getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException(
                    "Access denied"
            );
        }
    }

    private void validateMilestoneOwnership(
            RoadmapMilestone milestone,
            UUID userId
    ) {

        if (Boolean.TRUE.equals(
                milestone.getIsDeleted()
        )) {

            throw new ResourceNotFoundException(
                    "Milestone not found"
            );
        }

        if (!milestone.getRoadmap()
                .getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException(
                    "Access denied"
            );
        }
    }
}