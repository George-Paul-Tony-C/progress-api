package in.georgepaultony.progress.roadmap.controller;

import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import in.georgepaultony.progress.roadmap.dto.CreateRoadmapRequest;
import in.georgepaultony.progress.roadmap.dto.RoadmapFilterRequest;
import in.georgepaultony.progress.roadmap.dto.RoadmapResponse;
import in.georgepaultony.progress.roadmap.dto.UpdateRoadmapRequest;
import in.georgepaultony.progress.roadmap.service.RoadmapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roadmaps")
@RequiredArgsConstructor
public class RoadmapController {

    private final RoadmapService roadmapService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoadmapResponse>>
    create(
            @Valid
            @RequestBody
            CreateRoadmapRequest request
    ) {

        return ResponseUtil.created(
                "Roadmap created successfully",
                roadmapService.create(request)
        );
    }

    @GetMapping("/{roadmapId}")
    public ResponseEntity<ApiResponse<RoadmapResponse>>
    getById(
            @PathVariable UUID roadmapId
    ) {

        return ResponseUtil.ok(
                "Roadmap fetched successfully",
                roadmapService.getById(roadmapId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<RoadmapResponse>>>
    getAll(
            @ParameterObject
            RoadmapFilterRequest request
    ) {

        return ResponseUtil.ok(
                "Roadmaps fetched successfully",
                roadmapService.getAll(request)
        );
    }

    @PatchMapping("/{roadmapId}")
    public ResponseEntity<ApiResponse<RoadmapResponse>>
    update(
            @PathVariable UUID roadmapId,
            @RequestBody UpdateRoadmapRequest request
    ) {

        return ResponseUtil.ok(
                "Roadmap updated successfully",
                roadmapService.update(
                        roadmapId,
                        request
                )
        );
    }

    @DeleteMapping("/{roadmapId}")
    public ResponseEntity<ApiResponse<Void>>
    delete(
            @PathVariable UUID roadmapId
    ) {

        roadmapService.delete(
                roadmapId
        );

        return ResponseUtil.ok(
                "Roadmap deleted successfully",
                null
        );
    }
}