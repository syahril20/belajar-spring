package testing.belajar.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import testing.belajar.dto.CreateVisitRequest;
import testing.belajar.exception.BadRequestException;
import testing.belajar.model.UserVisitModel;
import testing.belajar.repository.UserVisitRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static testing.belajar.utils.GeneralConstant.DATA;
import static testing.belajar.utils.GeneralConstant.NAME;
import static testing.belajar.utils.GeneralConstant.PAGE;
import static testing.belajar.utils.GeneralConstant.SIZE;
import static testing.belajar.utils.GeneralConstant.TOTAL_ELEMENTS;
import static testing.belajar.utils.GeneralConstant.TOTAL_PAGES;
import static testing.belajar.utils.GeneralConstant.USER_ID;
import static testing.belajar.utils.GeneralConstant.VISIT_COUNT;
import static testing.belajar.utils.HelperUtils.isNullOrBlank;

@Slf4j
@Service
public class UserVisitService {

    private static final int MIN_PAGE_SIZE = 1;

    private final UserVisitRepository userVisitRepository;

    public UserVisitService(UserVisitRepository userVisitRepository) {
        this.userVisitRepository = userVisitRepository;
    }

    public Map<String, Object> findByNameOrUserId(String searchQuery, int page, int size) {
        if (page < 0 || size < MIN_PAGE_SIZE) {
            log.error("Invalid pagination params: page={}, size={}", page, size);
            throw new BadRequestException("Invalid pagination parameters");
        }
        if (isNullOrBlank(searchQuery)) {
            log.error("Search query cannot be empty");
            throw new BadRequestException("Search query is required");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<UserVisitModel> visitPage = userVisitRepository.findByNameContainingIgnoreCaseOrUserIdContainingIgnoreCase(
                searchQuery, searchQuery, pageable);

        List<UserVisitModel> visits = visitPage.getContent();
        List<Map<String, Object>> userList = new ArrayList<>();
        Set<String> uniqueUserIds = new HashSet<>();
        Map<String, Long> visitCountCache = new HashMap<>();

        for (UserVisitModel userVisit : visits) {
            String uid = userVisit.getUserId();
            if (uniqueUserIds.add(uid)) {
                long countVisit = visitCountCache.computeIfAbsent(uid, userVisitRepository::countByUserId);
                userList.add(toUserSummary(userVisit, countVisit));
            }
        }

        return buildPagedResponse(page, size, visitPage.getTotalElements(), visitPage.getTotalPages(), userList);
    }


    public Map<String, Object> getAllVisits(int page, int size) {
        if (page < 0 || size < MIN_PAGE_SIZE) {
            log.error("Invalid pagination params: page={}, size={}", page, size);
            throw new BadRequestException("Invalid pagination parameters");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<UserVisitModel> visitPage = userVisitRepository.findAll(pageable);

        List<UserVisitModel> visits = visitPage.getContent();

        List<Map<String, Object>> userList = new ArrayList<>();
        Set<String> uniqueUserIds = new HashSet<>();
        Map<String, Long> visitCountCache = new HashMap<>();

        for (UserVisitModel userVisit : visits) {
            String uid = userVisit.getUserId();
            if (uniqueUserIds.add(uid)) {
                long countVisit = visitCountCache.computeIfAbsent(uid, userVisitRepository::countByUserId);
                userList.add(toUserSummary(userVisit, countVisit));
            }
        }

        return buildPagedResponse(page, size, visitPage.getTotalElements(), visitPage.getTotalPages(), userList);
    }

    public void createVisit(CreateVisitRequest request) {
        if (request == null || isNullOrBlank(request.getUserId()) || isNullOrBlank(request.getName())) {
            log.error("User ID dan nama wajib diisi");
            throw new BadRequestException();
        }
        persistVisit(request);
    }

    @Transactional
    public void persistVisit(CreateVisitRequest request) {
        UserVisitModel visit = new UserVisitModel();
        visit.setUserId(request.getUserId());
        visit.setName(request.getName());
        userVisitRepository.save(visit);
    }

    private Map<String, Object> toUserSummary(UserVisitModel userVisit, long countVisit) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put(USER_ID, userVisit.getUserId());
        userMap.put(NAME, userVisit.getName());
        userMap.put(VISIT_COUNT, countVisit);
        return userMap;
    }

    private Map<String, Object> buildPagedResponse(int page, int size, long totalElements, int totalPages, List<Map<String, Object>> data) {
        Map<String, Object> response = new HashMap<>();
        response.put(PAGE, page);
        response.put(SIZE, size);
        response.put(TOTAL_ELEMENTS, totalElements);
        response.put(TOTAL_PAGES, totalPages);
        response.put(DATA, data);
        return response;
    }
}
