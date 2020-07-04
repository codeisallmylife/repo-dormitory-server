package com.dormitory.service.account.impl;

import com.dormitory.dao.account.SignLogRepository;
import com.dormitory.dto.SessionVO;
import com.dormitory.model.log.SignLog;
import com.dormitory.service.account.SignLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.dormitory.util.CodeConst.SESSION_VO_STR;

/**
 * @author aceace3
 * @version 1.0
 * @date 2020/5/19 18:30
 */
@Service
@Transactional
public class SignLogServiceImpl implements SignLogService {

    private transient Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    SignLogRepository signLogRepository;

    @Override
    public List getSignLog(HttpServletRequest request, List<SignLog> signLogs) {

        int daysCount=0;


        List allSignLog=new ArrayList();

        for(SignLog signLog: signLogs){
            if (signLog.getStudentInfo().getId()==getSvo(request).getRealId()) {
                daysCount++;
                List YMD=new ArrayList();
                YMD.add(signLog.getClockInYear());
                YMD.add(signLog.getClockInMonth());
                YMD.add(signLog.getClockInDay());

                allSignLog.add(YMD);
                allSignLog.add(daysCount);
            }
        }
        return allSignLog;
    }
    public SessionVO getSvo(HttpServletRequest request) {
        return (SessionVO) request.getSession().getAttribute(SESSION_VO_STR);
    }
}
