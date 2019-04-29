package com.sucl.smsm.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author sucl
 * @date 2019/4/29
 */
@Service
public class PasswordServiceImpl implements PasswordService{
    private PasswordEncoder passwordEncoder;

    public PasswordServiceImpl(){}

    public PasswordServiceImpl(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence password) {
        if(passwordEncoder!=null){
            return passwordEncoder.encode(password);
        }
        return password.toString();
    }

    @Override
    public boolean matches(CharSequence rawPwd, String encodPwd) {
        if(StringUtils.isEmpty(rawPwd) && StringUtils.isEmpty(encodPwd)){
            return true;
        }else if(!StringUtils.isEmpty(rawPwd) && !StringUtils.isEmpty(encodPwd)){
            if(passwordEncoder!=null){
                return passwordEncoder.matches(rawPwd,encodPwd);
            }else{
                return rawPwd.equals(encodPwd);
            }
        }
        return false;
    }
}
