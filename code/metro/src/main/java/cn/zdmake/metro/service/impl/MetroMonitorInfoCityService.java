package cn.zdmake.metro.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.Page;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.MathUtil;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.dao.IMetroLineIntervalDataDao;
import cn.zdmake.metro.dao.IMetroMonitorCityDao;
import cn.zdmake.metro.model.*;
import cn.zdmake.metro.vo.MonitorIntervalLrStaticView;
import cn.zdmake.metro.vo.MonitorIntervalView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.ibm.icu.util.Calendar;

import cn.zdmake.metro.base.utils.IhistorianUtil;
import cn.zdmake.metro.dao.IMetroDictionaryDao;
import cn.zdmake.metro.dao.IMetroLineIntervalSpDao;
import cn.zdmake.metro.service.IMetroMonitorInfoCityService;
import cn.zdmake.metro.vo.IhistorianResponse;
import cn.zdmake.metro.vo.MonitorIntervalLrStaticsView;
import cn.zdmake.metro.vo.MonitorLrAlldicView;
import cn.zdmake.metro.vo.MonitorStiaticParamView;
import cn.zdmake.metro.vo.MonitorViewData;


@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("infoCityService")
public class MetroMonitorInfoCityService implements IMetroMonitorInfoCityService {

	private final static String[] KPS = { "A0001", "A0002", "A0013", "B0001", "B0002", "B0004", "B0006", "D0015" };
	private final static String[] KPSS = { "A0001", "A0002", "A0003", "A0004", "A0005", "A0006", "A0007", "A0008",
			"A0009", "A0011", "B0001", "B0002", "B0004", "B0006", "B0011", "B0012", "C0001", "C0003", "C0005", "C0007",
			"C0009", "C0011", "C0013", "C0015", "D0008", "D0010", "D0012", "D0014", "D0016", "D0018", "D0020", "D0022",
			"D0023", "E0002", "E0004", "E0006", "E0008", "E0010", "E0012", "E0014", "E0016", "E0019", "F0001", "F0002",
			"F0005", "F0006", "G0002", "G0003", "G0004", "G0006", "G0007", "G0008", "G0010", "G0011", "G0012", "G0014",
			"G0015", "G0016", "G0018", "G0019", "G0020", "G0022", "G0023", "G0024", "G0026", "G0027", "G0028", "G0030",
			"G0031", "G0032", "H0042", "H0043", "H0040", "H0041", "I0020", "J0001", "J0002", "J0003", "J0004", "J0005",
			"J0006", "J0007", "J0008", "J0009", "J0010", "J0011", "J0012", "J0020", "J0021", "J0022", "J0023", "J0024",
			"J0025", "J0026", "J0027", "J0028", "J0029", "J0030", "R0001", "R0002", "R0003", "R0004", "R0005", "R0006",
			"R0007", "R0008", "R0009", "R0010", "R0011", "R0012", "R0013", "R0014", "R0015", "R0016", "R0017", "R0018",
			"R0019", "R0020", "R0021", "R0022", "R0023", "R0024", "A0013" };

	private final static String[] KPSM = { "J0001" };

	private final static String[] ILRDX = { "J0001", "J0020", "J0021", "J0024", "J0025", "J0028", "J0029" };

	private final static String[] MODEL1 = { "A0004", "B0006", "B0004", "B0001", "B0002" };
	private final static String[] MODEL2 = { "A4579", "B0006", "E0019" }; // (A0004+A0005+A0007+A0009+A0011) AS A4579
	private final static String[] MODEL3 = { "C0913", "C0105", "J0025", "J0027" }; // (C0009-C0013) AS C0913;
																					// (C0001-C0005) AS C0105
	private final static String[] MODEL4 = { "B0011", "B0012" };
	private final static String[] MODEL5 = { "C1511", "C0703", "J0024", "J0026" }; // (C0015-C0011) AS C1511;
																					// (C0007-C0003) AS C0703
	private final static String[] MODEL6 = { "B0001", "H0044" };
	private final static String[] MODEL7 = { "D0023", "B0001" };
	private final static String[] MODEL8 = { "J0020", "J0021", "J0024", "J0025" };

	@Autowired
	private IMetroMonitorCityDao monitorCityDao;

	@Autowired
	private IMetroDictionaryDao dicDao;

	@Autowired
	private IMetroLineIntervalDataDao lineIntervalDataDao;

	@Autowired
	private IMetroLineIntervalSpDao lineIntervalSpDao;

