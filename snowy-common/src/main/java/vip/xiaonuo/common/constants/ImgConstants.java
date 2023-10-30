package vip.xiaonuo.common.constants;


import cn.hutool.core.util.RandomUtil;

/**
 * @author 黄志源大魔王
 * @date 2023/5/6 15:48
 * @project snowy-master
 * @company 智影科技
 * @description
 */
public class ImgConstants {

	/**
	 * 用户注册头像List
	 */
	public static final String[] MERCHANT_IMAGE_LIST = {
			"https://pic4.zhimg.com/80/v2-67cda9baadac56cf988a43fed69061a3_720w.jpg",
			"https://pic4.zhimg.com/80/v2-bde2c54de689340f237023ccd35c517f_720w.jpg",
			"https://pic1.zhimg.com/80/v2-d2ff001eabe8e8d676fad10cb9588034_720w.jpg",
			"https://pic1.zhimg.com/80/v2-7fdca316d0285b4fbcd056e81e334130_720w.jpg",
			"https://pic3.zhimg.com/80/v2-2693a19167ecc917cd53cf0d4cd9e64e_720w.jpg",
			"https://pic1.zhimg.com/80/v2-a2c61c9d72df0ccb7c4d7dc13d4c9e44_720w.jpg",
			"https://pic3.zhimg.com/80/v2-5a3332c716a22f71a0a539a7eefc4382_720w.jpg",
			"https://pic2.zhimg.com/80/v2-8f594f867fde6e69e4ea7c47f6be5fc9_720w.jpg",
			"https://pic2.zhimg.com/80/v2-346fa40f020201936619ac0e7efa8169_720w.jpg",
			"https://pic1.zhimg.com/80/v2-77f92661ee5c9523637de586078b3fd4_720w.jpg",
			"https://pic3.zhimg.com/80/v2-063eaeec4c8a5b923dbc41453b4a1342_720w.jpg",
			"https://pic2.zhimg.com/80/v2-6ab5c7b9b0f64a34ce972b0b45c22c01_720w.jpg",
			"https://pic3.zhimg.com/80/v2-a499c55e5205bc527db8866ece109852_720w.jpg",
			"https://pic3.zhimg.com/80/v2-e5e85431f6aa831ed83fa1fe3dc4959a_720w.jpg",
			"https://pic4.zhimg.com/80/v2-217ed24607944fe081eec42a8fe12ad3_720w.jpg",
			"https://pic2.zhimg.com/80/v2-bcbcbdf39359e309db0b28f00c1836f9_720w.jpg",
			"https://pic4.zhimg.com/80/v2-d27197faa13f958673c2d57b710910b3_720w.jpg",
			"https://pic1.zhimg.com/80/v2-5270503db96ea68c6f58d6a85feb68f8_720w.jpg",
			"https://pic1.zhimg.com/80/v2-8df802dc23a3d6f1913a8d73a555c2d4_720w.jpg",
			"https://pic3.zhimg.com/80/v2-c40b4fe8c1de752b1ae7f89564ede036_720w.jpg",
			"https://pic4.zhimg.com/80/v2-5b9aefc0b5ea34d77934338689141e4f_720w.jpg",
	};

	/**
	 * 随机获取一个头像
	 *
	 * @return
	 */
	public static final String getRandomImg() {
		return MERCHANT_IMAGE_LIST[RandomUtil.randomInt(0, MERCHANT_IMAGE_LIST.length)];
	}

}
