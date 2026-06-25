package in.georgepaultony.progress.common.util;

import in.georgepaultony.progress.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    private ResponseUtil() {}

    public static <T> ResponseEntity<ApiResponse<T>> ok(String message , T data){
        return ResponseEntity.ok(ApiResponse.<T>builder().success(true).message(message).data(data).build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message , T data){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<T>builder().success(true).message(message).data(data).build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent(){
        return ResponseEntity.noContent().build();
    }

    // Handles 500 Internal Server Error
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.<T>builder().success(false).message(message).build());
    }
}
