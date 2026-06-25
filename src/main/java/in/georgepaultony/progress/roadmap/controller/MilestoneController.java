package in.georgepaultony.progress.roadmap.controller;

import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import in.georgepaultony.progress.roadmap.dto.CreateMilestoneRequest;
import in.georgepaultony.progress.roadmap.dto.MilestoneResponse;
import in.georgepaultony.progress.roadmap.dto.UpdateMilestoneRequest;
import in.georgepaultony.progress.roadmap.service.MilestoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping(
            "/api/v1/roadmaps/{roadmapId}/milestones"
    )
    public ResponseEntity<ApiResponse<MilestoneResponse>>
    create(
            @PathVariable UUID roadmapId,
            @Valid
            @RequestBody
            CreateMilestoneRequest request
    ) {

        return ResponseUtil.created(
                "Milestone created successfully",
                milestoneService.create(
                        roadmapId,
                        request
                )
        );
    }

    @GetMapping(
            "/api/v1/roadmaps/{roadmapId}/milestones"
    )
    public ResponseEntity<ApiResponse<List<MilestoneResponse>>>
    getByRoadmap(
            @PathVariable UUID roadmapId
    ) {

        return ResponseUtil.ok(
                "Milestones fetched successfully",
                milestoneService.getByRoadmap(
                        roadmapId
                )
        );
    }

    @PatchMapping(
            "/api/v1/milestones/{milestoneId}"
    )
    public ResponseEntity<ApiResponse<MilestoneResponse>>
    update(
            @PathVariable UUID milestoneId,
            @RequestBody UpdateMilestoneRequest request
    ) {

        return ResponseUtil.ok(
                "Milestone updated successfully",
                milestoneService.update(
                        milestoneId,
                        request
                )
        );
    }

    @PatchMapping(
            "/api/v1/milestones/{milestoneId}/move-up"
    )
    public ResponseEntity<ApiResponse<MilestoneResponse>>
    moveUp(
            @PathVariable UUID milestoneId
    ) {

        return ResponseUtil.ok(
                "Milestone moved up successfully",
                milestoneService.moveUp(
                        milestoneId
                )
        );

    }

    @PatchMapping(
            "/api/v1/milestones/{milestoneId}/move-down"
    )
    public ResponseEntity<ApiResponse<MilestoneResponse>>
    moveDown(
            @PathVariable UUID milestoneId
    ) {

        return ResponseUtil.ok(
                "Milestone moved down successfully",
                milestoneService.moveDown(
                        milestoneId
                )
        );

    }

    @DeleteMapping(
            "/api/v1/milestones/{milestoneId}"
    )
    public ResponseEntity<ApiResponse<Void>>
    delete(
            @PathVariable UUID milestoneId
    ) {

        milestoneService.delete(
                milestoneId
        );

        return ResponseUtil.ok(
                "Milestone deleted successfully",
                null
        );
    }
}