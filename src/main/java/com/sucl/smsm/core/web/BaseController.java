package com.sucl.smsm.core.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sucl.smsm.core.support.annotation.QueryCondition;
import com.sucl.smsm.core.support.annotation.QueryPage;
import com.sucl.smsm.core.orm.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sucl
 * @since 2018/12/8
 */
public class BaseController<S extends IService<M>,M extends Domain> {

    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);
    /**
     * 请求参数标记
     * 当Controller的返回对象类型与泛型约束的类型不一致时，我们可以重写该请求方法
     * 但是为了保证请求路径的一致，通过在请求中加上这个参数，则可以“重写”该方法，同时区分了请求
     * 比如：入参DmsUserView对象，为DmsUesr的扩展，为了接受该参数，必须重新定义请求
     */
    protected static final String COMBINED = "combined";
    private MessageSource messageSource;

    /**
     * 国际化
     * @param messageSource
     */
    @Autowired
    private void init(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    /**
     * 国际化服务
     * 可以在classpath:中加上对应的国际化描述
     * @param name
     * @param args
     * @return
     */
    protected String getI18nMessage(String name, String[] args){
        try{
            return this.messageSource.getMessage(name, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            logger.warn("messages文件没有找到【" + name + "】！");
        }
        return null;
    }

    protected String getI18nMessage(String name) {
        return getI18nMessage(name, null);
    }

    /**
     * 业务服务
     */
    @Autowired
    protected S service;

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    protected M get(@PathVariable("id") String id){
        return service.getById(id);
    }

    @GetMapping
    protected List<M> list(M m){
        return service.list(new QueryWrapper<M>(m) );
    }

    /**
     * 根据分页（排序）,条件查询
     * {
     *  conditions:[{property:"",operate:"",value:""}]
     *  page: 1
     *  limit: 10
     *  sort: [{"property":"","direction":"DESC"}]
     * }
     * @param page
     * @param wrapper
     * @return
     */
    @GetMapping("/pager")
    protected IPage<M> getPage(@QueryPage Page<M> page, @QueryCondition(domain = Domain.class) QueryWrapper<M> wrapper){
        return service.page(page, wrapper);
    }

    /**
     * 保存或修改(记得新增时id为null)
     *  MIME: application/json
     * @param o
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    protected boolean saveOrUpdate(@RequestBody M o){
        return service.saveOrUpdate(o);
    }

    /**
     * MIME: x-www-form-urlencoded
     * @param o
     * @return
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected boolean saveOrUpdate2(M o){
        return service.saveOrUpdate(o);
    }

    /**
     * 根据主键修改（目前没用到）
     * @param o
     * @return
     */
    @PutMapping
    protected boolean update(@RequestBody M o,@QueryCondition(domain = Domain.class) QueryWrapper<M> wrapper){
        return service.update(o,wrapper);
    }

    /**
     * 根据主键修改
     * @param o
     * @return
     */
    @PatchMapping
    protected boolean patchUpdate(@RequestBody M o){
        return service.updateById(o);
    }

    /**
     * 根据主键删除
     * @param id
     */
    @DeleteMapping("/{id}")
    protected void delete(@PathVariable("id") String id){
        service.removeById(id);
    }

}
