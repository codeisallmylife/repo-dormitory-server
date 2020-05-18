package com.dormitory.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.dormitory.dao.account.AskForLeaveRepository;
import com.dormitory.dao.account.TeacherInfoRepository;
import com.dormitory.dao.account.UserRepository;
import com.dormitory.dto.SessionVO;
import com.dormitory.model.info.TeacherInfo;
import com.dormitory.model.log.AskForLeave;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.dormitory.util.CodeConst.SESSION_VO_STR;

/**
 * @Author: aceace3
 * @Date: 2020/4/28 0028 下午 8:55
 */
@RestController
@RequestMapping(value = "/AskForLeaveController" , produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
public class AskForLeaveController {
    private transient Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    AskForLeaveRepository askForLeaveRepository;

    @Autowired
    UserRepository userRepository;


    //学生功能: 请假记录
    @RequestMapping(value = "/getStuRecord")
    public List getStuRecord(@RequestBody JSONObject param, HttpServletRequest request, HttpServletResponse respons){
        logger.info("-= enter getStuRecord=-");
        List<AskForLeave>  askForLeaveList=new ArrayList<>();
        askForLeaveList= (List<AskForLeave>) askForLeaveRepository.findAll();
        logger.info("SvoID: "+getSvo(request).getSvoId());

        for(AskForLeave askForLeave: askForLeaveList){
            if (askForLeave.getStudentInfo().getId()==getSvo(request).getRealId()) {
                logger.info("teacherId:" + userRepository.findOne(askForLeave.getTeacherInfo().getId()).getUsername());
                logger.info("replyText:" + askForLeave.getReplyText());
                logger.info("isHandle:" + askForLeave.getIsHandle());
                logger.info("Time:" + String.valueOf(askForLeave.getUpdated()).substring(5, 16));
            }
        }


        return askForLeaveList;
    }

    public SessionVO getSvo(HttpServletRequest request) {
        return (SessionVO) request.getSession().getAttribute(SESSION_VO_STR);
    }
}
