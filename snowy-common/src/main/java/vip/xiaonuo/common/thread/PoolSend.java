package vip.xiaonuo.common.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.util.LogPrintUtils;
import vip.xiaonuo.common.util.StringUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author as
 */
@Component
@Slf4j
public class PoolSend {

	/**
	 * 任务队列
	 */
	private final BlockingQueue<Runnable> workQueue;

	/**
	 * 线程池
	 */
	private final MdcThreadPoolExecutor es;

	/**
	 * 开始时间
	 */
	private final Long startTime;

	public PoolSend() {
		// 注入ioc容器中的线程池
		this(32, 200);
	}

	public PoolSend(Integer poolSize) {
		this(poolSize, poolSize);
	}

	/**
	 * @param corePoolSize    初始化线程数量、核心线程数
	 * @param maximumPoolSize 最大线程、非核心线程数
	 */
	public PoolSend(Integer corePoolSize, Integer maximumPoolSize) {
		startTime = System.currentTimeMillis();
		/*
		  构造无界的任务队列，资源足够，理论可以支持无线个任务
		  不过如果并发量很大的话，最好指定一下队列的长度，否则把所有东西放进队列里面
		  很可能会导致内存溢出
		 */
		workQueue = new LinkedBlockingQueue<>(maximumPoolSize * 10);
		/*
		 * 1、初始化线程数量
		 * 2、最大线程
		 * 3、线程存活时间
		 * 4、时间单位
		 * 5、阻塞队列，用来存储等待执行的任务
		 * 6、拒绝策略
		 */
		es = new MdcThreadPoolExecutor(corePoolSize, maximumPoolSize,
				120, TimeUnit.SECONDS, workQueue,
				new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());

	}

	public void send(Runnable task) {
		LogPrintUtils.info(log, "线程池 = " + es);
		LogPrintUtils.info(log, "任务 = " + task);
		es.execute(() -> {
			try {
				LogPrintUtils.info(log, "==========将任务放入线程池提交，调用者：【{}】", StringUtil.getInvokeName());
				task.run();
			} catch (Exception e) {
				log.error("线程池子线程执行异常：", e);
			}
		});
	}

	public void send(Runnable task, String description) {
		LogPrintUtils.info(log, "线程池 = " + es);
		LogPrintUtils.info(log, "任务 = " + task);
		es.execute(() -> {
			LogPrintUtils.info(log, "==========将任务放入线程池提交，调用者：【{}】，描述【{}】", StringUtil.getInvokeName(), description);
			task.run();
		});
	}

	/**
	 * 关闭线程池
	 */
	public void close() {
		es.shutdown();
		//等待所有任务都执行结束
		while (true) {
			//所有的子线程都结束了
			if (es.isTerminated()) {
				LogPrintUtils.info(log, "共耗时:" + (System.currentTimeMillis() - startTime) / 1000 + "s");
				break;
			}
		}
	}

	/**
	 * 判断当前线程池是否有线程执行
	 */
	public Boolean isPoolToExecute() {
		return es.getActiveCount() != 0;
	}
}