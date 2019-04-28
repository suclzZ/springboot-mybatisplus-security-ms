package com.sucl.smsm.security.service;

import com.sucl.smsm.security.user.IUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author sucl
 * @date 2019/4/28
 */
@Slf4j
public class UserHolderServiceImpl implements UserHolderService ,ApplicationContextAware {
    private List<UserProvidor> userProvidors;

    @Override
    public IUser getUser(String username) {
        IUser user = null;
        if(userProvidors!=null){
            for(UserProvidor userProvidor : userProvidors){
                user = userProvidor.getUser(username);
                if(user!=null){
                    return  user;
                }
            }
        }else{
            log.warn("can't find any UserProvidor bean.");
        }
        if(user == null){
            log.warn("can't find any user by username 【{}】",username);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initUserProvidor(applicationContext);
    }

    private void initUserProvidor(ApplicationContext applicationContext) {
        Map<String, UserProvidor> userProvidorBeanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, UserProvidor.class, true, false);
        if(MapUtils.isNotEmpty(userProvidorBeanMap)){
            userProvidors = new ArrayList<UserProvidor>(userProvidorBeanMap.values());
            Collections.sort(userProvidors,OrderComparator.INSTANCE);
        }
    }
}
