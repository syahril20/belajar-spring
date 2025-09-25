package testing.belajar.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import testing.belajar.dto.CreateVisitRequest;
import testing.belajar.exception.BadRequestException;
import testing.belajar.model.UserVisitModel;
import testing.belajar.repository.UserVisitRepository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserVisitServiceTest {

    // Test for successful getAllVisits with valid pagination parameters
    @Test
    void testGetAllVisits_Success() {
        int page = 0;
        int size = 5;

        Pageable pageable = PageRequest.of(page, size);
        Page mockPage = mock(Page.class);
        List<UserVisitModel> mockVisitList = List.of(
                new UserVisitModel("user1", "Alice"),
                new UserVisitModel("user2", "Bob")
        );

        when(userVisitRepository.findAll(pageable)).thenReturn(mockPage);
        when(mockPage.getContent()).thenReturn(mockVisitList);
        when(mockPage.getTotalElements()).thenReturn(2L);
        when(mockPage.getTotalPages()).thenReturn(1);
        when(userVisitRepository.countByUserId("user1")).thenReturn(1L);
        when(userVisitRepository.countByUserId("user2")).thenReturn(1L);

        Map<String, Object> result = userVisitService.getAllVisits(page, size);

        verify(userVisitRepository, times(1)).findAll(pageable);

        assert result.get("page").equals(page);
        assert result.get("size").equals(size);
        assert result.get("totalElements").equals(2L);
        assert result.get("totalPages").equals(1);
    }

    // Test for getAllVisits with negative page number
    @Test
    void testGetAllVisits_NegativePage() {
        assertThrows(BadRequestException.class, () -> userVisitService.getAllVisits(-1, 5));
        verify(userVisitRepository, never()).findAll(any(Pageable.class));
    }

    // Test for getAllVisits with page size less than the minimum size
    @Test
    void testGetAllVisits_InvalidPageSize() {
        assertThrows(BadRequestException.class, () -> userVisitService.getAllVisits(0, 0));
        verify(userVisitRepository, never()).findAll(any(Pageable.class));
    }

    // Test for getAllVisits when the repository returns an empty page
    @Test
    void testGetAllVisits_EmptyPage() {
        int page = 0;
        int size = 5;

        Pageable pageable = PageRequest.of(page, size);
        Page<UserVisitModel> mockPage = mock(Page.class);

        when(userVisitRepository.findAll(pageable)).thenReturn(mockPage);
        when(mockPage.getContent()).thenReturn(List.of());
        when(mockPage.getTotalElements()).thenReturn(0L);
        when(mockPage.getTotalPages()).thenReturn(0);

        Map<String, Object> result = userVisitService.getAllVisits(page, size);

        verify(userVisitRepository, times(1)).findAll(pageable);

        assert result.get("page").equals(page);
        assert result.get("size").equals(size);
        assert result.get("totalElements").equals(0L);
        assert result.get("totalPages").equals(0);
    }

    @InjectMocks
    private UserVisitService userVisitService;

    @Mock
    private UserVisitRepository userVisitRepository;

    @Test
    void testCreateVisit_SuccessfulCreation() {
        CreateVisitRequest request = new CreateVisitRequest("user123", "John Doe");
        UserVisitModel visitModel = new UserVisitModel();
        visitModel.setUserId(request.getUserId());
        visitModel.setName(request.getName());

        when(userVisitRepository.save(any(UserVisitModel.class))).thenReturn(visitModel);

        userVisitService.createVisit(request);

        verify(userVisitRepository, times(1)).save(any(UserVisitModel.class));
    }

    @Test
    void testCreateVisit_NullRequest() {
        assertThrows(BadRequestException.class, () -> userVisitService.createVisit(null));
        verify(userVisitRepository, never()).save(any(UserVisitModel.class));
    }

    @Test
    void testCreateVisits_MissingUserId() {
        CreateVisitRequest request = new CreateVisitRequest(null, "John Doe");
        assertThrows(BadRequestException.class, () -> userVisitService.createVisit(request));
        verify(userVisitRepository, never()).save(any(UserVisitModel.class));
    }

    @Test
    void testCreateVisit_MissingName() {
        CreateVisitRequest request = new CreateVisitRequest("user123", null);
        assertThrows(BadRequestException.class, () -> userVisitService.createVisit(request));
        verify(userVisitRepository, never()).save(any(UserVisitModel.class));
    }

    @Test
    void testCreateVisit_BlankFields() {
        CreateVisitRequest request = new CreateVisitRequest("", "");
        assertThrows(BadRequestException.class, () -> userVisitService.createVisit(request));
        verify(userVisitRepository, never()).save(any(UserVisitModel.class));
    }
}