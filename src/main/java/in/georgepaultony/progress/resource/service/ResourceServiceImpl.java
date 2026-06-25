package in.georgepaultony.progress.resource.service;

import in.georgepaultony.progress.auth.security.CurrentUserProvider;
import in.georgepaultony.progress.common.exception.ForbiddenException;
import in.georgepaultony.progress.common.exception.ResourceNotFoundException;
import in.georgepaultony.progress.journal.entity.Journal;
import in.georgepaultony.progress.journal.repository.JournalRepository;
import in.georgepaultony.progress.resource.dto.CreateResourceRequest;
import in.georgepaultony.progress.resource.dto.ResourceResponse;
import in.georgepaultony.progress.resource.dto.UpdateResourceRequest;
import in.georgepaultony.progress.resource.entity.Resource;
import in.georgepaultony.progress.resource.mapper.ResourceMapper;
import in.georgepaultony.progress.resource.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final JournalRepository journalRepository;
    private final ResourceMapper resourceMapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public ResourceResponse create(
            UUID journalId,
            CreateResourceRequest request
    ) {

        UUID userId = currentUserProvider.getId();

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Journal not found"));

        if (!journal.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }

        Resource resource = resourceMapper.toEntity(request);

        resource.setJournal(journal);

        resource = resourceRepository.save(resource);

        return resourceMapper.toResponse(resource);
    }

    @Override
    public List<ResourceResponse> getByJournal(
            UUID journalId
    ) {

        UUID userId = currentUserProvider.getId();

        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Journal not found"));

        if (!journal.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }

        return resourceRepository
                .findByJournalIdAndIsDeletedFalse(journalId)
                .stream()
                .map(resourceMapper::toResponse)
                .toList();
    }

    @Override
    public ResourceResponse update(
            UUID resourceId,
            UpdateResourceRequest request
    ) {

        UUID userId = currentUserProvider.getId();

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resource not found"));

        if (!resource.getJournal()
                .getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException("Access denied");
        }

        if (request.resourceType() != null) {
            resource.setResourceType(
                    request.resourceType()
            );
        }

        if (request.title() != null) {
            resource.setTitle(
                    request.title()
            );
        }

        if (request.url() != null) {
            resource.setUrl(
                    request.url()
            );
        }

        resource = resourceRepository.save(resource);

        return resourceMapper.toResponse(resource);
    }

    @Override
    public void delete(
            UUID resourceId
    ) {

        UUID userId = currentUserProvider.getId();

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Resource not found"));

        if (!resource.getJournal()
                .getUser()
                .getId()
                .equals(userId)) {

            throw new ForbiddenException("Access denied");
        }

        resource.setIsDeleted(true);

        resourceRepository.save(resource);
    }
}