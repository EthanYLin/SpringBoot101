package cn.edu.fudan.se.springboot101demo.repository;

import cn.edu.fudan.se.springboot101demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
}
