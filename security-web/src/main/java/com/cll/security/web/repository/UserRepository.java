package com.cll.security.web.repository;

import com.cll.security.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByName(String name);
}
