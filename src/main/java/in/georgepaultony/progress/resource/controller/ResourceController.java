package in.georgepaultony.progress.resource.controller;

import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import in.georgepaultony.progress.resource.dto.CreateResourceRequest;
import in.georgepaultony.progress.resource.dto.ResourceResponse;
import in.georgepaultony.progress.resource.dto.UpdateResourceRequest;
import in.georgepaultony.progress.resource.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping("/api/v1/journals/{journalId}/resources")
    public ResponseEntity<ApiResponse<ResourceResponse>> create(
            @PathVariable UUID journalId,
            @Valid @RequestBody CreateResourceRequest request
    ) {

        return ResponseUtil.created(
                "Resource created successfully",
                resourceService.create(
                        journalId,
                        request
                )
        );
    }

    @GetMapping("/api/v1/journals/{journalId}/resources")
    public ResponseEntity<ApiResponse<List<ResourceResponse>>> getByJournal(
            @PathVariable UUID journalId
    ) {

        return ResponseUtil.ok(
                "Resources fetched successfully",
                resourceService.getByJournal(
                        journalId
                )
        );
    }

    @PatchMapping("/api/v1/resources/{resourceId}")
    public ResponseEntity<ApiResponse<ResourceResponse>> update(
            @PathVariable UUID resourceId,
            @RequestBody UpdateResourceRequest request
    ) {

        return ResponseUtil.ok(
                "Resource updated successfully",
                resourceService.update(
                        resourceId,
                        request
                )
        );
    }

    @DeleteMapping("/api/v1/resources/{resourceId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID resourceId
    ) {

        resourceService.delete(resourceId);

        return ResponseUtil.ok(
                "Resource deleted successfully",
                null
        );
    }
}