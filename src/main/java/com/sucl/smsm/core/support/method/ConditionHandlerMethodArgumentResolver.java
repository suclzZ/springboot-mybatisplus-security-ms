package com.sucl.smsm.core.support.method;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.sucl.smsm.core.annotation.QueryCondition;
import com.sucl.smsm.core.orm.Condition;
import com.sucl.smsm.core.orm.Domain;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 查询条件参数转换
 * 结合mybatis-plus整合
 */
public class ConditionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String CONDITIONS = "conditions";
    private Map<String, Set<String>> cachedDomainFieldNames = Collections.synchronizedMap(new HashMap());

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return Wrapper.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(QueryCondition.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        QueryCondition queryCondition = parameter.getParameterAnnotation(QueryCondition.class);
        ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
        Class<?> domainClazz = resolvableType.getGeneric(0).resolve();
//        Class<?> domain = queryCondition.domain();
        Set<String> fieldNames = getDomainParamNames(domainClazz);
        String conditionStr = webRequest.getParameter(CONDITIONS);
        if(StringUtils.isNotEmpty(conditionStr)){
            ObjectMapper objectMapper = new ObjectMapper();
            Collection<Condition> conditions = objectMapper.readValue(conditionStr,new TypeReference<List<Condition>>(){});
            return converToWrapper(conditions,fieldNames);
        }else{
            Map<String, String> parameters = toStringValueMap(webRequest.getParameterMap());
            return converToWrapper(parameters,fieldNames);
        }
    }

    private Map<String,String> toStringValueMap(Map<String,String[]> parameterMap) {
        Map<String,String> parameters = Maps.newHashMap();
        if(parameterMap!=null){
            for(Map.Entry<String,String[]> entry : parameterMap.entrySet()){
                String[] vs = entry.getValue();
                String value = vs!=null&&vs.length>0?vs[0]:null;
                parameters.put(entry.getKey(),value);
            }
        }
        return parameters;
    }

    private Set<String> getDomainParamNames(Class<?> domainClazz){
        String domainClazzName = domainClazz.getName();
        Set<String> paramNames = new HashSet<String>();
        if (cachedDomainFieldNames.containsKey(domainClazzName)) {
            paramNames = cachedDomainFieldNames.get(domainClazzName);
        }else {
            ConditionFieldCallback conditionFieldCallback = new ConditionFieldCallback("");
            ReflectionUtils.doWithFields(domainClazz, conditionFieldCallback);
            paramNames.addAll(conditionFieldCallback.getFieldNames());
            this.cachedDomainFieldNames.put(domainClazzName, paramNames);
        }
        return paramNames;
    }

    private Wrapper converToWrapper(Map<String, String> parameters,Set<String> fields){
        QueryWrapper wrapper = new QueryWrapper();
        if(parameters!=null){
            for(String key : parameters.keySet()){
                if(fields.contains(key)){
                    String value = parameters.get(key);
                    key = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE).convert(key);
                    if(value==null){
                        wrapper.isNotNull(key);
                    }else{
                        wrapper.eq(key,value);
                    }
                }
            }
        }
        return wrapper;
    }

    private Wrapper converToWrapper(Collection<Condition> conditions,Set<String> fields){
        QueryWrapper wrapper = new QueryWrapper();
        if(conditions!=null){
            for(Condition condition : conditions){
                //判断property是否属于entity的属性
                if(fields.contains(condition.getProperty())){
                    wrapperCondition(wrapper,condition);
                }
            }
        }
        return wrapper;
    }

    /**
     * eq、like、in、gt、lt...
     * @param wrapper
     * @param condition
     */
    private void wrapperCondition(QueryWrapper wrapper, Condition condition) {
        String property = condition.getProperty();
        property = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE).convert(property);
        String operate = condition.getOperate()!=null?condition.getOperate().toUpperCase():null;
        Object value = condition.getValue();
        if(StringUtils.isEmpty(operate) || "EQ".equals(operate)){
            if(value == null){
                wrapper.isNull(property);
            }else{
                wrapper.eq(property,value);
            }
        }else if("NE".equals(operate)){
             if(value==null) {
                 wrapper.isNotNull(property);
             }else{
                 wrapper.ne(property,value);
             }
        }else if("LIKE".equals(operate)){
            wrapper.like(property,value);
        }else if("IN".equals(operate)){
            String [] values = null;
            if(value!=null){
                if(String.class.isAssignableFrom(value.getClass())){
                    values = value.toString().split(",");
                }else if(Collection.class.isAssignableFrom(value.getClass())){
                    values = (String[]) ((Collection)value).toArray();
                }
                if(values!=null)
                    wrapper.in(property,values);
            }
        }
        //TODO more...
    }

    private class ConditionFieldCallback implements ReflectionUtils.FieldCallback{
        private List<String> fieldNames = new ArrayList();
        private String prefix = "";

        public ConditionFieldCallback(String prefix) {
            this.prefix = Objects.toString(prefix,"");
        }

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException{
            String fieldName = field.getName();
            TableField tableField = field.getAnnotation(TableField.class);
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableField != null || tableId!=null) {
                this.fieldNames.add(this.prefix + fieldName);
            }else {
                Class fieldClass = field.getType();
                if (Domain.class.isAssignableFrom(fieldClass)){
                    if (this.prefix.split("\\.").length > 3) {
                        return;
                    }
                    ConditionFieldCallback conditionFieldCallback = new ConditionFieldCallback( this.prefix + field.getName() + ".");
                    ReflectionUtils.doWithFields(fieldClass, conditionFieldCallback);
                    this.fieldNames.addAll(conditionFieldCallback.getFieldNames());
                }
            }
        }
        public List<String> getFieldNames() {
            return this.fieldNames;
        }
    }
}
