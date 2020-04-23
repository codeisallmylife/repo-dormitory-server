package com.dormitory.controller.account;

import com.dormitory.dao.account.*;
import com.dormitory.dto.AjaxResponse;
import com.dormitory.model.AbstractModel;
import com.dormitory.model.account.*;
import com.dormitory.model.info.ClassInfo;
import com.dormitory.model.info.StudentInfo;
import com.dormitory.model.info.TeacherInfo;
import com.dormitory.model.log.SignLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 此类为测试鉴权内容写入，执行时按照接口前注释所写的step顺序调用
 */

@RestController
@Transactional
@RequestMapping(value = "/account/test" , produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
public class TestController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SysRoleActionRepository sysRoleActionRepository;

    @Autowired
    private RoleActionRepository roleActionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private TeacherInfoRepository teacherInfoRepository;

    @Autowired
    private SignLogRepository signLogRepository;

    //step 0 测试dao
    @RequestMapping(value = "/testDao")
    public String testDao(){
        Role r=roleRepository.findByName("学生");
        System.out.println("-=-=学生："+r);

        return "testDao运行结束";
    }

    //step 1
    @RequestMapping(value = "/testCreateRole")
    public AjaxResponse testCreateRole(){
        AjaxResponse json = AjaxResponse.buildSuccessResponse();

        List<Role> roles = new ArrayList<>();

        Role role = new Role();
        role.setName("管理员");
        role.setDescription("管理员,系统负责人，最高权限");

        Role role1 = new Role();
        role1.setName("学生");
        role1.setDescription("学生，只允许调用学生权限下的接口");

        Role role2 = new Role();
        role2.setName("教师");
        role2.setDescription("教师，只允许调用教师权限下的接口");

        roles.add(role);
        roles.add(role1);
        roles.add(role2);

        roleRepository.save(roles);

        return json;
    }

    //step 3
    @RequestMapping(value = "/testCreateRoleAction")
    public AjaxResponse testCreateRoleAction(){
        AjaxResponse json = AjaxResponse.buildSuccessResponse();

        List<SysRoleAction> sysRoleActions = sysRoleActionRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        //分出角色
        Role admin = new Role();
        Role stu = new Role();
        Role teach = new Role();
        for (Role role:roles){
            if (role.getName().equals("管理员")){
                admin = role;
            }
            if (role.getName().equals("学生")){
                stu = role;
            }
            if (role.getName().equals("教师")){
                teach = role;
            }
        }

        //分出权限
        SysRoleAction sysRoleActionForAdmin = new SysRoleAction();
        SysRoleAction sysRoleActionForStu = new SysRoleAction();
        SysRoleAction sysRoleActionForTeacher = new SysRoleAction();
        for (SysRoleAction sysRoleAction:sysRoleActions){
            if (sysRoleAction.getActionType().equals(SysRoleAction.ActionType.API_ALLOW_ADMIN)){
                sysRoleActionForAdmin = sysRoleAction;
            }
            if (sysRoleAction.getActionType().equals(SysRoleAction.ActionType.API_ALLOW_STU)){
                sysRoleActionForStu = sysRoleAction;
            }
            if (sysRoleAction.getActionType().equals(SysRoleAction.ActionType.API_ALLOW_TEACH)){
                sysRoleActionForTeacher = sysRoleAction;
            }
        }


        //step1 设定管理员权限
        List<RoleAction> roleActions = new ArrayList<>();
        for (SysRoleAction sysRoleAction:sysRoleActions){
            RoleAction roleAction = new RoleAction();
            roleAction.setAction(sysRoleAction);
            roleAction.setRole(admin);
            roleActions.add(roleAction);
        }

        //step2 设定学生权限
        RoleAction roleActionForStu = new RoleAction();
        roleActionForStu.setAction(sysRoleActionForStu);
        roleActionForStu.setRole(stu);

        //step3 设定教师权限
        RoleAction roleActionsForTeacher = new RoleAction();
        roleActionsForTeacher.setAction(sysRoleActionForTeacher);
        roleActionsForTeacher.setRole(teach);

        roleActions.add(roleActionForStu);
        roleActions.add(roleActionsForTeacher);

        roleActionRepository.save(roleActions);

        return json;
    }

    //step 2
    @RequestMapping(value = "/testCreateSysRoleAction")
    public AjaxResponse testCreateSysRoleAction(){
        AjaxResponse json = AjaxResponse.buildSuccessResponse();

        List<SysRoleAction> sysRoleActions = new ArrayList<>();

        SysRoleAction sysRoleAction = new SysRoleAction();
        sysRoleAction.setActionType(SysRoleAction.ActionType.API_ALLOW_ADMIN);
        sysRoleAction.setActionGroup(SysRoleAction.ActionGroup.API_ACCOUNT);

        SysRoleAction sysRoleAction1 = new SysRoleAction();
        sysRoleAction1.setActionType(SysRoleAction.ActionType.API_ALLOW_STU);
        sysRoleAction1.setActionGroup(SysRoleAction.ActionGroup.API_ACCOUNT);

        SysRoleAction sysRoleAction2 = new SysRoleAction();
        sysRoleAction2.setActionType(SysRoleAction.ActionType.API_ALLOW_TEACH);
        sysRoleAction2.setActionGroup(SysRoleAction.ActionGroup.API_ACCOUNT);

        sysRoleActions.add(sysRoleAction);
        sysRoleActions.add(sysRoleAction1);
        sysRoleActions.add(sysRoleAction2);

        sysRoleActionRepository.save(sysRoleActions);

        return json;
    }

    //step 4
    @RequestMapping(value = "/testCreatePermission")
    public AjaxResponse testCreatePermission(){
        AjaxResponse json = AjaxResponse.buildSuccessResponse();

        Permission permission = new Permission();

        permission.setDescription("数据管理员");
        permission.setName("数据管理员");
        permission.setType(1);

        permissionRepository.save(permission);

        return json;
    }

    //step 5
    @RequestMapping(value = "/testCreateClass")
    public AjaxResponse testCreateClass(){
        AjaxResponse json = AjaxResponse.buildSuccessResponse();

        ClassInfo classInfo = new ClassInfo();
        classInfo.setClassName("一班");

        classInfoRepository.save(classInfo);

        return json;
    }

    //step 6
    @RequestMapping(value = "/testCreateUser")
    public AjaxResponse testCreateUser(){
        AjaxResponse json = AjaxResponse.buildSuccessResponse();

        List<SysRoleAction> sysRoleActions = sysRoleActionRepository.findAll();
        List<Role> roles = roleRepository.findAll();


        //分出角色
        Role admin = new Role();
        Role stu = new Role();
        Role teach = new Role();
        for (Role role:roles){
            if (role.getName().equals("管理员")){
                admin = role;
            }
            if (role.getName().equals("学生")){
                stu = role;
            }
            if (role.getName().equals("教师")){
                teach = role;
            }
        }

        //学生账号
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setLeaveTimes(1);
        studentInfo.setGender(1);
        studentInfo.setDomNumber(1);
        studentInfo.setBedNumber(1);
        studentInfo.setBuilding("1");
        studentInfo.setStudentCode("1");
        studentInfo.setClassInfo(classInfoRepository.getOne(1));
        StudentInfo s = studentInfoRepository.save(studentInfo);

        User user = new User();
        user.setRole(stu);
        user.setUsername("aaa");
        user.setPassword("123");
        user.setPhone("123");
        user.setStudentInfo(s);
        user.setClassInfo(classInfoRepository.findOne(1));

        userRepository.save(user);

        //教师账号
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setTeacher_id("100001");
        teacherInfo.setClassInfo(classInfoRepository.getOne(1));

        TeacherInfo t = teacherInfoRepository.save(teacherInfo);

        User user1 = new User();
        user1.setRole(teach);
        user1.setUsername("aaaa");
        user1.setPassword("1234");
        user1.setPhone("1234");
        user1.setTeacherInfo(t);
        user1.setClassInfo(classInfoRepository.findOne(1));


        userRepository.save(user1);

        //管理员账号
        User adminUser = new User();
        adminUser.setRole(admin);
        adminUser.setUsername("aaaaa");
        adminUser.setPassword("12345");
        adminUser.setPhone("12345");

        userRepository.save(adminUser);
        return json;
    }


    //step7
    @RequestMapping(value = "/createSignLog")
    public AjaxResponse createSignLog(){
        AjaxResponse  json=AjaxResponse.buildFailResponse();

        List<SignLog> signLogs=new ArrayList<>();
//        打卡记录1
        SignLog signLog=new SignLog();
        signLog.setClockInDay(17);
        signLog.setClockInMonth(4);
        signLog.setClockInYear(2020);
//        打卡记录2
        SignLog signLog2=new SignLog();
        signLog2.setClockInDay(18);
        signLog2.setClockInMonth(4);
        signLog2.setClockInYear(2020);

        signLogs.add(signLog);
        signLogs.add(signLog2);

//        获取学生ID=1
        StudentInfo studentInfo=studentInfoRepository.getOne(1);
        signLog.setStudentInfo(studentInfo);
        signLog2.setStudentInfo(studentInfo);


        signLogRepository.save(signLogs);



        return json;
    }


}
