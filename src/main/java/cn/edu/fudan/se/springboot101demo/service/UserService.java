package cn.edu.fudan.se.springboot101demo.service;


import cn.edu.fudan.se.springboot101demo.DTO.NewUserRequest;
import cn.edu.fudan.se.springboot101demo.DTO.UserResponse;
import cn.edu.fudan.se.springboot101demo.entity.User;
import cn.edu.fudan.se.springboot101demo.exception.InvalidRequestException;
import cn.edu.fudan.se.springboot101demo.exception.NotFoundException;
import cn.edu.fudan.se.springboot101demo.repository.UserRepository;
import cn.edu.fudan.se.springboot101demo.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public UserResponse registerUser(NewUserRequest request){
        // 检查用户名是否已经存在
        if (userRepository.existsByName(request.name())){
            throw new InvalidRequestException("Username already exists");
        }
        // 加密密码
        String encryptedPassword = PasswordUtil.encryptPassword(request.password());
        // 保存用户
        // 这里创建的用户对象中，id是null。
        var user = new User(null, request.name(), encryptedPassword, 0);
        // 保存用户后，对user重新赋值，此时id为数据库中自动生成的值。
        user = userRepository.save(user);
        return new UserResponse(user);
    }

    public UserResponse getUserResponseById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::new)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserResponse changeUsername(Long userId, String newName) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (userRepository.existsByName(newName)) {
            throw new InvalidRequestException("Username already exists");
        }
        user.setName(newName);
        user = userRepository.save(user);
        return new UserResponse(user);
    }

    public UserResponse addBalance(Long userId, Integer amount) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (amount < 0) {
            throw new InvalidRequestException("Amount must be positive");
        }
        user.setBalance(user.getBalance() + amount);
        user = userRepository.save(user);
        return new UserResponse(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void deductBalance(User user, Integer amount) {
        if (user.getBalance() < amount) {
            throw new InvalidRequestException("Insufficient balance");
        } else if (amount < 0) {
            throw new InvalidRequestException("Amount must be positive");
        }
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
    }
}
