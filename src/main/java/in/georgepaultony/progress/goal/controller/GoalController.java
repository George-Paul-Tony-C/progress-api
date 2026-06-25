package in.georgepaultony.progress.goal.controller;

import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.dto.PagedResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import in.georgepaultony.progress.goal.dto.CreateGoalRequest;
import in.georgepaultony.progress.goal.dto.GoalFilterRequest;
import in.georgepaultony.progress.goal.dto.GoalResponse;
import in.georgepaultony.progress.goal.dto.UpdateGoalRequest;
import in.georgepaultony.progress.goal.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<ApiResponse<GoalResponse>>
    create(
            @Valid
            @RequestBody
            CreateGoalRequest request
    ) {

        return ResponseUtil.created(
                "Goal created successfully",
                goalService.create(request)
        );
    }

    @GetMapping("/{goalId}")
    public ResponseEntity<ApiResponse<GoalResponse>>
    getById(
            @PathVariable UUID goalId
    ) {

        return ResponseUtil.ok(
                "Goal fetched successfully",
                goalService.getById(goalId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<GoalResponse>>>
    getAll(
            @ParameterObject
            GoalFilterRequest request
    ) {

        return ResponseUtil.ok(
                "Goals fetched successfully",
                goalService.getAll(request)
        );
    }

    @PatchMapping("/{goalId}")
    public ResponseEntity<ApiResponse<GoalResponse>>
    update(
            @PathVariable UUID goalId,
            @RequestBody UpdateGoalRequest request
    ) {

        return ResponseUtil.ok(
                "Goal updated successfully",
                goalService.update(
                        goalId,
                        request
                )
        );
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<ApiResponse<Void>>
    delete(
            @PathVariable UUID goalId
    ) {

        goalService.delete(goalId);

        return ResponseUtil.ok(
                "Goal deleted successfully",
                null
        );
    }
}