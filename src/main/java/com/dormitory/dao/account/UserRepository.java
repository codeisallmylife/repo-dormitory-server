package com.dormitory.dao.account;

import com.dormitory.dao.BaseRepository;
import com.dormitory.model.account.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends BaseRepository<User> {

    @Query("from User u where u.username = :username and remove = 0")
    User findByUsername(@Param("username") String username);

}
