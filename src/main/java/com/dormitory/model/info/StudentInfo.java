package com.dormitory.model.info;

import com.dormitory.model.AbstractModel;
import com.dormitory.model.account.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "stu_info")
public class StudentInfo extends AbstractModel {

    @NotNull
    private Integer domNumber;

    @NotNull
    private Integer bedNumber;

    @NotNull
    private Integer leaveTimes;

    @NotNull
    private Integer gender;

    @ManyToOne
    private ClassInfo classInfo;

}
