package cn.com.hwxt.util;

import cn.com.hwxt.pojo.EFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

public class ZipTool {
	static final int BUFFER = 2048;
	public static void main(String argv[]) throws Exception {
		unZipFiles("d:/temp/yfg/yfg-15393e9fe0a4828982a1dce82d29e581.zip" , "d:/temp/yfg/yfg-15393e9fe0a4828982a1dce82d29e581" , "UTF-8");
	}

	public static boolean unZip(InputStream is, String tempFileName) {
		boolean result = false;
		ZipInputStream zis = null;
		BufferedOutputStream dest = null;
		java.util.zip.ZipEntry entry = null;
		try {
			zis = new ZipInputStream(new BufferedInputStream(is));

			while ((entry = zis.getNextEntry()) != null) {
				int size;
				byte[] buffer = new byte[BUFFER];

				FileOutputStream fos = new FileOutputStream(tempFileName+entry.getName());
				BufferedOutputStream bos = new BufferedOutputStream(fos,buffer.length);

				while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
					bos.write(buffer, 0, size);
				}
				bos.flush();
				bos.close();
			}

			result = true;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				zis.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 生成压缩文件
	 * 
	 * @param byteArrayMap
	 *            Map<文件名 文件内容的byte数组>
	 * @param tempFileName
	 *            临时文件全名
	 * @author: www
	 */
	public static void zip(Map<String, byte[]> byteArrayMap, String tempFileName) {
		byte data[] = new byte[BUFFER];
		FileOutputStream dest = null;
		ZipOutputStream out = null;
		BufferedInputStream bis = null;
		try {
			Set<String> tableNameSet = byteArrayMap.keySet();
			dest = new FileOutputStream(tempFileName);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			out.setEncoding(System.getProperty("sun.jnu.encoding"));
			for (String tableName : tableNameSet) {
				InputStream is = new ByteArrayInputStream(byteArrayMap
						.get(tableName));
				bis = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(tableName);
				out.putNextEntry(entry);
				int count;
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				out.flush();
				bis.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				dest.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	/**
	 * 生成压缩文件
	 *
	 * @param byteArrayMap  Map<文件名 文件内容的byte数组>
	 * @param tempFileName  临时文件全名
	 * @param encoding  指定编码,如果是null 或者"" 取System.getProperty("sun.jnu.encoding")
	 * @author: www
	 */
	public static void zip(Map<String, byte[]> byteArrayMap, String tempFileName , String encoding) {
		encoding = StringUtils.isBlank(encoding) ?
				System.getProperty("sun.jnu.encoding") : encoding;
		byte data[] = new byte[BUFFER];
		FileOutputStream dest = null;
		ZipOutputStream out = null;
		BufferedInputStream bis = null;
		try {
			Set<String> tableNameSet = byteArrayMap.keySet();
			dest = new FileOutputStream(tempFileName);
			out = new ZipOutputStream(new BufferedOutputStream(dest));
			out.setEncoding(encoding);
			for (String tableName : tableNameSet) {
				InputStream is = new ByteArrayInputStream(byteArrayMap
						.get(tableName));
				bis = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(tableName);
				out.putNextEntry(entry);
				int count;
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				out.flush();
				bis.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				dest.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	/**
	 * 把文件压缩为目标文件
	 * @param dir 要压缩的目录
	 * @param zipFilePath 压缩后zip文件的路径和名称
	 * @author: www
	 * @throws Exception
	 */
	public void zip(String dir , String zipFilePath) throws Exception{
		ZipOutputStream zipOut = null;
		BufferedInputStream bis = null;
		byte data[] = new byte[BUFFER];
		InputStream is = null;

		try {
			File[] fileList = new File(dir).listFiles();
			zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
			zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
			for (File file : fileList) {
				is = new FileInputStream(file);
				bis = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
				zipOut.putNextEntry(entry);
				int count;
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					zipOut.write(data, 0, count);
				}
				zipOut.flush();
				bis.close();
				is.close();
			}
		} catch (Exception e) {
			throw new Exception("压缩错误");
		} finally {
			zipOut.close();
			FileUtils.deleteDirectory(new File(dir));//删除unzip文件夹
		}
	}
	/**
	 * 把源文件压缩成zip并且删除
	 */
	public static void zipMulitFile(List<String> fileList , String zipFilePath) throws Exception{
		ZipOutputStream zipOut = null;
		BufferedInputStream bis = null;
		byte data[] = new byte[BUFFER];
		InputStream is = null;

		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
			zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
			for (String fPath : fileList) {
				File file = new File(fPath);
				is = new FileInputStream(file);
				bis = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
				zipOut.putNextEntry(entry);
				int count;
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					zipOut.write(data, 0, count);
				}
				zipOut.flush();
				bis.close();
				is.close();
			}
		} catch (Exception e) {
			throw new Exception("压缩错误");
		} finally {
			zipOut.close();
			for (String fPath : fileList) {
				File file = new File(fPath);
				file.delete();

			}
		}
	}
	/**
	 * 压缩一个文件 
	 **/
	public void zipFile(String unzipFile , String zipFilePath) throws Exception{
		ZipOutputStream zipOut = null;
		BufferedInputStream bis = null;
		byte data[] = new byte[BUFFER];
		InputStream is = null;
		File file = new File(unzipFile);
		try {
			if(file.exists()){
				zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
				zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
				is = new FileInputStream(unzipFile);
				bis = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
				zipOut.putNextEntry(entry);
				int count;
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					zipOut.write(data, 0, count);
				}
			}
		} catch (Exception e) {
			throw new Exception("压缩错误" + e.getMessage());
		} finally {
			if(null != zipOut){
				zipOut.flush();
				zipOut.close();
			}
			if(null != bis){
				bis.close();
			}
			if(null != is){
				is.close();
			}
		}
	}

	/**
	 * 压缩一个文件 根据标识删除源文件
	 * @param unzipFile
	 * @param zipFilePath
	 * @param delSourceFile  是否删除临时文件
	 * @throws Exception
	 */
	public void zipFile(String unzipFile , String zipFilePath , Boolean delSourceFile) throws ZipFileException {
		ZipOutputStream zipOut = null;
		BufferedInputStream bis = null;
		byte data[] = new byte[BUFFER];
		InputStream is = null;
		File file = new File(unzipFile);
		try {
			if(file.exists()){
				FileUtils.touch(new File(zipFilePath));
				zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
				zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
				is = new FileInputStream(unzipFile);
				bis = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
				zipOut.putNextEntry(entry);
				int count;
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					zipOut.write(data, 0, count);
				}
			}
		} catch (Exception e) {
			throw new ZipFileException("压缩错误" + e.getMessage());
		} finally {
			try {
				if(null != bis){
                    bis.close();
                }
				if(null != is){
                    is.close();
                }
				if(delSourceFile){
                    file.delete();
                }
				if(null != zipOut){
                    zipOut.flush();
                    zipOut.close();
                }
			} catch (IOException e) {
				throw new ZipFileException("压缩文件关闭资源发生错误" + e.getMessage());
			}
		}
	}

	/**
	 * 压缩一个目录,包含子目录和文件.空文件夹将被忽略
	 * @param path
	 * @param zipFilePath
	 * @throws Exception
     */
	public void zipDir(String path, String zipFilePath) throws ZipFileException{
		ZipOutputStream zipOut = null;
		path = FilenameUtils.normalize(path + File.separator);
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
			zipOut.setEncoding(System.getProperty("sun.jnu.encoding"));
			zipDir(path , zipOut , path);
		} catch (Exception e) {
			throw new ZipFileException("压缩错误" , e);
		} finally {
			if(null != zipOut){
				try {	zipOut.close();	} catch (IOException e) {
					throw new ZipFileException("压缩文件,释放资源错误" , e);
				}
			}
		}
	}

	/**
	 * 内部递归调用的方法
	 */
	private void zipDir(String dir2zip, ZipOutputStream zos , String bashPath) throws ZipFileException{
		int count = 0;
		InputStream is = null;
		BufferedInputStream bis = null;
		byte[] data = new byte[BUFFER];
			File zipDir = new File(dir2zip);
			File[] filelist = zipDir.listFiles();
			for (File file : filelist) {
				if (file.isDirectory()) {
					zipDir(file.getPath() , zos , bashPath);
					continue;
				}
				try {
					is = new FileInputStream(file);
					bis = new BufferedInputStream(is, BUFFER);
					ZipEntry entry = new ZipEntry(file.getPath().replace(bashPath , ""));
					zos.putNextEntry(entry);
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						zos.write(data, 0, count);
					}
					if(null != zos){	zos.flush();	}
					if(null != bis){	bis.close();	}
					if(null != is){	is.close();	}
				} catch (Exception e) {
					throw new ZipFileException("压缩错误" , e);
				}finally{
					if(null != bis){
						try {	bis.close();	} catch (IOException e) {
							throw new ZipFileException("压缩文件,释放资源错误" , e);
						}
					}
					if(null != is) {
						try {	is.close();		} catch (IOException e) {
							throw new ZipFileException("压缩文件,释放资源错误" , e);
						}
					}
				}
			}
	}

