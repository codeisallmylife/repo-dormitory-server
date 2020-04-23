package com.dormitory.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.dormitory.dao.account.ClassInfoRepository;
import com.dormitory.dao.account.RoleRepository;
import com.dormitory.dao.account.StudentInfoRepository;
import com.dormitory.dao.account.UserRepository;
import com.dormitory.model.account.Role;
import com.dormitory.model.account.User;
import com.dormitory.model.info.ClassInfo;
import com.dormitory.model.info.StudentInfo;
import com.dormitory.service.account.RegisterService;
import com.dormitory.service.account.impl.RegisterServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 注册
 */
@RestController
@RequestMapping(value = "/RegisterController", produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
public class RegisterController {

    private transient Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    RegisterService registerService=new RegisterServiceImpl();

    @Autowired
    StudentInfoRepository studentInfoRepository;

    //学生注册
    @RequestMapping(value = "/stuRegister")
    public boolean stuRegister(@RequestBody JSONObject param, HttpServletRequest request, HttpServletResponse respons){

        logger.info("-= enter stuRegister =-");

        //获取参数
        String stuName=param.getString("stuName");
        int gender=param.getIntValue("gender");
        String studentCode=param.getString("studentCode");
        String phone=param.getString("phone");
        int classInfoId=param.getIntValue("classInfoId");
        String building=param.getString("building");
        int domNumber=param.getIntValue("domNumber");
        int bedNumber=param.getIntValue("bedNumber");
        String password=param.getString("password");
        String confirmPass=param.getString("confirmPass");

        System.out.println("-=-=-=-=-=-=-=-=-=- ");
        System.out.println("-=-=-=-=-=-=-=-=-=- "+studentInfoRepository.findByStudentCode(studentCode));
        System.out.println("-=-=-=-=-=-=-=-=-=- ");


        if (password.equals(confirmPass)){
            registerService.studentRegister(stuName,gender,studentCode,phone,classInfoId,building,domNumber,bedNumber,password,confirmPass);
            return true;
        }


        return false;
    }

}
