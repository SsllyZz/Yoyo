package com.yoyo.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
/**
 * 上传工具类
 * spring mvn支持
 */
public class UploadUtil {


	public static String fileUpload(MultipartFile file) throws Exception{
		// 判断是否有上传文件
		if (Objects.isNull(file) || file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
			return null;
		}
		String savePath = "picture"; // 保存文件的相对目录
		String fileName = file.getOriginalFilename();
		// 文件存储路径
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+savePath;
		// 获取当前文件类型
		String type = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());

		// 使用UUID生成文件名，确保唯一性
		String newFileName = UUID.randomUUID().toString() + "." + type;

		// 按指定路径重命名构建文件
		File savefile = new File(path, newFileName);
		// 若保存文件的文件夹不存在则创建
		if (!savefile.getParentFile().exists()) {
			savefile.getParentFile().mkdirs();
		}
		System.out.println("上传文件绝对路径: " + savefile.getPath());
		file.transferTo(savefile);
		return savePath + "/" + newFileName;
	}


}
