package top.mnsx.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.mnsx.annotation.SystemLog;
import top.mnsx.constants.SystemConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(top.mnsx.annotation.SystemLog)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        StringBuilder sb = new StringBuilder();

        try {
            before(joinPoint, sb);
            result = joinPoint.proceed();
            after(result, sb);
        } finally {
            // 结束后换行
            sb.append("=======End=======").append(System.lineSeparator());
            // 控制台输出日志
            log.info("{}", sb);
            // 文本保存日志
            logFile(sb);
        }

        return result;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void cronDeleteFile() throws IOException {
        String path = SystemConstants.LOG_LOCATION + "log";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
    }

    private void logFile(StringBuilder sb) throws IOException {
        String path = SystemConstants.LOG_LOCATION + "log.txt";
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        new Thread(() -> {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(path, "rw");
                long length = raf.length();
                raf.seek(length);
                raf.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (raf != null) {
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void before(ProceedingJoinPoint joinPoint, StringBuilder sb) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        SystemLog systemLog = getSystemLog(joinPoint);

        sb.append("\n=======Start=======\n");
        // 打印请求 URL
        sb.append("URL            : ").append(request.getRequestURL()).append("\n");
        // 打印描述信息
        sb.append("BusinessName   : ").append(systemLog.businessName()).append("\n");
        // 打印 Http method
        sb.append("HTTP Method    : ").append(request.getMethod()).append("\n");
        // 打印调用 controller 的全路径以及执行方法
        sb.append("Class Method   : ").append(joinPoint.getSignature().getDeclaringTypeName()).append(".")
                .append((joinPoint.getSignature()).getName()).append("\n");
        // 打印请求的 IP
        sb.append("IP             : ").append(request.getRemoteHost()).append("\n");
        // 打印请求入参
        sb.append("Request Args   : ").append(JSON.toJSONString(joinPoint.getArgs())).append("\n");
    }

    private void after(Object result, StringBuilder sb) {
        // 打印出参
        sb.append("Response       : ").append(JSON.toJSONString(result)).append("\n");
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(SystemLog.class);
    }
}