	@Override
	public PageResultSet<MonitorViewData> findMonitorInfoCityData(Long cityId, Long lineId, int buildStatus,
			int pageNum, int pageSize) throws IOException {
		PageResultSet<MonitorViewData> result = null;
		Map<String, Object> params = new HashMap<>();
		params.put("cityId", cityId);
		params.put("lineId", lineId);
		if (buildStatus >= 0) {
			params.put("buildStatus", buildStatus);
		}
		int total = monitorCityDao.countMonitorInfoCityDatas(params);
		Page page = new Page(total, pageSize, pageNum);
		if (total > 0) {
			params.put("start", page.getBeginIndex());
			params.put("pageSize", page.getPageSize());
			List<MonitorInfoCity> mics = monitorCityDao.findMonitorInfoCityDatas(params);
			result = new PageResultSet<>();
			List<MonitorViewData> list = new ArrayList<MonitorViewData>();
			MonitorViewData mvd = null;
			List<String> keys = new ArrayList<String>();
			for (MonitorInfoCity mic : mics) {
				mvd = new MonitorViewData();
				mvd.setIntervalName(mic.getLineName() + "_" + mic.getIntervalName());
				mvd.setBuildStatus(mic.getBuildStatus());
				mvd.setTotalRingNum(mic.getRingNum());
				mvd.setLeftOrRight(mic.getLeftOrRight());
				for (String p : KPS) {
					keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
				}
				list.add(mvd);
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);
			if (ir != null && ir.getCode() == 200) {
				int i = 0;
				Map<String, Object> map = ir.getResult();
				
				for (MonitorInfoCity mic : mics) {
					keys = new ArrayList<String>();
					for (String p : KPS) {
						keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
					}

					Object obj = map.get(keys.get(0));
					if (obj != null) {
//						String A0001 = GsonUtils.toJson(obj);
						String A0001 = new ObjectMapper().writeValueAsString(obj);
//						ObjectMapper mapper = new ObjectMapper();
						Map<String, Object> resultMap = new ObjectMapper().readValue(A0001, HashMap.class);
						list.get(i).setA0001(Float.parseFloat(resultMap.get("ring").toString()));
						int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
						list.get(i).setCommuniStatus(quality == 100 ? 1 : 0);
					} else {
						list.get(i).setA0001(null);
						list.get(i).setCommuniStatus(0);
					}
					obj = map.get(keys.get(1));
					list.get(i).setA0002(obj != null ? Integer.parseInt(obj.toString()) : null);
					obj = map.get(keys.get(2));
					list.get(i).setA0013(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(3));
					list.get(i).setB0001(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(4));
					list.get(i).setB0002(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(5));
					list.get(i).setB0004(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(6));
					list.get(i).setB0006(obj != null ? Float.parseFloat(obj.toString()) : null);
					obj = map.get(keys.get(7));
					list.get(i).setD0015(obj != null ? Float.parseFloat(obj.toString()) : null);
					i++;
					
				}
			}
			result.setPage(page);
			result.setList(list);
		}

		return result;
	}

