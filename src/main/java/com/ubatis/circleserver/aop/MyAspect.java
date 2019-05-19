package com.ubatis.circleserver.aop;

import com.ubatis.circleserver.anno.*;
import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.basic.MyParams;
import com.ubatis.circleserver.config.SysConfig;
import com.ubatis.circleserver.exception.ValidException;
import com.ubatis.circleserver.util.DateUtil;
import com.ubatis.circleserver.util.MyMapCache;
import com.ubatis.circleserver.util.common.CS;
import com.ubatis.circleserver.util.daoutils.MyAopDao;
import com.ubatis.circleserver.util.sign.MySignUtil;
import com.ubatis.circleserver.util.token.*;
import io.netty.util.internal.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/** 验证切面
 * Created by lance on 2017/4/21.
 */
@Aspect
@Component
public class MyAspect {

    private final static Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Autowired
    private SysConfig sysConfig;
    @Autowired
    private MySignUtil mySignUtil;
    @Autowired
    private LatestTokenUtil latestTokenUtil;
    @Autowired
    private OauthTokenUtil oauthTokenUtil;
    // @Autowired
    // private MyAopDao dao;

    @Pointcut("execution(public * com.ubatis.circleserver.modules.*.controller.*.*(..))")
    public void pointcut() {

    }

    /**
     * 打印http请求信息
     * @param joinPoint
     */
    @Before(value = "pointcut()")
    public void requestInfo(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        // response.setHeader("Access-Control-Allow-Origin", "*");
        // 在WebMvcConfig.java 里设置跨域相关配置
        logger.info("url={}", request.getRequestURL());
        logger.info("method={}", request.getMethod());
        logger.info("ip={}", request.getRemoteAddr());
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("args={}", joinPoint.getArgs());
    }

    /**
     * 检查必需参数
     * @param joinPoint
     */
    @Before(value = "pointcut() && @annotation(requireParams)")
    public void requireParams(JoinPoint joinPoint, RequireParams requireParams){
        logger.info("要求的参数：{}",requireParams.value());
        String[] params = requireParams.value().split(",");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        for(String param: params){
            if(StringUtils.isEmpty(request.getParameter(param.trim()))){
                throw new ValidException(CS.MISS_PARAMS, param.trim());
            }
        }
    }

