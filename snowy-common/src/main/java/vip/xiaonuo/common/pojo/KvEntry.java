package vip.xiaonuo.common.pojo;

import lombok.Data;

@Data
public class KvEntry<K, V> {

	private K key;

	private V value;

	public KvEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

}
