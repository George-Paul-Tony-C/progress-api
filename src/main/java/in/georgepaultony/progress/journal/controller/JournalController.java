package in.georgepaultony.progress.journal.controller;

import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import in.georgepaultony.progress.journal.dto.CreateJournalRequest;
import in.georgepaultony.progress.journal.dto.JournalFilterRequest;
import in.georgepaultony.progress.journal.dto.JournalResponse;
import in.georgepaultony.progress.journal.dto.UpdateJournalRequest;
import in.georgepaultony.progress.journal.service.JournalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/journals")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @PostMapping
    public ResponseEntity<ApiResponse<JournalResponse>> create(
            @Valid @RequestBody CreateJournalRequest request) {

        return ResponseUtil.created(
                "Journal created successfully",
                journalService.create(request)
        );
    }

    @GetMapping("/{journalId}")
    public ResponseEntity<ApiResponse<JournalResponse>> getById(
            @PathVariable UUID journalId) {

        return ResponseUtil.ok(
                "Journal fetched successfully",
                journalService.getById(journalId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<JournalResponse>>> getAll(
            @ParameterObject JournalFilterRequest request) {

        return ResponseUtil.ok(
                "Journals fetched successfully",
                journalService.getAll(request)
        );
    }

    @PatchMapping("/{journalId}")
    public ResponseEntity<ApiResponse<JournalResponse>> update(
            @PathVariable UUID journalId,
            @RequestBody UpdateJournalRequest request) {

        return ResponseUtil.ok(
                "Journal updated successfully",
                journalService.update(journalId, request)
        );
    }

    @DeleteMapping("/{journalId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID journalId) {

        journalService.delete(journalId);

        return ResponseUtil.ok(
                "Journal deleted successfully",
                null
        );
    }
}