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

    private final UserVisitRepository userVisitRepository;

    public UserVisitService(UserVisitRepository userVisitRepository) {
        this.userVisitRepository = userVisitRepository;
    }

    public Map<String, Object> getAllVisits(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserVisitModel> visitPage = userVisitRepository.findAll(pageable);

        List<UserVisitModel> visits = visitPage.getContent();

        List<Map<String, Object>> userList = new ArrayList<>();
        Set<String> userIds = new HashSet<>(); // pakai Set supaya unik

        for (UserVisitModel userVisit : visits) {
            if (!userIds.contains(userVisit.getUserId())) {
                userIds.add(userVisit.getUserId());

                Map<String, Object> userMap = new HashMap<>();
                long countVisit = userVisitRepository.countByUserId(userVisit.getUserId());
                userMap.put(USER_ID, userVisit.getUserId());
                userMap.put(NAME, userVisit.getName());
                userMap.put(VISIT_COUNT, countVisit);

                userList.add(userMap);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put(PAGE, page);
        response.put(SIZE, size);
        response.put(TOTAL_ELEMENTS, visitPage.getTotalElements());
        response.put(TOTAL_PAGES, visitPage.getTotalPages());
        response.put(DATA, userList);

        return response;
    }

    public Map<String, Object> saveVisit(CreateVisitRequest request) {
        if (isNullOrBlank(request.getUserId()) || isNullOrBlank(request.getName())) {
            log.error("User ID dan nama wajib diisi");
            throw new BadRequestException();
        }

        save(request);
        return new HashMap<>();
    }

    @Transactional
    public void save(CreateVisitRequest request) {
        UserVisitModel visit = new UserVisitModel();
        visit.setUserId(request.getUserId());
        visit.setName(request.getName());
        userVisitRepository.save(visit);
    }
}
