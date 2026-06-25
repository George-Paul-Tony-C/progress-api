package in.georgepaultony.progress.resource.service;

import in.georgepaultony.progress.resource.dto.CreateResourceRequest;
import in.georgepaultony.progress.resource.dto.ResourceResponse;
import in.georgepaultony.progress.resource.dto.UpdateResourceRequest;

import java.util.List;
import java.util.UUID;

public interface ResourceService {

    ResourceResponse create(
            UUID journalId,
            CreateResourceRequest request
    );

    List<ResourceResponse> getByJournal(
            UUID journalId
    );

    ResourceResponse update(
            UUID resourceId,
            UpdateResourceRequest request
    );

    void delete(
            UUID resourceId
    );
}