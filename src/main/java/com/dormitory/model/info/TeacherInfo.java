package com.dormitory.model.info;

import com.dormitory.model.AbstractModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "teach_info")
public class TeacherInfo extends AbstractModel {

    @NotNull
    @Column(columnDefinition = "varchar(32) COMMENT'傻儿子赵一不知道为什么要设置一个教师的专有id'")
    private String teacher_id;

    @ManyToOne
    private ClassInfo classInfo;
}
