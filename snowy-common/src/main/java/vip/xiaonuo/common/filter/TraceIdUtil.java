package vip.xiaonuo.common.filter;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author Administrator
 */
public class TraceIdUtil {

    public static final String TRACE_ID = "traceId";

    /**
     * 请求设置 TRACE_ID
     */
    public static void setRequestId() {
        MDC.put(TRACE_ID, newTraceId());
    }

    /**
     * 当子线程的父线程不存在 TraceId 情况下设置TraceId
     */
    public static void setRequestIdByParentThreadNull() {
        MDC.put(TRACE_ID, newTraceId() + "-ParentThreadNull");
    }

    public static String newTraceId() {
        String str = "-";
        return UUID.randomUUID().toString().replaceAll(str, "");
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void clear() {
        MDC.clear();
    }

}
