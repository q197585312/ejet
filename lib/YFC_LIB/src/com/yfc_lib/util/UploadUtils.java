package com.yfc_lib.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.yfc_lib.bean.UploadFileInfo;
import com.yfc_lib.util.image.ImageUtil;

/**
 * 
 * 实现文件上传的工具类
 * 
 */
public class UploadUtils {
	public final static int			GET			= 0;
	public final static int			POST		= 1;
	
	private static final int		TIME_OUT	= 120000;
	private static final String		CHARSET		= "UTF-8";
	
	private HttpURLConnection		conn;
	private OutputStream			out;
	private DataOutputStream		dos;
	
	private OnUploadFileLisenter	onUploadFileLisenter;
	
	public interface OnUploadFileLisenter {
		
		public void msg(String msg);
		
		public void fileSchedule(int index, int nowLength, int fileLength);
		
		public void success(String result);
		
		public void error(String error);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler	handler	= new Handler() {
								public void handleMessage(android.os.Message msg) {
									if (onUploadFileLisenter != null) {
										switch (msg.what) {
											case 0:
												onUploadFileLisenter.msg((String) msg.obj);
												break;
											case 1:
												onUploadFileLisenter.fileSchedule(
													(Integer) msg.obj, msg.arg1, msg.arg2);
												break;
											case 2:
												onUploadFileLisenter.success((String) msg.obj);
												break;
											case 3:
												onUploadFileLisenter.error((String) msg.obj);
												break;
											default:
												break;
										}
									}
								};
							};
	
	public void close() {
		try {
			dos.close();
			out.close();
			conn.disconnect();
		} catch (Exception e) {
			Logger.e(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param method
	 *            get --> 0 post --> 1 method只作用于参数的提交方式
	 * @param list
	 *            文件对象集合
	 * @param context
	 * @param requestURL
	 *            服务器地址
	 * @param isImage
	 *            是否是图片文件 如果是的话会对图片进行一些处理 压缩图片减小图片大小 增加传递速度
	 * @param l
	 *            监听器
	 * @param strings
	 *            参数
	 */
	public void uploadFile(int method, List<UploadFileInfo> list, Context context,
							String requestURL, boolean isImage, OnUploadFileLisenter l,
							String... strings) {
		onUploadFileLisenter = l;
		
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--";
		String LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		
		try {
			URL url = null;
			switch (method) {
				case GET:
					url = new URL(requestURL + getParameterStr(strings));
					break;
				case POST:
					url = new URL(requestURL);
					break;
				default:
					break;
			}
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
			
			boolean flag = true;
			
			if (list != null && list.size() > 0) {// 当文件不为空，把文件包装并且上传
				out = conn.getOutputStream();
				dos = new DataOutputStream(out);
				
				List<UploadFileInfo> myList = null;
				
				if (isImage) {
					// 处理图片大小
					myList = handleFile(list, context);
				} else {
					myList = list;
				}
				
				switch (method) {
					case GET:
						break;
					case POST:
						dos.write(postParameterStr(BOUNDARY, PREFIX, LINE_END, strings).getBytes(
							CHARSET));
						break;
					default:
						break;
				}
				
				for (int i = 0; i < myList.size(); i++) {
					UploadFileInfo ufi = myList.get(i);
					if (ufi.getFile() != null) {
						StringBuffer sb = new StringBuffer();
						sb.append(PREFIX + BOUNDARY + LINE_END);
						// 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
						// filename是文件的名字，包含后缀名的 比如:abc.png
						sb.append("Content-Disposition: form-data; name=\"" + ufi.getFileName()
									+ "\"; filename=\"" + ufi.getFile().getName() + "\"" + LINE_END);
						sb.append("Content-Type: application/octet-stream; charset=" + CHARSET
									+ LINE_END);
						sb.append(LINE_END);
						dos.write(sb.toString().getBytes());
						File f = ufi.getFile();
						
						long fileLength = f.length();
						long nowLength = 0;
						
						InputStream is = new FileInputStream(f);
						byte[] bytes = new byte[1024];
						int len = 0;
						
						while ((len = is.read(bytes)) != -1) {
							if (flag) {
								nowLength += len;
								
								Message msg = new Message();
								msg.what = 1;
								msg.arg1 = (int) (nowLength / 1024);
								msg.arg2 = (int) (fileLength / 1024);
								msg.obj = i + 1;
								handler.sendMessage(msg);
							}
							
							dos.write(bytes, 0, len);
							dos.flush();
						}
						is.close();
						dos.write(LINE_END.getBytes());
					}
				}
				
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
				dos.write(end_data);
				
				dos.flush();
				dos.close();
				out.flush();
				out.close();
			} else {
				flag = false;
			}
			
			if (flag) {
				Message msg = new Message();
				msg.obj = "正在等待服务器响应,请稍候...";
				msg.what = 0;
				handler.sendMessage(msg);
			}
			
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				String result = "";
				
				InputStream in = conn.getInputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = in.read(bytes)) != -1) {
					result += new String(bytes, 0, len, CHARSET);
				}
				in.close();
				
				Message msg = new Message();
				msg.obj = result;
				msg.what = 2;
				handler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.obj = responseCode + "";
				msg.what = 3;
				handler.sendMessage(msg);
			}
			
			conn.disconnect();
		} catch (Exception e) {
			Logger.e(e.getMessage());
			Message msg = new Message();
			msg.obj = e.getMessage();
			msg.what = 3;
			handler.sendMessage(msg);
		}
	}
	
