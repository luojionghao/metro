package cn.zdmake.metro.base.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.zdmake.metro.base.security.Tea;
import cn.zdmake.metro.vo.IhistorianResponse;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class IhistorianUtil {
	private final static String URL = ConfigProperties.getValueByKey("IHISTORIAN_VISIT_URL");
	private final static String CityAbbreviation = ConfigProperties.getValueByKey("CITY_ABBREVIATION");
	// private final static String res =
	// "H66eXNn6R7OtLTuRHFDhJNpT0Q/oeJ1yp7s7hlSh0Q/2WN7dGVFlvGDOIUp5Gp14Mp27zFt8Dwl+4Off3pj0dSZ84IAIBxH8IelHfcEvQZSk8nqZin1/c+8IMY310p45sb/3QZ6gJWNpjKZ2kTB0wUrq+KkxW9y83pqq9umWKrge61p5vZVZWBLmmNEP+kgjTGfsvE0NfhKhwT9h8W62FiRd8O91D7hboO/3Gn4NA5LnVYcxDdo5+nPjzDDsbyuLDNtDRI3+sWDGgGM0O99BTo6i8RGlugsL";

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL, params);

			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getCalcByKeys(List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			Set<String> new_keys = new HashSet<String>(keys);
			map.put("request", new_keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/onlinecalc", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @return
	 */
	public static IhistorianResponse getDataByKeysRing(List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			Set<String> new_keys = new HashSet<String>(keys);
			map.put("request", new_keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/ringquality", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @param mileage
	 *            里程
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys, Float mileage) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys.get(0));
			map.put("mileage", mileage);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/mileage2ts", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * Ihistorian 响应结果
	 * 
	 * @param keys
	 *            key集合
	 * @param timestamps
	 *            里程
	 * @return
	 */
	public static IhistorianResponse getDataByKeys(List<String> keys, List<String> timestamps) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			map.put("timestamp", timestamps);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/timestamp1", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 获取key
	 * 
	 * @param lineNo
	 *            线路编号 21
	 * @param intervalMark
	 *            区间标号 16
	 * @param leftOrRight
	 *            左右线标记 L
	 * @param param
	 *            参数名 A0001 来源于字典表
	 * @return GZ21_16L.A0001.F_CV
	 */
	public static String getKey(int lineNo, int intervalMark, String leftOrRight, String param) {
		return CityAbbreviation + lineNo + "_" + intervalMark + leftOrRight.toUpperCase() + "." + param + ".F_CV";
	}

	/**
	 * 获取key
	 * 
	 * @param lineNo
	 *            线路编号 21
	 * @param intervalMark
	 *            区间标号 16
	 * @param leftOrRight
	 *            左右线标记 L
	 * @return GZ21_16L.A0001.F_CV
	 */
	public static String getKey(int lineNo, int intervalMark, String leftOrRight) {
		return CityAbbreviation + lineNo + "_" + intervalMark + leftOrRight.toUpperCase();
	}

	/**
	 * 根据环号数据获取对应环的水平和垂直纠偏量
	 * 
	 * @param ringarr
	 * @param ks
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndRingNums(List ringarr, List<String> ks) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", ks);
			Collections.sort(ringarr, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1.compareTo(o2);
				}
			});
			map.put("ringarr", ringarr);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/ringcalc", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数据获取材料消耗量
	 * 
	 * @param keys
	 * @param rings
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndRings(List<String> keys, List<String> rings) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			map.put("ringarr", rings);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/maxofring", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数据获取材料消耗量
	 * 
	 * @param keys
	 * @param rings
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndRingsTime(List<String> keys, List<String> rings) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", keys);
			map.put("ringarr", rings);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebytsv2", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数组获取统计时间
	 * 
	 * @param key
	 * @param rings
	 * @return
	 */
	public static IhistorianResponse getDataByKeyAndRings(String key, List<String> rings) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("request", key);
			map.put("ringarr", rings);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/timecalcbyring", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 获取每天推进进度
	 * 
	 * @param key
	 * @param bd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return
	 */
	public static IhistorianResponse getDataByKeyAndTime(String key, Date bd, Date ed) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("starttime", String.valueOf(bd.getTime() / 1000));
			map.put("endtime", String.valueOf(ed.getTime() / 1000));
			map.put("field", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/ringsdiffbydate", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<Object> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 综合统计
	 * 
	 * @param parse
	 * @param parse2
	 * @param timerequest
	 * @param keys
	 * @return
	 */
	public static IhistorianResponse getDataByKeyAndBeTime(Date parse, Date parse2, String timerequest,
			List<String> keys) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("starttime", String.valueOf(parse.getTime() / 1000));
			map.put("endtime", String.valueOf(parse2.getTime() / 1000));
			map.put("timerequest", timerequest);
			map.put("fieldrequest", keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/summarydatabyts", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号数组获取综合数据
	 * 
	 * @param keys
	 *            参数数组
	 * @param rings
	 *            环号数组
	 * @param fixedcount
	 *            模式1-7种 当为0时代表无模式
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndBeRing(List<String> keys, List<String> rings, int fixedcount,
			String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("ringarr", rings);
			map.put("request", keys);
			map.put("fixedcount", fixedcount);
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebyringv2", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据起始和结束日期获取综合数据
	 * 
	 * @param keys
	 *            参数数组
	 * @param beginTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @param fixedcount
	 *            模式1-7种 当为0时代表无模式
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getDataByKeysAndBeTime(List<String> keys, Date beginTime, Date endTime,
			int fixedcount, String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("starttime", String.valueOf(beginTime.getTime() / 1000));
			map.put("endtime", String.valueOf(endTime.getTime() / 1000));
			map.put("fixedcount", fixedcount);
			map.put("identify", key_h);
			map.put("request", keys);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/fivetagvaluebytsv2", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				List<HashMap<String, HashMap>> list = JSONArray.parseObject(result, List.class);
				map.clear();
				map.put("list", list);
				ires.setResult(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号获取综合数据
	 * 
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getDataByLine(String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/allmileage", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				//String result = GsonUtils.toJson(resMap.get("result"));
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/**
	 * 根据环号获取平面坐标
	 * 
	 * @param key_h
	 *            到区间左右线key前缀
	 * @return
	 */
	public static IhistorianResponse getCoordinatesByLine(String key_h) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("identify", key_h);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/planecoordinates", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getExportdatabydatemin(String key, Date exportdate) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("datetime", String.valueOf(exportdate.getTime() / 1000));
			map.put("request", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/exportdatabydatemin", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getExportdatabyringmin(String key, String ring) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("ring", ring);
			map.put("request", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/exportdatabyringmin", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	public static IhistorianResponse getTodayRing(String key) {
		IhistorianResponse ires = new IhistorianResponse();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("identify", key);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String jsonString = new ObjectMapper().writeValueAsString(map);
			NameValuePair pvp = new BasicNameValuePair("parameters", Tea.encryptByBase64Tea(jsonString));
			params.add(pvp);
			String res = HttpClientUtil.post(URL + "/todayRing", params);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> resMap = mapper.readValue(Tea.decryptByBase64Tea(res.replace("\"", "")), HashMap.class);
			ires.setCode(Integer.parseInt(resMap.get("code").toString()));
			ires.setMessage(resMap.get("message").toString());
			if (ires.getCode() == 200) {
				String result = new ObjectMapper().writeValueAsString(resMap.get("result"));
				Map<String, Object> resultMap = mapper.readValue(result, HashMap.class);
				ires.setResult(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ires;
	}

	/*
	 * public static void main(String[] args) throws Exception { List<String> keys =
	 * new ArrayList<>(); long h = System.currentTimeMillis();
	 * keys.add("GZ21_16L.A4579.F_CV"); keys.add("GZ21_16L.B0006.F_CV");
	 * keys.add("GZ21_16L.E0019.F_CV"); List<String> rings = new ArrayList<>();
	 * rings.add("818"); rings.add("819"); rings.add("820"); rings.add("821");
	 * rings.add("822"); rings.add("824"); IhistorianResponse ir =
	 * getDataByKeysAndRingsTime(keys,rings);
	 * 
	 * IhistorianResponse ir = getTodayRing("GZ21_16L"); System.out.println(ir);
	 * 
	 * }
	 */

}
