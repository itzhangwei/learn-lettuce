package com.learn.cvs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author zack.zhang
 * @projectName learn-lettuce
 * @title CSVPoiUtils
 * @package com.learn.cvs
 * @description cvs
 * @date 2019/10/16 10:19 上午
 */
public class CSVPoiUtils {
	/**
	 * 导出csv文件(此方法可以保持到本地)
	 *
	 * @param fileName      导出完整路径不加后缀名
	 * @param linkedHashMap 导出csv文件的标头数据
	 * @param lists         导出csv文件的行数据
	 * @author Jason K
	 */
//	public static void csv(String fileName, LinkedHashMap<String, String> linkedHashMap, List<LinkedHashMap<String, String>> lists) {
//		char delimiter = ',';
//		try {
//			CSVPrinter printer = new CSVPrinter(new FileWriter(fileName+".csv"),CSVFormat.EXCEL);
//			csvOutput.setEscapeMode(CsvWriter.ESCAPE_MODE_DOUBLED);
//			//第一行
//			for (String key : linkedHashMap.keySet()) {
//				csvOutput.write(linkedHashMap.get(key));
//			}
//			csvOutput.endRecord();
//			//循环写出数据
//			for (LinkedHashMap<String, String> data : lists) {
//				for (String key : linkedHashMap.keySet()) {
//					csvOutput.write(data.get(key));
//				}
//				csvOutput.endRecord();
//			}
//			csvOutput.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 导出csv文件
//	 *
//	 * @param out
//	 * @param linkedHashMap 头标题
//	 * @param lists         行数据
//	 * @author Jason K
//	 */
//	public static void csv(OutputStream out, LinkedHashMap<String, String> linkedHashMap, List<LinkedHashMap<String, String>> lists) {
//		char delimiter = ',';
//		try {
//			CsvWriter csvOutput = new CsvWriter(out, delimiter, Charset.forName("GBK"));
//			csvOutput.setEscapeMode(CsvWriter.ESCAPE_MODE_DOUBLED);
//			//第一行
//			for (String key : linkedHashMap.keySet()) {
//				csvOutput.write(linkedHashMap.get(key));
//			}
//			csvOutput.endRecord();
//			//循环写出数据
//			for (LinkedHashMap<String, String> data : lists) {
//				for (String key : linkedHashMap.keySet()) {
//					csvOutput.write(data.get(key));
//				}
//				csvOutput.endRecord();
//			}
//			csvOutput.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 下载文件
	 *
	 * @param response
	 * @param csvFilePath 文件路径
	 * @param fileName    文件名称
	 * @throws IOException
	 * @author Jason K
	 */
//	public static void exportFile(HttpServletResponse response, String fileName, LinkedHashMap<String, String> linkedHashMap, List<LinkedHashMap<String, String>> lists)
//			throws IOException {
//		response.setContentType("application/csv;charset=GBK");
//		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
//		try {
//			response.setCharacterEncoding("GBK");
//			CSVPoiUtils.csv(response.getOutputStream(), linkedHashMap, lists);
//		} catch (FileNotFoundException e) {
//			System.out.println(e);
//		}
//	}
	public static void main(String[] args) {
		
		try (CSVPrinter printer = new CSVPrinter(new FileWriter("/Users/zack.zhang/Documents/learn/learn-lettuce/test.csv"), CSVFormat.EXCEL)) {
			printer.printRecord("id", "userName", "firstName", "lastName", "birthday");
			printer.printRecord(1, "john73", "John", "Doe", LocalDate.of(1973, 9, 15));
			printer.println();
			printer.printRecord(2, "mary", "Mary", "Meyer", LocalDate.of(1985, 3, 29));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}