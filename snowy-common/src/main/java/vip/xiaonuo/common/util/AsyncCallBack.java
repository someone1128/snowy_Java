package vip.xiaonuo.common.util;

import java.io.IOException;

public interface AsyncCallBack {
	void onResponse(String paramString)
			throws IOException;

	void onFailure(String paramString);
}
