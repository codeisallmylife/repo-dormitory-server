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
    private String teacher_id;

    @ManyToOne
    private ClassInfo classInfo;
}
