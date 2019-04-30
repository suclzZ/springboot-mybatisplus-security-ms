spring-security:
一般的配置
@EnableWebSecurity + extends WebSecurityConfigurerAdapter
认证原理：
    /login(默认DefaultLoginPageGeneratingFilter，针对/login请求拦截，进入 默认页面)-> 
    进入登录页面(将登录界面请求修改非/login，同时不进行拦截) -> 
    进入过滤器(默认UsernamePasswordAuthenticationFilter，对/login POST进行过滤) -> 
    生成UsernamePasswordAuthenticationToken(用户输入的信息进行组织) ->
    默认通过DaoAuthenticationProvider对Authentication做一些处理和校验 ->
    调用对应的UserDetailsService生成UserDetails ->
    AuthenticationManager进行验证
    
    对于单表或多表，我们可以仅使用一个UserDetailsService，通过尝试的方式在一张表多个字段或多张表不同条件进行尝试查询即可。
    对于不同类型的登录，比如通过form、token、aouth、cas等方式认证，我们需要配置一套服务：
    ①Filter 仿造UsernamePasswordAuthenticationFilter,可以定义不同请求拦截，与shiro中filter一样
    ②Authentication，即UsernamePasswordAuthenticationToken，这个也和shiro中的token一样
    ③AuthenticationProvider，类似于shiro中的realm，会根据不同的Authentication进行不同的处理
    ④UserDetailsService，这个主要是配置AuthenticationProvider，主要是为了获取真实用户构建UserDetails
    
--------------------------------------------------------------------    
    