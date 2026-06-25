package in.georgepaultony.progress.analytics.controller;

import in.georgepaultony.progress.analytics.dto.DashboardResponse;
import in.georgepaultony.progress.analytics.service.AnalyticsService;
import in.georgepaultony.progress.common.dto.ApiResponse;
import in.georgepaultony.progress.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>>
    getDashboard() {

        return ResponseUtil.ok(
                "Dashboard fetched successfully",
                analyticsService.getDashboard()
        );
    }
}