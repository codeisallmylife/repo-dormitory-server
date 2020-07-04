package com.dormitory.dao.account;

import com.dormitory.dao.BaseRepository;
import com.dormitory.model.account.User;
import com.dormitory.model.info.StudentInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.jdo.annotations.Transactional;
import java.util.List;

public interface UserRepository extends BaseRepository<User> {

    @Query("from User u where u.username = :username and remove = 0")
    User findByUsername(@Param("username") String username);

    List<User> findByPhoneIs(String phone);

    User findByStudentInfoIdIs(int studentInfoId);



}
