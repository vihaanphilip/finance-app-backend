package com.vphilip.finance.app.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(name = "app.logging.requests.enabled", havingValue = "true", matchIfMissing = true)
public class RequestLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        log.debug("[REQ] {} args={}", method, Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.debug("[RES] {} elapsed={}ms", method, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable t) {
            log.debug("[ERR] {} elapsed={}ms error={}", method, System.currentTimeMillis() - start, t.getMessage());
            throw t;
        }
    }
}
