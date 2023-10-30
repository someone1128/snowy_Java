package vip.xiaonuo.common.thread;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import vip.xiaonuo.common.filter.TraceIdUtil;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author 黄志源大魔王
 * @date 2023/1/9 15:47
 * @project manager_system_server_axh
 * @company 弘瑞创享
 * @description
 */
@Slf4j
public class MdcThreadPoolExecutor extends ThreadPoolExecutor {

	public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}

	public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}

	public MdcThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	@Override
	public void execute(Runnable task) {
		//复制主线程MDC
		Map<String, String> context = MDC.getCopyOfContextMap();
		super.execute(() -> {
			//主线程MDC赋予子线程
			childThreadRenameMdcTraceId(context);
			try {
				task.run();
			} finally {
				try {
					TraceIdUtil.clear();
				} catch (Exception e) {
					//log.warn("MDC clear exception：{}", e.getMessage());
				}
			}
		});

	}

	/**
	 * 设置父线程的 MDC 上下文到子线程中
	 * 并重命名子线程的 TraceId，重命名格式：父线程TraceId-子线程—{子线程名称}
	 *
	 * @param context
	 */
	private void childThreadRenameMdcTraceId(Map<String, String> context) {
		if (null != context) {
			String traceId = context.get(TraceIdUtil.TRACE_ID) + "-子线程{}";
			context.put(TraceIdUtil.TRACE_ID, StrUtil.format(traceId, Thread.currentThread().getName()));
			MDC.setContextMap(context);
			return;
		}
		//主线程没有MDC就自己生成一个
		TraceIdUtil.setRequestIdByParentThreadNull();
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		Map<String, String> context = MDC.getCopyOfContextMap();
		return super.submit(() -> {
			childThreadRenameMdcTraceId(context);     //主线程MDC赋予子线程
			try {
				return task.call();
			} finally {
				try {
					TraceIdUtil.clear();
				} catch (Exception e) {
					//log.warn("MDC clear exception：{}", e.getMessage());
				}
			}
		});
	}

}
