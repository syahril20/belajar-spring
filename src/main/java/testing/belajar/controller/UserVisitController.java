package testing.belajar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testing.belajar.dto.CreateVisitRequest;
import testing.belajar.services.UserVisitService;
import testing.belajar.utils.ApiResponse;

import static testing.belajar.utils.GeneralConstant.SUCCESS;

@RestController
@RequestMapping("/v1/users/visit")
public class UserVisitController {

    private final UserVisitService userVisitService;

    public UserVisitController(UserVisitService userVisitService) {
        this.userVisitService = userVisitService;
    }

    @GetMapping
    public ApiResponse getVisits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return new ApiResponse(200, SUCCESS, userVisitService.getAllVisits(page, size));
    }

    @PostMapping
    public ApiResponse createVisit(@RequestBody CreateVisitRequest request) {
        return new ApiResponse(200, SUCCESS, userVisitService.saveVisit(request));
    }

}