	@Override
	public Map<String, String> findCountMechineDatas(Long userId, Long lineId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("lineId", lineId);
		List<KeyValue> list = monitorCityDao.findCountMechineDatas(params);
		Map<String, String> map = null;
		if (list != null && list.size() > 0) {
			map = new HashMap<String, String>();
			for (KeyValue k : list) {
				map.put("total" + k.getKey(), k.getValue());
			}
		}
		List<MonitorInfoCity> mics = monitorCityDao.findAllMonitorInfoCityDatas(params);
		List<String> keys = new ArrayList<String>();
		for (MonitorInfoCity mic : mics) {
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001"));
		}
		IhistorianResponse ir = IhistorianUtil.getCalcByKeys(keys);
		if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
			map.put("totalSuccess",
					ir.getResult().get("success") != null ? ir.getResult().get("success").toString() : "0");
			map.put("totalFail", ir.getResult().get("fail") != null ? ir.getResult().get("fail").toString() : "0");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> findIntervalLrDaoPanDatas(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (String p : KPSS) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), p));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				map = ir.getResult();
				String key = "GZ" + mic.getLineNo() + "_" + mic.getIntervalMark() + mic.getLeftOrRight().toUpperCase()
						+ "." + "A0001.F_CV";
				Integer nowRingNum = StringUtil.nullToInt(map.get(key) != null ? map.get(key).toString() : "0");
				if (nowRingNum > 0) {
					List ringarr = new ArrayList();
					for (int i = 0; i < 10; i++) {
						if (nowRingNum - i > 0) {
							ringarr.add(nowRingNum - i);
						} else {
							break;
						}
					}
					List<String> ks = new ArrayList<String>();
					ks.add("GZ" + mic.getLineNo() + "_" + mic.getIntervalMark() + mic.getLeftOrRight().toUpperCase()
							+ "." + "J0020.F_CV");
					ks.add("GZ" + mic.getLineNo() + "_" + mic.getIntervalMark() + mic.getLeftOrRight().toUpperCase()
							+ "." + "J0021.F_CV");
					IhistorianResponse irr = IhistorianUtil.getDataByKeysAndRingNums(ringarr, ks);
					if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
						List<HashMap<String, HashMap>> list = (List<HashMap<String, HashMap>>) irr.getResult()
								.get("list");
						List<String> hs = new ArrayList<String>();
						List<String> vs = new ArrayList<String>();
						for (Map m : list) {
							Iterator i = m.keySet().iterator();
							while (i.hasNext()) {
								String o = i.next().toString();
								HashMap<String, Object> hv = JSONArray.parseObject(m.get(o).toString(), HashMap.class);
								hs.add(String.valueOf(hv.get("h")));
								vs.add(String.valueOf(hv.get("v")));
								break;
							}
						}
						Collections.reverse(hs);
						Collections.reverse(vs);
						map.put("ringNumH", hs);
						map.put("ringNumV", vs);
					}
				}
				Map<String, Object> front = StringUtil.getMapAndMileage(map, mic, "J0002", "J0003", "J0004", "J0001");
				if (front != null) {
					double frontMin = Math.floor(StringUtil.nullToDouble(front.get("mileage").toString()));
					double frontMax = frontMin + 1;
					Map<String, Object> frontParams = new HashMap<>();
					frontParams.put("intervalId", intervalId);
					frontParams.put("leftOrRight", leftOrRight);
					frontParams.put("mileage", frontMin);
					MetroLineIntervalData frontDataMin = lineIntervalDataDao.findUniqueData(frontParams);
					frontParams.put("mileage", frontMax);
					MetroLineIntervalData frontDataMax = lineIntervalDataDao.findUniqueData(frontParams);
					if (frontDataMin != null && frontDataMax != null) {
						double hfront = MathUtil.getHdeviation(StringUtil.nullToDouble(front.get("mapX").toString()),
								StringUtil.nullToDouble(front.get("mapY").toString()),
								frontDataMin.getMapX().doubleValue(), frontDataMin.getMapY().doubleValue(),
								frontDataMax.getMapX().doubleValue(), frontDataMax.getMapY().doubleValue());

						double vfront = MathUtil.getVdeviation(StringUtil.nullToDouble(front.get("mapZ").toString()),
								frontDataMin.getMapZ().doubleValue(), frontDataMax.getMapZ().doubleValue(),
								StringUtil.nullToDouble(front.get("mileage").toString()), frontMin, frontMax);
						map.put("hfront", hfront);
						map.put("vfront", vfront);
					}

				}
				Map<String, Object> medium = StringUtil.getMapAndMileage(map, mic, "J0005", "J0006", "J0007", "J0028");
				if (medium != null) {
					double mediumMin = Math.floor(StringUtil.nullToDouble(medium.get("mileage").toString()));
					double mediumMax = mediumMin + 1;
					Map<String, Object> mediumParams = new HashMap<>();
					mediumParams.put("intervalId", intervalId);
					mediumParams.put("leftOrRight", leftOrRight);
					mediumParams.put("mileage", mediumMin);
					MetroLineIntervalData mediumDataMin = lineIntervalDataDao.findUniqueData(mediumParams);
					mediumParams.put("mileage", mediumMax);
					MetroLineIntervalData mediumDataMax = lineIntervalDataDao.findUniqueData(mediumParams);
					if (mediumDataMin != null && mediumDataMax != null) {
						double hMedium = MathUtil.getHdeviation(StringUtil.nullToDouble(medium.get("mapX").toString()),
								StringUtil.nullToDouble(medium.get("mapY").toString()),
								mediumDataMin.getMapX().doubleValue(), mediumDataMin.getMapY().doubleValue(),
								mediumDataMax.getMapX().doubleValue(), mediumDataMax.getMapY().doubleValue());

						double vMedium = MathUtil.getVdeviation(StringUtil.nullToDouble(medium.get("mapZ").toString()),
								mediumDataMin.getMapZ().doubleValue(), mediumDataMax.getMapZ().doubleValue(),
								StringUtil.nullToDouble(front.get("mileage").toString()), mediumMin, mediumMax);
						map.put("hMedium", hMedium);
						map.put("vMedium", vMedium);
					}
				}
				Map<String, Object> back = StringUtil.getMapAndMileage(map, mic, "J0008", "J0009", "J0010", "J0029");
				if (back != null) {
					double backMin = Math.floor(StringUtil.nullToDouble(back.get("mileage").toString()));
					double backMax = backMin + 1;
					Map<String, Object> backParams = new HashMap<>();
					backParams.put("intervalId", intervalId);
					backParams.put("leftOrRight", leftOrRight);
					backParams.put("mileage", backMin);
					MetroLineIntervalData backDataMin = lineIntervalDataDao.findUniqueData(backParams);
					backParams.put("mileage", backMax);
					MetroLineIntervalData backDataMax = lineIntervalDataDao.findUniqueData(backParams);
					if (backDataMin != null && backDataMax != null) {
						double hBack = MathUtil.getHdeviation(StringUtil.nullToDouble(back.get("mapX").toString()),
								StringUtil.nullToDouble(back.get("mapY").toString()),
								backDataMin.getMapX().doubleValue(), backDataMin.getMapY().doubleValue(),
								backDataMax.getMapX().doubleValue(), backDataMax.getMapY().doubleValue());

						double vBack = MathUtil.getVdeviation(StringUtil.nullToDouble(back.get("mapZ").toString()),
								backDataMin.getMapZ().doubleValue(), backDataMax.getMapZ().doubleValue(),
								StringUtil.nullToDouble(front.get("mileage").toString()), backMin, backMax);
						map.put("hBack", hBack);
						map.put("vBack", vBack);
					}

				}

			}
			map.put("lineName", mic.getLineName());
			map.put("intervalMark", mic.getIntervalMark());
			map.put("intervalName", mic.getIntervalName());
			map.put("leftOrRight", mic.getLeftOrRight());
			map.put("ringNum", mic.getRingNum());
			map.put("head",
					"GZ" + mic.getLineNo() + "_" + mic.getIntervalMark() + mic.getLeftOrRight().toUpperCase() + ".");
		}

		return map;
	}

	@Override
	public PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrDics(long parseLong, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", parseLong);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		params.put("start", 0);
		params.put("pageSize", 500);
		List<MetroDictionary> dics = dicDao.findObjsList(params);
		PageResultSet<MonitorLrAlldicView> res = new PageResultSet<MonitorLrAlldicView>();
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (MetroDictionary dic : dics) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						dic.getDicName()));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			Map<String, Object> map = null;
			if (ir != null) {
				map = ir.getResult();
			}
			List<MonitorLrAlldicView> list = new ArrayList<MonitorLrAlldicView>();
			MonitorLrAlldicView mav = null;
			int step = 1;
			for (MetroDictionary dic : dics) {
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						dic.getDicName());
				Object obj = map != null ? map.get(key) : null;
				if (step == 1) {
					mav = new MonitorLrAlldicView();
					mav.setKey1(dic.getDicMean());
					mav.setValue1((obj != null ? obj : "-") + dic.getDicUnit());
					step++;
				} else if (step == 2) {
					mav.setKey2(dic.getDicMean());
					mav.setValue2((obj != null ? obj : "-") + dic.getDicUnit());
					step++;
				} else if (step == 3) {
					mav.setKey3(dic.getDicMean());
					mav.setValue3((obj != null ? obj : "-") + dic.getDicUnit());
					step++;
				} else {
					mav.setKey4(dic.getDicMean());
					mav.setValue4((obj != null ? obj : "-") + dic.getDicUnit());
					list.add(mav);
					step = 1;
				}
			}
			res.setList(list);
			res.setPage(new Page(0, 100, 1));
		}
		return res;
	}

	@Override
	public MonitorIntervalView findMonitorIntervalDatas(Long intervalId, Long intervalSpId) {
		MonitorIntervalView miv = null;
		Map<String, Object> params = new HashMap<>();
		params.put("intervalSpId", intervalSpId);
		MetroLineIntervalSp mlis = lineIntervalSpDao.findObjById(params);
		params.put("intervalId", intervalId);
		params.put("leftOrRight", mlis.getLeftOrRight());
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		params.clear();
		if (mic != null) {
			miv = new MonitorIntervalView();
			miv.setTitle("[" + mic.getLineName() + "-" + mic.getIntervalMark() + "标-" + mic.getIntervalName() + "-"
					+ ("l".equals(mic.getLeftOrRight()) ? "左" : "右") + "线]监测数据");
			miv.setIntervalSpId(intervalSpId);
			List<String> keys = new ArrayList<String>();
			for (String k : KPSM) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), k));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys, mlis.getOriginMileage());
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				Map m = ir.getResult();
				Long b = Long.parseLong(m.get("30m") != null ? m.get("30m").toString() : "0");
				Long e = Long.parseLong(m.get("50m") != null ? m.get("50m").toString() : "0");
				if (b > 0 && e > 0) {
					params.clear();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					params.put("beginTime", sdf.format(new Date(b * 1000)));
					params.put("endTime", sdf.format(new Date(e * 1000)));
					params.put("mdNo", mlis.getSpName());
					List<MetroLineIntervalMd> mlims = monitorCityDao.findMonitorInfoIntervalData(params);
					List<String> times = new ArrayList<String>();
					List<String> times1 = new ArrayList<String>();
					for (MetroLineIntervalMd md : mlims) {
						times.add(String.valueOf(md.getMonitorDate().getTime() / 1000));
						times1.add(sdf.format(md.getMonitorDate()));
					}

					miv.setDataTime(times1);
					IhistorianResponse irr = IhistorianUtil.getDataByKeys(keys, times);
					if (irr != null && irr.getCode() == 200 && irr != null) {
						List<HashMap> list = (List<HashMap>) irr.getResult().get("list");
						if (list != null && list.size() > 0) {
							List<List> grandSettlement = new ArrayList<List>();
							List<List> speedSettlement = new ArrayList<List>();
							List vl = null;
							List vl1 = null;
							for (Map mp : list) {
								vl = new ArrayList();
								vl1 = new ArrayList();
								Object mile = mp.get(keys.get(0));
								vl.add((mile != null ? Float.parseFloat(mile.toString()) : 0)
										- mlis.getOriginMileage());
								vl1.add((mile != null ? Float.parseFloat(mile.toString()) : 0)
										- mlis.getOriginMileage());
								grandSettlement.add(vl);
								speedSettlement.add(vl1);
							}

							int i = 0;
							for (MetroLineIntervalMd md : mlims) {
								grandSettlement.get(i).add(md.getSumVar());
								speedSettlement.get(i).add(md.getSpSpeed());
								i++;
							}
							miv.setGrandSettlement(grandSettlement);
							miv.setSpeedSettlement(speedSettlement);

						}
					}
				}
			}

		}
		return miv;
	}

	@Override
	public Map<String, Object> findIntervalLrDaoxDatas(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			for (String k : ILRDX) {
				keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), k));
			}
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);

			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				params = ir.getResult();
				int c = 0;
				Object[] p1 = new Object[2];
				Object[] p2 = new Object[2];
				Object[] b1 = new Object[2];
				Object[] b2 = new Object[2];

				for (String key : keys) {
					if (c == 0) {// 盾首里程
						b2[0] = params.get(key);
						p2[1] = params.get(key);
					} else if (c == 1) {// 前水平偏差
						result.put("x1", params.get(key));
						p2[0] = params.get(key);
					} else if (c == 2) {// 前垂直偏差
						result.put("y1", params.get(key));
						b2[1] = params.get(key);
					} else if (c == 3) {// 尾水平偏差
						result.put("x2", params.get(key));
						p1[0] = params.get(key);
					} else if (c == 4) {// 尾垂直偏差
						result.put("y2", params.get(key));
						b1[1] = params.get(key);
					} else {// 盾尾里程
						b1[0] = params.get(key);
						p1[1] = params.get(key);
					}
					c++;
				}
				List p = new ArrayList();
				p.add(p1);
				p.add(p2);
				List b = new ArrayList();
				b.add(b1);
				b.add(b2);
				result.put("pyl", p);
				result.put("pylb", b);
			}
		}
		return result;
	}

	@Override
	public List<List<Object>> findMonitorStaticTab1(String intervalId, String leftOrRight, String beginRing,
			String endRing, String paramName) {
		List<List<Object>> res = new ArrayList<List<Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), paramName));
			List<String> rings = new ArrayList<String>();
			List<Object> re = new ArrayList<Object>();
			for (int i = Integer.parseInt(beginRing); i <= Integer.parseInt(endRing); i++) {
				re.add(i);
				rings.add(String.valueOf(i));
			}
			res.add(re);
			IhistorianResponse ir = IhistorianUtil.getDataByKeysAndRings(keys, rings);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> result = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<Object> re1 = new ArrayList<Object>();
				int i = 0;
				for (String r : rings) {
					Map m = result.get(i);
					Map<String, HashMap> m1 = (Map<String, HashMap>) m.get(r);
					Object obj = m1.get(keys.get(0));
					re1.add(obj);
					i++;
				}
				res.add(re1);
			}
		}
		return res;
	}

	@Override
	public List<List<Object>> findMonitorStaticTab2(String intervalId, String leftOrRight, String beginRing,
			String endRing) {
		List<List<Object>> res = new ArrayList<List<Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0002");
			List<String> rings = new ArrayList<String>();
			List<Object> list1 = new ArrayList<Object>();
			for (int i = Integer.parseInt(beginRing); i <= Integer.parseInt(endRing); i++) {
				list1.add(i);
				rings.add(String.valueOf(i));
			}
			res.add(list1);
			IhistorianResponse ir = IhistorianUtil.getDataByKeyAndRings(key, rings);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> result = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<Object> list2 = new ArrayList<Object>();
				List<Object> list3 = new ArrayList<Object>();
				List<Object> list4 = new ArrayList<Object>();
				List<Object> list5 = new ArrayList<Object>();
				List<Object> list6 = new ArrayList<Object>();
				int i = 0;
				for (Map map : result) {
					Map<String, HashMap> m = (Map<String, HashMap>) map.get(rings.get(i));
					list2.add(m.get("K0001"));// 停止时间
					list3.add(m.get("K0002"));// 推进时间
					list4.add(m.get("K0003"));// 拼装时间
					i++;
				}

				float v1 = 0;
				for (Object f : list3) {
					v1 = v1 + Float.parseFloat(f.toString());
				}
				list5.add(v1);
				Map<String, Object> t1 = new HashMap<String, Object>();
				t1.put("value", v1);
				t1.put("name", "推进时间");

				float v2 = 0;
				for (Object f : list4) {
					v2 = v2 + Float.parseFloat(f.toString());
				}
				list5.add(v2);
				Map<String, Object> t2 = new HashMap<String, Object>();
				t2.put("value", v2);
				t2.put("name", "拼装时间");

				float v3 = 0;
				for (Object f : list2) {
					v3 = v3 + Float.parseFloat(f.toString());
				}
				list5.add(v3);
				Map<String, Object> t3 = new HashMap<String, Object>();
				t3.put("value", v3);
				t3.put("name", "停止时间");

				list6.add(t1);
				list6.add(t2);
				list6.add(t3);

				res.add(list3);
				res.add(list4);
				res.add(list2);
				res.add(list5);
				res.add(list6);
			}

		}
		return res;
	}

	@Override
	public List<List<Object>> findMonitorStaticTab3(String intervalId, String leftOrRight, String beginDate,
			String endDate) {
		List<List<Object>> res = new ArrayList<List<Object>>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001");
			String[] bs = beginDate.split("/");
			String[] ns = endDate.split("/");
			String b = bs[2] + "-" + bs[0] + "-" + bs[1];
			String e = ns[2] + "-" + ns[0] + "-" + ns[1];
			List<Object> times = new ArrayList<Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date bd = null, ed = null;
			try {
				bd = sdf.parse(b);
				ed = sdf.parse(e);
				Calendar cal = Calendar.getInstance();
				cal.setTime(bd);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(ed);
				long now = 0l;
				while (now <= cal1.getTimeInMillis()) {
					times.add(sdf.format(cal.getTime()));
					now = cal.getTimeInMillis();
					cal.add(Calendar.DATE, 1);
				}
			} catch (ParseException e1) {

			}
			res.add(times);
			IhistorianResponse ir = IhistorianUtil.getDataByKeyAndTime(key, bd, ed);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				res.add((List<Object>) ir.getResult().get("list"));
			}

		}
		return res;
	}

	@Override
	public PageResultSet<MonitorIntervalLrStaticView> findMonitorStaticTab4(String intervalId, String leftOrRight,
			String pageNum, String pageSize, String beginTime, String endTime, String excelType) {
		PageResultSet<MonitorIntervalLrStaticView> res = new PageResultSet<MonitorIntervalLrStaticView>();
		List<MonitorIntervalLrStaticView> result = new ArrayList<MonitorIntervalLrStaticView>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			String timerequest = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
					"A0002");
			String key1 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "D0023");
			String key2 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "G0001");
			List<String> keys = new ArrayList<String>();
			keys.add(key1);
			keys.add(key2);
			String[] bs = beginTime.split("/");
			String[] ns = endTime.split("/");
			String b = bs[2] + "-" + bs[0] + "-" + bs[1];
			String e = ns[2] + "-" + ns[0] + "-" + ns[1];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				List<MonitorIntervalLrStaticView> msvs = new ArrayList<MonitorIntervalLrStaticView>();
				IhistorianResponse ir = IhistorianUtil.getDataByKeyAndBeTime(sdf.parse(b), sdf.parse(e), timerequest,
						keys);
				if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
					List<HashMap<String, HashMap>> data = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
					MonitorIntervalLrStaticView msv = null;
					for (Map map : data) {
						msv = new MonitorIntervalLrStaticView();
						Iterator i = map.keySet().iterator();
						while (i.hasNext()) {
							String o = i.next().toString();
							Long date = Long.parseLong(o) * 1000;
							msv.setDate(sdf.format(new Date(date)));
							HashMap<String, Object> v = JSONArray.parseObject(map.get(o).toString(), HashMap.class);
							msv.setK0001(Float.parseFloat(v.get("K0001").toString()));
							msv.setK0002(Float.parseFloat(v.get("K0002").toString()));
							msv.setK0003(Float.parseFloat(v.get("K0003").toString()));
							msv.setD0023(Float.parseFloat(v.get(key1).toString()));
							msv.setG0001(Float.parseFloat(v.get(key2).toString()));
							break;
						}
						msvs.add(msv);
					}
				}
				String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
						"A0001");
				IhistorianResponse irr = IhistorianUtil.getDataByKeyAndTime(key, sdf.parse(b), sdf.parse(e));
				if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
					int i = 0;
					List<Object> l = (List<Object>) irr.getResult().get("list");
					for (Object o : l) {
						msvs.get(i).setRingNum(Float.parseFloat((o.toString())));
						i++;
					}
				}
				List<String> times = new ArrayList<String>();
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(b));
				long end = sdf.parse(e).getTime();
				while (cal.getTime().getTime() <= end) {
					times.add(String.valueOf(cal.getTime().getTime() / 1000));
					cal.add(Calendar.DATE, 1);
				}
				keys.clear();
				keys.add(key);
				IhistorianResponse irrr = IhistorianUtil.getDataByKeys(keys, times);
				if (irr != null && irr.getCode() == 200 && irr.getResult() != null) {
					List<HashMap> ll = (List<HashMap>) irrr.getResult().get("list");
					int i = 0;
					for (Map m : ll) {
						Float ringn = m.get(key) != null ? Float.parseFloat((m.get(key).toString())) : 0;
						msvs.get(i).setBeginRing(ringn);
						msvs.get(i).setEndRing(ringn + msvs.get(i).getRingNum());
						i++;
					}
				}
				result = getMonitorStaticTab4Rec(msvs, excelType, mic);
				Page page = new Page(result.size(), StringUtil.nullToInt(pageSize), StringUtil.nullToInt(pageNum));
				res.setPage(page);
				res.setList(result);
			} catch (Exception ec) {
				ec.printStackTrace();
			}
		}
		return res;
	}

	private static List<MonitorIntervalLrStaticView> getMonitorStaticTab4Rec(List<MonitorIntervalLrStaticView> data,
			String excelType, MonitorInfoCity mic) {
		List<MonitorIntervalLrStaticView> result = new ArrayList<MonitorIntervalLrStaticView>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			MonitorIntervalLrStaticView m = null;
			Float ringNum = 0f;// 推进环数
			Float K0001 = 0f;// 推进时间
			Float K0002 = 0f;// 拼装时间
			Float K0003 = 0f;// 停止时间
			Float D0023 = 0f;// 同步注浆量
			Float G0001 = 0f;// 盾尾油脂量
			if ("1".equals(excelType)) {// 日
				return data;
			} else if ("2".equals(excelType)) {// 周
				Calendar cal = Calendar.getInstance();
				int week = 0, day = 0, i = 0;
				for (MonitorIntervalLrStaticView mv : data) {
					ringNum = ringNum + mv.getRingNum();
					K0001 = K0001 + mv.getK0001();
					K0002 = K0002 + mv.getK0002();
					K0003 = K0003 + mv.getK0003();
					D0023 = D0023 + mv.getD0023();
					G0001 = G0001 + mv.getG0001();

					Date d1 = sdf.parse(mv.getDate());
					cal.setTime(d1);
					week = cal.get(Calendar.WEEK_OF_YEAR);
					day = cal.get(Calendar.DAY_OF_WEEK);
					if (i == 0 || day == 2) {// 记录第一和每周第一
						m = new MonitorIntervalLrStaticView();
						m.setLineMark(mic.getLineNo() + "号线-" + mic.getIntervalName() + "-"
								+ ("L".equals(mic.getLeftOrRight().toUpperCase()) ? "左" : "右") + "线");
						m.setDate(week + "周(" + sdf.format(d1) + "至");
						m.setBeginRing(mv.getBeginRing());
						m.setBeginDate(sdf.format(d1));
					}

					if (i == (data.size() - 1) || day == 1) {// 记录最后和每周最后
						m.setDate(m.getDate() + sdf.format(d1) + ")");
						m.setEndDate(sdf.format(d1));
						m.setEndRing(mv.getEndRing());
						m.setRingNum(ringNum);
						m.setK0001(K0001);
						m.setK0002(K0002);
						m.setK0003(K0003);
						m.setD0023(D0023);
						m.setG0001(G0001);
						result.add(m);
						ringNum = 0f;
						K0001 = 0f;
						K0002 = 0f;
						K0003 = 0f;
						D0023 = 0f;
						G0001 = 0f;
					}
					i++;
				}
			} else {// 月
				Calendar cal = Calendar.getInstance();
				int month = 0, day = 0, i = 0;
				for (MonitorIntervalLrStaticView mv : data) {
					ringNum = ringNum + mv.getRingNum();
					K0001 = K0001 + mv.getK0001();
					K0002 = K0002 + mv.getK0002();
					K0003 = K0003 + mv.getK0003();
					D0023 = D0023 + mv.getD0023();
					G0001 = G0001 + mv.getG0001();

					Date d1 = sdf.parse(mv.getDate());
					cal.setTime(d1);
					month = cal.get(Calendar.MONTH);
					day = cal.get(Calendar.DAY_OF_MONTH);
					if (i == 0 || day == 1) {// 记录第一和每周第一
						m = new MonitorIntervalLrStaticView();
						m.setLineMark(mic.getLineNo() + "号线-" + mic.getIntervalName() + "-"
								+ ("L".equals(mic.getLeftOrRight().toUpperCase()) ? "左" : "右") + "线");
						m.setDate(month + "月(" + sdf.format(d1) + "至");
						m.setBeginRing(mv.getBeginRing());
						m.setBeginDate(sdf.format(d1));
					}
					cal.add(Calendar.DATE, 1);
					day = cal.get(Calendar.DAY_OF_MONTH);
					if (i == (data.size() - 1) || day == 1) {// 记录最后和每周最后
						m.setDate(m.getDate() + sdf.format(d1) + ")");
						m.setEndDate(sdf.format(d1));
						m.setEndRing(mv.getEndRing());
						m.setRingNum(ringNum);
						m.setK0001(K0001);
						m.setK0002(K0002);
						m.setK0003(K0003);
						m.setD0023(D0023);
						m.setG0001(G0001);
						result.add(m);
						ringNum = 0f;
						K0001 = 0f;
						K0002 = 0f;
						K0003 = 0f;
						D0023 = 0f;
						G0001 = 0f;
					}
					i++;
				}
			}

		} catch (ParseException e1) {

		}
		return result;
	}

	@Override
	public MonitorIntervalLrStaticsView findMonitorStaticTab5(String intervalId, String leftOrRight, int model,
			String type, String beginTime, String endTime, int beginRing, int endRing, String[] ks, String[] kns,
			String[] indxs) {
		MonitorIntervalLrStaticsView misv = new MonitorIntervalLrStaticsView();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			String[] pns = {};
			String[] pnames = {};
			String[] inxs = {};
			if (model == 1) {
				pns = MODEL1;
				pnames = "土压（上）,总推力,扭矩,掘进速度,刀盘转速".split(",");
				inxs = "0,1,2,3,4".split(",");
			} else if (model == 2) {
				pns = MODEL2;
				pnames = "土压力,总推力,铰接压力".split(",");
				inxs = "0,1,2".split(",");
			} else if (model == 3) {
				pns = MODEL3;
				pnames = "千斤顶油压差（上下）,千斤顶行程差（上下）,盾尾垂直偏差,垂直趋势".split(",");
				inxs = "0,1,1,2".split(",");
			} else if (model == 4) {
				pns = MODEL4;
				pnames = "液压油箱油温,齿轮油油温".split(",");
				inxs = "0,1".split(",");
			} else if (model == 5) {
				pns = MODEL5;
				pnames = "千斤顶油压差（左右）,千斤顶行程差（左右）,盾尾水平偏差,水平趋势".split(",");
				inxs = "0,1,1,2".split(",");
			} else if (model == 6) {
				pns = MODEL6;
				pnames = "掘进速度,发泡剂注入流量".split(",");
				inxs = "0,1".split(",");
			} else if (model == 7) {
				pns = MODEL7;
				pnames = "注浆量,掘进速度".split(",");
				inxs = "0,1".split(",");
			} else if (model == 8) {
				pns = MODEL8;
				pnames = "前盾水平偏差,前盾垂直偏差,尾盾水平偏差,尾盾垂直偏差".split(",");
				inxs = "0,0,1,1".split(",");
			} else {
				pns = ks;
				pnames = kns;
				inxs = indxs;
			}
			for (String paramName : pns) {
				keys.add(
						IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), paramName));
			}
			String KEY_A0001 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(),
					"A0001");
			keys.add(KEY_A0001);
			List<String> pnamesl = new ArrayList<String>();
			Collections.addAll(pnamesl, pnames);
			misv.setpNames(pnamesl);

			IhistorianResponse ir = null;
			String key_h = "GZ" + mic.getLineNo() + "_" + mic.getIntervalMark() + leftOrRight.toUpperCase();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if ("t".equals(type)) {
				String[] bs = beginTime.split("/");
				String[] ns = endTime.split("/");
				String b = bs[2] + "-" + bs[0] + "-" + bs[1];
				String e = ns[2] + "-" + ns[0] + "-" + ns[1];
				try {
					ir = IhistorianUtil.getDataByKeysAndBeTime(keys, sdf.parse(b), sdf.parse(e), model, key_h);
				} catch (ParseException e1) {
					System.out.print(e1);
				}
			} else {
				List<String> rings = new ArrayList<String>();
				for (int i = beginRing; i <= endRing; i++) {
					rings.add(String.valueOf(i));
				}
				ir = IhistorianUtil.getDataByKeysAndBeRing(keys, rings, model, key_h);
			}
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				List<HashMap<String, HashMap>> list = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
				List<String> trKeys = new ArrayList<String>();
				List<MonitorStiaticParamView> values = new ArrayList<MonitorStiaticParamView>();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				for (Map map : list) {

					Iterator i = map.keySet().iterator();
					while (i.hasNext()) {
						String k = i.next().toString();
						// if("t".equals(type)){
						Date d = new Date(Long.parseLong(k) * 1000);
						HashMap<String, Object> iv = JSONArray.parseObject(map.get(k).toString(), HashMap.class);
						String ringNum = String.valueOf(iv.get(KEY_A0001));
						trKeys.add(sdf.format(d) + " " + ringNum);
						// }else{
						// trKeys.add(k);
						// }
						break;
					}
				}
				int keysSize = keys.size() - 1;
				for (int j = 0; j < keysSize; j++) {
					MonitorStiaticParamView mspv = new MonitorStiaticParamView();
					mspv.setName(pnamesl.get(j));
					mspv.setyAxisIndex(Integer.parseInt(inxs[j]));
					List<Object> data = new ArrayList<Object>();
					for (Map m : list) {
						Iterator ii = m.keySet().iterator();
						while (ii.hasNext()) {
							HashMap<String, Object> vv = JSONArray.parseObject(m.get(ii.next()).toString(),
									HashMap.class);
							data.add(vv.get(keys.get(j)));
							break;
						}
					}
					mspv.setData(data);
					values.add(mspv);
				}
				misv.setKeys(trKeys);
				misv.setValues(values);
			}
		}
		return misv;
	}

	@Override
	public int findCurrRingNum(String intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			List<String> keys = new ArrayList<String>();
			keys.add(IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0001"));
			IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				try {
					return Integer.parseInt(ir.getResult().get(keys.get(0)).toString());
				} catch (Exception e) {
					return 0;
				}
			}
		}
		return 0;
	}

}
