package vip.xiaonuo.common.pojo;

public class ElapsedTimeStat {

	private final long start;

	public ElapsedTimeStat() {
		this.start = System.currentTimeMillis();
	}

	public long close() {
		return System.currentTimeMillis() - start;
	}

}