    /**
     * 验证 latest token
     * @param jp
     * @param requireLatestToken
     * @return
     * @throws Throwable
     */
    @Around(value = "pointcut() && @annotation(requireLatestToken)")
    public Object validLatestToken(ProceedingJoinPoint jp, RequireLatestToken requireLatestToken) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //需要验证LatestToken
        String client_id = request.getHeader("client_id");
        String uid = request.getHeader("uid");
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(client_id)){
            throw new ValidException(CS.MISS_PARAMS, "headers 缺少 client_id");
        }
        if(StringUtils.isEmpty(uid)){
            throw new ValidException(CS.MISS_PARAMS, "headers 缺少 uid");
        }
        if(StringUtils.isEmpty(token)){
            throw new ValidException(CS.MISS_PARAMS, "headers 缺少 token");
        }
        // 检查是否需要本人
        String uidkey = requireLatestToken.uidkey();
        if (!StringUtil.isNullOrEmpty(uidkey)) {
            for (Object paramObj: jp.getArgs()) {
                logger.info("===paramObj.getClass().getSimpleName()=={}", paramObj.getClass().getSimpleName());
                if (paramObj instanceof MyParams) {
                    Method[] methods = paramObj.getClass().getMethods();
                    for (Method method: methods){
                        if (method.getName().startsWith("set")
                                && method.getName().substring(3).toLowerCase().equals(uidkey.toLowerCase())) {
                            method.invoke(paramObj, uid);
                        }
                    }
                }
            }
        }
        LatestResult beforeLatestResult = latestTokenUtil.valid(client_id,uid,token);
        logger.info("beforeLatestResult:{}", beforeLatestResult.toString());
        if(beforeLatestResult.getCode() != CS.SUCCESS){
            throw new ValidException(beforeLatestResult.getCode(),beforeLatestResult.getMsg());
        }
        JsonBase jsonBase = (JsonBase) jp.proceed();
        if (jsonBase != null) {
            // 当前最新token。因为任务可能耗时比较长。
            LatestToken latestToken = latestTokenUtil.getLatestToken(client_id,uid);
            logger.info("latestToken:{}", latestToken.toString());
            if(latestToken != null){
                jsonBase.setLatest_token(latestToken);
            }
        }
        return jsonBase;
    }

    /**
     * 验证 oath token
     * @param jp
     * @param requireToken
     * @throws Throwable
     */
    @Before(value = "pointcut() && @annotation(requireToken)")
    public void validToken(JoinPoint jp, RequireToken requireToken) throws Throwable {
        if(!sysConfig.isValid_token()) return;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        OauthValidResult oauthValidResult = null;

        String uid = request.getHeader("uid");
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(uid)){
            throw new ValidException(CS.MISS_PARAMS, "uid");
        }
        if(StringUtils.isEmpty(token)){
            throw new ValidException(CS.MISS_PARAMS, "token");
        }
        oauthValidResult = oauthTokenUtil.valid(uid, token);
        logger.info("oauthValidResult:{}",oauthValidResult.getCode());
        if(oauthValidResult.getCode() != CS.SUCCESS){
            throw new ValidException(oauthValidResult.getCode(),oauthValidResult.getMsg());
        }
    }

    /**
     * 验证签名
     * @param jp
     * @param requireSign
     * @throws Throwable
     */
    @Before(value = "pointcut() && @annotation(requireSign)")
    public void validSign(JoinPoint jp, RequireSign requireSign) throws Throwable {
        if(!sysConfig.isValid_sign()) return;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //
        String uid = request.getHeader("uid");
        String sign = request.getHeader("sign");
        String timestamp = request.getHeader("timestamp");
        if(StringUtils.isEmpty(uid)){
            throw new ValidException(CS.MISS_PARAMS, "headers 缺少 uid");
        }
        if(StringUtils.isEmpty(sign)){
            throw new ValidException(CS.MISS_PARAMS, "headers 缺少 sign");
        }
        if(StringUtils.isEmpty(timestamp)){
            throw new ValidException(CS.MISS_PARAMS, "headers 缺少 timestamp");
        }
        if(Math.abs(Integer.parseInt(timestamp) - DateUtil.getCurrentTimestamp()) > 60 * 20){
            //20分钟内
            throw new ValidException(CS.SIGN_REQUEST_FAIL, "签名时间戳过期");
        }
        // 检查是否需要本人
        String uidkey = requireSign.uidkey();
        if (!StringUtil.isNullOrEmpty(uidkey)) {
            if (!uid.equals(request.getParameter(uidkey))) throw new ValidException(CS.NO_PERMISSION, "不是本人");
        }
        // 检测 用户id是否合法。要包含staff表的
        if(!MyMapCache.getInstance().userIdSet.contains(uid)){
            Map userMap = null;// dao.queryForMap("SELECT id FROM " + TableName.CRM_USER + " WHERE id = ? ", new Object[]{uid}, new int[]{Types.VARCHAR});
            Map staffMap = null; // dao.queryForMap("SELECT id FROM " + TableName.SYS_STAFF + " WHERE id = ? ", new Object[]{uid}, new int[]{Types.VARCHAR});
            // logger.info("userMap: {}", userMap);
            // logger.info("staffMap: {}", staffMap);
            if(userMap == null && staffMap == null){
                throw new ValidException(CS.INVALID_PARAMS, "用户 不存在");
            }
            if(MyMapCache.getInstance().userIdSet.size() >= 1000000){
                MyMapCache.getInstance().userIdSet.clear();
            }
            MyMapCache.getInstance().userIdSet.add(uid);
        }
        if (!MyMapCache.getInstance().userIdSet.contains(uid)) {
            throw new ValidException(CS.INVALID_PARAMS, "非法uid");
        }
        //
        boolean ret = mySignUtil.valid(sign, uid, timestamp);
        if(!ret){
            throw new ValidException(CS.SIGN_REQUEST_FAIL, "签名错误");
        }
    }

    /**
     * 验证角色、权限
     * @param jp
     * @param requireRoles
     * @throws Throwable
     */
    @Before(value = "pointcut() && @annotation(requireRoles)")
    public void validRoles(JoinPoint jp, RequireRoles requireRoles) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String remark = requireRoles.remark();//如果其他用户也要校验权限，从remark区分
        if (StringUtil.isNullOrEmpty(remark)) remark = "sys_staff";
        String[] needRoles = requireRoles.value().split(",");

        String uid = request.getHeader("uid");
        if(StringUtils.isEmpty(uid)){
            throw new ValidException(CS.MISS_PARAMS, "header 缺少 uid");
        }
        // TODO 做一下缓存
        String rolesStr = null; // systemService.getRoles(uid);
        if (StringUtil.isNullOrEmpty(rolesStr)) throw new ValidException(CS.NO_PERMISSION, "没有权限");
        String[] userRoles = rolesStr.split(",");

        for (String userRole : userRoles) {
            for (String needrole: needRoles) {
                if (userRole.trim().equals(needrole.trim())) {
                    return;
                }
            }
        }

        throw new ValidException(CS.NO_PERMISSION, "没有权限");
    }

}
