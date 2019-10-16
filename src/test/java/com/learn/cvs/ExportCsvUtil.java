package com.learn.cvs;

/**
 * @author zack.zhang
 * @projectName mate
 * @title ExportCsvUtil
 * @package com.gymbomate.common.util
 * @description csv文件导出
 * @date 2019/10/16 3:07 下午
 */
//@Slf4j
public class ExportCsvUtil {
	
//	public static void exportCsv(JSONObject table, ServletOutputStream os) {
//		OutputStreamWriter osw = null;
//		CSVFormat csvFormat = null;
//		CSVPrinter csvPrinter = null;
//		try {
//			JSONArray headers = table.getJSONArray("head");
//			osw = new OutputStreamWriter(os, "UTF-8");
//
//			List<String> headList = new ArrayList<>();
//			headers.forEach(h -> {
//				headList.add(((JSONObject) h).getString("name"));
//			});
//
//			//表头
//			csvFormat = CSVFormat.EXCEL.withHeader(headList.toArray(new String[]{}));
//
//
//			final CSVPrinter csvP = new CSVPrinter(osw, csvFormat);
//
//			csvPrinter = csvP;
//
//			JSONArray dataList = table.getJSONArray("body");
//
//			// 内容
//
//			log.info("csv内容body，开始：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
//
//			dataList.parallelStream().forEachOrdered(data -> {
//				JSONObject json = (JSONObject) data;
//				List<String> recordValueList = new ArrayList<>();
//				headers.forEach(h -> {
//					JSONObject head = (JSONObject) h;
//					String key = head.getString("key");
//					String filedValue = json.getString(key);
//				});
//				try {
//					csvP.printRecord(recordValueList);
//				} catch (IOException e) {
//					throw new BaseException("csv导出异常");
//				}
//			});
//			log.info("csv内容body，结束：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
//
//		} catch (Exception e) {
//			log.error(JSON.toJSONString(e));
//		} finally {
//			close(os, csvPrinter);
//		}
//	}
//
//	private static void close(ServletOutputStream os, CSVPrinter csvPrinter) {
//		if (csvPrinter != null) {
//			try {
//				csvPrinter.flush();
//			} catch (IOException e) {
//				log.error(JSON.toJSONString(e));
//			}
//			try {
//				csvPrinter.close();
//			} catch (IOException e) {
//				log.error(JSON.toJSONString(e));
//			}
//		}
//		if (os != null) {
//			try {
//				os.flush();
//			} catch (IOException e) {
//				log.error(JSON.toJSONString(e));
//			}
//			try {
//				os.close();
//			} catch (IOException e) {
//				log.error(JSON.toJSONString(e));
//			}
//		}
//	}
}
