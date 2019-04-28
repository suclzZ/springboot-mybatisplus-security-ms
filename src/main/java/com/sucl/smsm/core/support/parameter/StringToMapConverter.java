package com.sucl.smsm.core.support.parameter;

import com.sucl.smsm.core.util.JsonUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * eg:DmsFileView:properties
 * @author sucl
 * @since 2018/11/21
 */
@Component
public class StringToMapConverter implements Converter<String,Map<String,Object>> {

    @Override
    public Map<String, Object> convert(String source) {
        if(source.startsWith("{") && source.endsWith("}")){
            return JsonUtils.toMap(source);
        }
        return null;
    }
}