	public static String getParameterStr(String... strings) {
		String result = "";
		if (strings != null && strings.length % 2 == 0) {
			for (int i = 0; i < strings.length - 1; i += 2) {
				String key = strings[i];
				String value = strings[i + 1];
				if (key != null && !"".equals(key) && value != null && !"".equals(value)) {
					if (i != 0 && !"".equals(result)) {
						result += "&";
					}
					result += strings[i] + "=" + strings[i + 1];
				}
			}
			if (!"".equals(result)) {
				result = "?" + result;
			}
		}
		return result;
	}
	
	private static String postParameterStr(String BOUNDARY, String PREFIX, String LINE_END,
											String... strings) {
		StringBuilder sb = new StringBuilder();
		if (strings != null && strings.length % 2 == 0) {
			for (int i = 0; i < strings.length; i += 2) {
				sb.append(PREFIX + BOUNDARY + LINE_END);
				sb.append("Content-Disposition: form-data;  name=\"" + strings[i] + "\"" + LINE_END);
				sb.append(LINE_END);
				sb.append(strings[i + 1] + LINE_END);
			}
		}
		return sb.toString();
	}
	
	private List<UploadFileInfo> handleFile(List<UploadFileInfo> list, Context context) {
		List<UploadFileInfo> myList = new ArrayList<UploadFileInfo>();
		String saveDir = ImageUtil.getCacheDir(context);
		
		boolean flag = true;
		// if (list.size() > 1) {
		// flag = true;
		// }
		
		for (int i = 0; i < list.size(); i++) {
			if (flag) {
				Message msg = new Message();
				if (list.size() == 1) {
					msg.obj = "正在处理图片,请稍候.";
				} else {
					msg.obj = "正在处理第" + (i + 1) + "张图片,请稍候.";
				}
				msg.what = 0;
				handler.sendMessage(msg);
			}
			UploadFileInfo info = list.get(i);
			File f = info.getFile();
			String path = f.getAbsolutePath();
			String saveName = path.hashCode() + ImageUtil.getImageSuffix(path);
			Bitmap bitmap = ImageUtil.getBitmap(f, 800);
			bitmap = ImageUtil.compressImage(bitmap);
			info.setFile(ImageUtil.saveBitmap(saveDir + File.separator + saveName, bitmap, true));
			myList.add(info);
		}
		return myList;
	}
}