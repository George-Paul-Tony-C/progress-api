package in.georgepaultony.progress.roadmap.service;

import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.common.exception.ForbiddenException;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.roadmap.dto.*;
import in.georgepaultony.progress.roadmap.entity.Roadmap;
import in.georgepaultony.progress.roadmap.enums.RoadmapStatus;
import in.georgepaultony.progress.roadmap.mapper.RoadmapMapper;
import in.georgepaultony.progress.roadmap.repository.RoadmapMilestoneRepository;
import in.georgepaultony.progress.roadmap.repository.RoadmapRepository;
import in.georgepaultony.progress.roadmap.specification.RoadmapSpecification;
import in.georgepaultony.progress.user.entity.User;
import in.georgepaultony.progress.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoadmapServiceImpl
        implements RoadmapService {

    private final RoadmapRepository roadmapRepository;

    private final RoadmapMilestoneRepository milestoneRepository;

    private final UserRepository userRepository;

    private final CurrentUserProvider currentUserProvider;

    private final RoadmapMapper roadmapMapper;

    @Override
    public RoadmapResponse create(
            CreateRoadmapRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));

        Roadmap roadmap =
                roadmapMapper.toEntity(request);

        roadmap.setUser(user);

        if (roadmap.getStatus() == null) {

            roadmap.setStatus(
                    RoadmapStatus.NOT_STARTED
            );
        }

        roadmap =
                roadmapRepository.save(roadmap);

        return mapToResponse(
                roadmap
        );
    }

    @Override
    public RoadmapResponse getById(
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

        validateOwnership(
                roadmap,
                userId
        );

        return mapToResponse(
                roadmap
        );
    }

    @Override
    public PagedResponse<RoadmapResponse> getAll(
            RoadmapFilterRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        Pageable pageable =
                PageRequest.of(
                        request.getPage(),
                        request.getSize()
                );

        Specification<Roadmap> specification =
                RoadmapSpecification.belongsToUser(userId)
                        .and(RoadmapSpecification.notDeleted())
                        .and(RoadmapSpecification.hasStatus(request.getStatus()))
                        .and(RoadmapSpecification.containsKeyword(request.getKeyword()));

        Page<Roadmap> roadmaps =
                roadmapRepository.findAll(
                        specification,
                        pageable
                );

        return new PagedResponse<>(

                roadmaps.getContent()
                        .stream()
                        .map(this::mapToResponse)
                        .toList(),

                roadmaps.getNumber(),
                roadmaps.getSize(),
                roadmaps.getTotalElements(),
                roadmaps.getTotalPages(),
                roadmaps.isFirst(),
                roadmaps.isLast()
        );
    }

    @Override
    public RoadmapResponse update(
            UUID roadmapId,
            UpdateRoadmapRequest request
    ) {

        UUID userId =
                currentUserProvider.getId();

        Roadmap roadmap =
                roadmapRepository.findById(roadmapId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Roadmap not found"
                                ));

        validateOwnership(
                roadmap,
                userId
        );

        if (request.title() != null) {
            roadmap.setTitle(request.title());
        }

        if (request.description() != null) {
            roadmap.setDescription(request.description());
        }

        if (request.targetDate() != null) {
            roadmap.setTargetDate(request.targetDate());
        }

        if (request.status() != null) {
            roadmap.setStatus(request.status());
        }

        roadmap =
                roadmapRepository.save(roadmap);

        return mapToResponse(
                roadmap
        );
    }

    @Override
    public void delete(
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

        validateOwnership(
                roadmap,
                userId
        );

        roadmap.setIsDeleted(true);

        roadmapRepository.save(
                roadmap
        );
    }

    private void validateOwnership(
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

    private RoadmapResponse mapToResponse(
            Roadmap roadmap
    ) {

        Integer totalMilestones =
                milestoneRepository
                        .countByRoadmapIdAndIsDeletedFalse(
                                roadmap.getId()
                        );

        Integer completedMilestones =
                milestoneRepository
                        .countByRoadmapIdAndCompletedTrueAndIsDeletedFalse(
                                roadmap.getId()
                        );

        return new RoadmapResponse(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                roadmap.getTargetDate(),
                roadmap.getStatus(),
                totalMilestones,
                completedMilestones,
                roadmap.getCreatedAt(),
                roadmap.getUpdatedAt()
        );
    }
}