	/**
	 * 解压文件.包括子目录和文件
	 * @param zipPath
	 * @param targetPath
	 * @throws IOException
	 */
	public void unZipFiles(String zipPath , String targetPath) throws ZipFileException{
		ZipFile zip = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			System.out.println(System.getProperty("sun.jnu.encoding"));
			zip = new ZipFile(new File(zipPath),"UTF-8");
			for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
				int size = 0;
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File unzipFile = new File(FilenameUtils.normalize
						(targetPath + File.separator + entry.getName()));
				if (unzipFile.isDirectory()) {
					continue;
				} else {
					try {
						FileUtils.touch(unzipFile);
						is = zip.getInputStream(entry);
						byte[] buffer = new byte[BUFFER];
						os = new FileOutputStream(unzipFile);
						bos = new BufferedOutputStream(os, buffer.length);
						while ((size = is.read(buffer, 0, buffer.length)) != -1) {
							bos.write(buffer, 0, size);
						}
						if(bos != null){	bos.flush();	}
						if(os != null){		os.flush();		}
					}catch (IOException e) {
						throw new ZipFileException("解压错误" , e);
					} finally {
						try {
							if(null != is){  is.close(); }
							if(null != bos){ bos.close(); }
							if(null != os){  os.close();}
						} catch (IOException e) {
							throw new ZipFileException("压缩文件,释放资源错误" , e);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new ZipFileException("压缩文件,释放资源错误IOError" , e);
		}finally{
			if(null != zip){
				try {
					zip.close();
				} catch (IOException e) {
					throw new ZipFileException("压缩文件,释放资源错误" , e);
				}
			}
		}
	}

	/**
	 * 解压文件.包括子目录和文件
	 * @param zipPath
	 * @param targetPath
	 * @throws IOException
	 */
	public static void unZipFiles(String zipPath , String targetPath , String fileNameEncoding) throws ZipFileException{
		ZipFile zip = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			zip = new ZipFile(new File(zipPath),fileNameEncoding);
			for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
				int size = 0;
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File unzipFile = new File(FilenameUtils.normalize
						(targetPath + File.separator + entry.getName()));
				if (unzipFile.isDirectory()) {
					continue;
				} else {
					try {
						FileUtils.touch(unzipFile);
						is = zip.getInputStream(entry);
						byte[] buffer = new byte[BUFFER];
						os = new FileOutputStream(unzipFile);
						bos = new BufferedOutputStream(os, buffer.length);
						while ((size = is.read(buffer, 0, buffer.length)) != -1) {
							bos.write(buffer, 0, size);
						}
						if(bos != null){	bos.flush();	}
						if(os != null){		os.flush();		}
					}catch (IOException e) {
						throw new ZipFileException("解压错误" , e);
					} finally {
						try {
							if(null != is){  is.close(); }
							if(null != bos){ bos.close(); }
							if(null != os){  os.close();}
						} catch (IOException e) {
							throw new ZipFileException("压缩文件,释放资源错误" , e);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new ZipFileException("压缩文件,释放资源错误IOError" , e);
		}finally{
			if(null != zip){
				try {
					zip.close();
				} catch (IOException e) {
					throw new ZipFileException("压缩文件,释放资源错误" , e);
				}
			}
		}
	}

	/**
	 * 解压文件.包括子目录和文件
	 * @param zipPath
	 * @param targetPath
	 * @throws IOException
	 */
	public static void unZipFiles(File zipFile , String targetPath , String fileNameEncoding) throws ZipFileException{
		ZipFile zip = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			zip = new ZipFile(zipFile,fileNameEncoding);
			for (Enumeration entries = zip.getEntries(); entries.hasMoreElements(); ) {
				int size = 0;
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File unzipFile = new File(FilenameUtils.normalize
						(targetPath + File.separator + entry.getName()));
				if (unzipFile.isDirectory()) {
					continue;
				} else {
					try {
						FileUtils.touch(unzipFile);
						is = zip.getInputStream(entry);
						byte[] buffer = new byte[BUFFER];
						os = new FileOutputStream(unzipFile);
						bos = new BufferedOutputStream(os, buffer.length);
						while ((size = is.read(buffer, 0, buffer.length)) != -1) {
							bos.write(buffer, 0, size);
						}
						if(bos != null){	bos.flush();	}
						if(os != null){		os.flush();		}
					}catch (IOException e) {
						throw new ZipFileException("解压错误" , e);
					} finally {
						try {
							if(null != is){  is.close(); }
							if(null != bos){ bos.close(); }
							if(null != os){  os.close();}
						} catch (IOException e) {
							throw new ZipFileException("压缩文件,释放资源错误" , e);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new ZipFileException("压缩文件,释放资源错误IOError" , e);
		}finally{
			if(null != zip){
				try {
					zip.close();
				} catch (IOException e) {
					throw new ZipFileException("压缩文件,释放资源错误" , e);
				}
			}
		}
	}
}
