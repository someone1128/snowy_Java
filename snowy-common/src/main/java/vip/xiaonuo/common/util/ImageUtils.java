package vip.xiaonuo.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄志源大魔王
 * @date 2023/5/26 5:58
 * @project aigc-java-server
 * @company 智影科技
 * @description
 */
public class ImageUtils {

	/**
	 * 将指定 url 图片转换为 BufferedImage
	 *
	 * @param imageUrl
	 * @return
	 */
	public static BufferedImage getImageFromUrl(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}


	public static List<BufferedImage> getImagesFromUrls(List<String> imageUrls) {

		List<BufferedImage> imageList = new ArrayList<>();

		try {
			for (String imageUrl : imageUrls) {
				URL url = new URL(imageUrl);
				BufferedImage image = ImageIO.read(url);
				imageList.add(image);
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return imageList;
	}

	/**
	 * 每行图片的数量
	 *
	 * @param images
	 * @param columns
	 * @return
	 */
	public static BufferedImage concatenateImages(List<BufferedImage> images, int columns) {
		List<List<BufferedImage>> splitList = ListUtils.splitList(images, columns);
		List<BufferedImage> acrossImages = new ArrayList<>();
		// 左右拼接
		for (List<BufferedImage> list : splitList) {
			acrossImages.add(mergeImages(list));
		}
		// 上下拼接响应
		return mergeImagesVertically(acrossImages);
	}

	/**
	 * 左右拼接图片
	 *
	 * @param images
	 * @return
	 */
	public static BufferedImage mergeImages(List<BufferedImage> images) {
		int width = images.stream().mapToInt(BufferedImage::getWidth).sum(); // 获取所有图片的总宽度
		int height = images.get(0).getHeight(); // 假设所有图片高度相同
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // 创建新的空白图像
		Graphics2D g2d = result.createGraphics();
		int x = 0;
		for (BufferedImage image : images) {
			g2d.drawImage(image, x, 0, null); // 将每张图片绘制到新的空白图像上
			x += image.getWidth();
		}
		g2d.dispose(); // 释放图像资源
		return result;
	}

	/**
	 * 上下拼接图片
	 *
	 * @param images
	 * @return
	 */
	public static BufferedImage mergeImagesVertically(List<BufferedImage> images) {
		int width = images.get(0).getWidth(); // 假设所有图片宽度相同
		int height = images.stream().mapToInt(BufferedImage::getHeight).sum(); // 获取所有图片的总高度
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // 创建新的空白图像
		Graphics2D g2d = result.createGraphics();
		int y = 0;
		for (BufferedImage image : images) {
			g2d.drawImage(image, 0, y, null); // 将每张图片绘制到新的空白图像上
			y += image.getHeight();
		}
		g2d.dispose(); // 释放图像资源
		return result;
	}


	/**
	 * 将指定图片按照指定宽高进行缩放
	 *
	 * @param originalImage
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
		// 获取原始图片的宽高比
		double ratio = (double) originalImage.getWidth() / (double) originalImage.getHeight();
		// 根据新的宽度计算新的高度
		int newHeight = (int) (width / ratio);
		// 计算最近的 2 的幂的尺寸
		int newWidth = Integer.highestOneBit(width - 1) << 1;
		newHeight = Integer.highestOneBit(newHeight - 1) << 1;
		// 创建新的空白图像
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resizedImage.createGraphics();
		// 设置插值算法
		Object hint = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
		if (newWidth < originalImage.getWidth() || newHeight < originalImage.getHeight()) {
			hint = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
		}
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		// 绘制原始图片到新的空白图像上
		g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
		// 执行多次的较小的缩放
		while (newWidth != width || newHeight != height) {
			int prevW = newWidth, prevH = newHeight;
			newWidth = Math.max(newWidth >> 1, width);
			newHeight = Math.max(newHeight >> 1, height);
			BufferedImage tmp = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			g2d = tmp.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2d.drawImage(resizedImage, 0, 0, newWidth, newHeight, 0, 0, prevW, prevH, null);
			g2d.dispose();
			// 释放原来的图像资源
			resizedImage.flush();
			resizedImage = tmp;
		}
		g2d.dispose();
		return resizedImage;
	}

	/**
	 * 遍历制定文件夹下的png图片
	 *
	 * @param folderPath
	 * @return
	 */
	public static List<BufferedImage> readImagesFromFolder(String folderPath) {
		List<BufferedImage> images = new ArrayList<>();
		File folder = new File(folderPath);
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
			for (File file : files) {
				try {
					BufferedImage image = ImageIO.read(file);
					images.add(image);
					System.out.println("读取图片：" + file.getAbsolutePath());
				} catch (IOException ex) {
					System.err.println("读取图片出错：" + file.getAbsolutePath());
				}
			}
		} else {
			System.err.println("文件夹不存在：" + folderPath);
		}
		return images;
	}

	/**
	 * 遍历指定文件夹下的 png 图片并且返回文件名称
	 *
	 * @param folderPath
	 * @return
	 */
	public static Map<String, BufferedImage> readImagesFromFolder2(String folderPath) {
		Map<String, BufferedImage> images = new HashMap<>();
		File folder = new File(folderPath);
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
			for (File file : files) {
				try {
					BufferedImage image = ImageIO.read(file);
					images.put(file.getName(), image);
					System.out.println("读取图片：" + file.getAbsolutePath());
				} catch (IOException ex) {
					System.err.println("读取图片出错：" + file.getAbsolutePath());
				}
			}
		} else {
			System.err.println("文件夹不存在：" + folderPath);
		}
		return images;
	}
}
