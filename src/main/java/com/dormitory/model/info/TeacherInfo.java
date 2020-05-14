package com.dormitory.model.info;

import com.dormitory.model.AbstractModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "teach_info")
public class TeacherInfo extends AbstractModel {


//    @OneToMany
//    private Set<ClassInfo> classInfos;
}

