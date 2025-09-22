package testing.belajar.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import testing.belajar.dto.CreateUserRequest;
import testing.belajar.exception.BadRequestException;
import testing.belajar.model.UserModel;
import testing.belajar.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static testing.belajar.utils.GeneralConstant.EMAIL;
import static testing.belajar.utils.GeneralConstant.ID;
import static testing.belajar.utils.GeneralConstant.NAME;
import static testing.belajar.utils.HelperUtils.isNullOrBlank;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<Map<String, Object>> getListUsers() {
        List<UserModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> userList = new ArrayList<>();
        for (UserModel user : users) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put(ID, user.getId());
            userMap.put(NAME, user.getName());
            userMap.put(EMAIL, user.getEmail());
            userList.add(userMap);
        }
        return userList;
    }

    public Map<String, Object> saveUser(CreateUserRequest request) throws BadRequestException {
        if (isNullOrBlank(request.getName()) || isNullOrBlank(request.getEmail())){
            log.error("Nama dan email wajib diisi");
            throw new BadRequestException();
        }

        if (isUserExistByEmail(request.getEmail())) {
            log.error("Email {} sudah terdaftar", request.getEmail());
            throw new BadRequestException();
        }

        save(request);
        return new HashMap<>();
    }

    @Transactional
    private void save(CreateUserRequest request) {
        UserModel user = new UserModel();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        userRepository.save(user);
    }
}
