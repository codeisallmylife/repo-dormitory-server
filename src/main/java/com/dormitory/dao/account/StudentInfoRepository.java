package com.dormitory.dao.account;

import com.dormitory.dao.BaseRepository;
import com.dormitory.model.account.Role;
import com.dormitory.model.info.StudentInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentInfoRepository extends BaseRepository<StudentInfo> {

    @Query("from StudentInfo si where si.studentCode = :studentCode")
    StudentInfo findByStudentCode(@Param("studentCode") String studentCode);

}
