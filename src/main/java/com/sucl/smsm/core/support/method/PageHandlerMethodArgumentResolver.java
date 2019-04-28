package com.sucl.smsm.core.support.method;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.sucl.smsm.core.annotation.QueryPage;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 针对EXT请求格式进行的参数转换
 */
public class PageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(QueryPage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String page = Objects.toString(webRequest.getParameter("page"),"0");
//        String start = webRequest.getParameter("start");
        String limit = Objects.toString(webRequest.getParameter("limit"),"-1");
        Page pager = new Page();
        pager.setSize(Integer.valueOf(limit));
        pager.setCurrent(Integer.valueOf(page));

        String sort = webRequest.getParameter("sort");
        //{"property":"employeeNo","direction":"DESC"}
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> sortList = null;
        if(sort != null){
            sortList = objectMapper.readValue(sort, new TypeReference<List<Map<String, String>>>() { });
        }
        List<String> ascs = new ArrayList<>();
        List<String> descs = new ArrayList<>();
        if(sortList!=null){
            for(Map smap : sortList){
                String field = Objects.toString(smap.get("property"));
                field = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE).convert(field);
                if("DESC".equals(smap.get("direction"))){
                    descs.add(field);
                }else if("ASC".equals(smap.get("direction"))){
                    ascs.add(field);
                }
            }
        }
        pager.setAscs(ascs);
        pager.setDescs(descs);
        return pager;
    }
}
