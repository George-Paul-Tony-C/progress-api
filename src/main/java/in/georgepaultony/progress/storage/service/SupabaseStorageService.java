package in.georgepaultony.progress.storage.service;

import in.georgepaultony.progress.common.exception.InvalidFileException;
import in.georgepaultony.progress.common.exception.StorageException;
import in.georgepaultony.progress.storage.config.SupabaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupabaseStorageService implements StorageService {

    private static final long MAX_FILE_SIZE =
            10 * 1024 * 1024;

    private static final List<String> ALLOWED_TYPES =
            List.of(
                    "image/png",
                    "image/jpeg",
                    "application/pdf"
            );

    private final SupabaseProperties supabaseProperties;

    private final WebClient.Builder webClientBuilder;

    @Override
    public String uploadFile(
            MultipartFile file,
            String folder
    ) {

        validateFile(file);

        try {

            String fileName =
                    UUID.randomUUID()
                            + "-"
                            + file.getOriginalFilename();

            String objectPath =
                    folder + "/" + fileName;

            String uploadUrl =
                    supabaseProperties.getUrl()
                            + "/storage/v1/object/"
                            + supabaseProperties.getBucket()
                            + "/"
                            + objectPath;

            WebClient webClient =
                    webClientBuilder.build();

            webClient.post()
                    .uri(uploadUrl)
                    .header(
                            "Authorization",
                            "Bearer "
                                    + supabaseProperties.getServiceRoleKey()
                    )
                    .header(
                            "apikey",
                            supabaseProperties.getServiceRoleKey()
                    )
                    .header(
                            HttpHeaders.CONTENT_TYPE,
                            file.getContentType()
                    )
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return supabaseProperties.getUrl()
                    + "/storage/v1/object/public/"
                    + supabaseProperties.getBucket()
                    + "/"
                    + objectPath;

        } catch (IOException ex) {

            throw new StorageException(
                    "Failed to read uploaded file"
            );

        } catch (Exception ex) {

            throw new StorageException(
                    "Failed to upload file to storage"
            );
        }
    }

    private void validateFile(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {

            throw new InvalidFileException(
                    "File cannot be empty"
            );
        }

        if (file.getOriginalFilename() == null
                || file.getOriginalFilename().isBlank()) {

            throw new InvalidFileException(
                    "File name is required"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {

            throw new InvalidFileException(
                    "File size cannot exceed 10 MB"
            );
        }

        if (!ALLOWED_TYPES.contains(
                file.getContentType()
        )) {

            throw new InvalidFileException(
                    "Only PNG, JPEG and PDF files are allowed"
            );
        }
    }
}