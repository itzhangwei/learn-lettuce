package com.learn.cvs;

/**
 * @author zack.zhang
 * @projectName mate
 * @title CvsServiceImpl
 * @package com.gymbomate.common.service
 * @description cvs导出
 * @date 2019/10/16 10:07 上午
 */
//@Slf4j
//@Service
//public class CsvServiceImpl implements CsvService /*{
//
//	@Override
//	public void excelCvs(HttpServletRequest request, HttpServletResponse response, List dataList, String paraFileName, JSONArray head) {
//		Map<String, Object> data = new HashedMap();
//		data.put("data", dataList);
//		try {
//			response.setContentType("application/octet-stream;charset=utf-8");
//			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(paraFileName, "UTF-8") + ".csv");
//			JSONArray body = JSON.parseArray(JSON.toJSONString(data.get("data")));
//			export(head, body, response.getOutputStream());
//			response.flushBuffer();
//		} catch (IOException e) {
//			log.error(e.getMessage(),e);
//		}
//	}
//	public void export(JSONArray head, JSONArray body, ServletOutputStream outputStream) {
//		JSONObject table = new JSONObject();
//		log.info("head.size-------" + head.size());
//		log.info("body.size-------" + body.size());
//		table.put("title", "data");
//		table.put("head", head);
//		table.put("body", body);
//		ExportCsvUtil.exportCsv(table, outputStream);
//
//	}
//
//
//}*/
