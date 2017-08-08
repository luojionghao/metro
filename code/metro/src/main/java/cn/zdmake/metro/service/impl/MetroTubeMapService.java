package cn.zdmake.metro.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.base.utils.IhistorianUtil;
import cn.zdmake.metro.dao.IMetroTubeMapDao;
import cn.zdmake.metro.model.TubeMapInfo;
import cn.zdmake.metro.service.IMetroTubeMapService;
import cn.zdmake.metro.vo.DataChild;
import cn.zdmake.metro.vo.IhistorianResponse;
import cn.zdmake.metro.vo.TubeMapIntervalLr;

@Service("tmService")
public class MetroTubeMapService implements IMetroTubeMapService {
	@Autowired
	private IMetroTubeMapDao tubeDao;

	@Override
	public TubeMapIntervalLr findLrInfo(Long userId, Long intervalId, Date loginTime) {
		TubeMapIntervalLr lr = new TubeMapIntervalLr();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.put("loginTime", sdf.format(loginTime));
		List<TubeMapInfo> lrs = tubeDao.findTubeMapInfo(params);
		List<String> keys = new ArrayList<String>();
		for (TubeMapInfo tm : lrs) {
			DataChild dc = new DataChild();
			String key = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0001");
			keys.add(key);
			key = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0003");
			keys.add(key);
			String warn = tm.getWarningLevel() != null ? (tm.getWarningLevel() == 1 ? "[红色上限预警]"
					: (tm.getWarningLevel() == 2 ? "[橙色上限预警]" : (tm.getWarningLevel() == 3 ? "橙色下限预警" : "红色下限预警")))
					: null;
			dc.setAlarm(StringUtil.nullToString(warn) + " " + StringUtil.nullToString(tm.getDicMean()) + " "
					+ StringUtil.nullToString(tm.getNumValue() != null ? tm.getNumValue().toString() : "") + " "
					+ StringUtil.nullToString(tm.getDicUnit()));
			dc.setBuildStatus(tm.getBuildStatus());
			dc.setDataTime(Calendar.getInstance().getTime());
			if ("l".equals(tm.getLeftOrRight())) {
				lr.setLeft(dc);
			} else {
				lr.setRight(dc);
			}
		}
		IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);
		for (TubeMapInfo tm : lrs) {
			String key1 = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0001");
			String key2 = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0002");
			if (ir != null) {
				try {
					if ("l".equals(tm.getLeftOrRight())) {
						if (ir.getResult() != null) {
							//String result = GsonUtils.toJson(ir.getResult().get(key1));
							String result = new ObjectMapper().writeValueAsString(ir.getResult().get(key1));
							ObjectMapper mapper = new ObjectMapper();
							Map<String, Object> resultMap = (Map) mapper.readValue(result, HashMap.class);
							lr.getLeft().setCurrentRing(StringUtil.nullToInt(resultMap.get("ring").toString()));
							int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
							lr.getLeft().setCommuniStatus(quality == 100 ? 1 : 0);
						} else {
							lr.getLeft().setCurrentRing(0);
							lr.getLeft().setAdvanceStatus(0);
						}
						lr.getLeft().setAdvanceStatus(StringUtil
								.nullToInt(ir.getResult() != null ? ir.getResult().get(key2).toString() : null));
					} else {
						if (ir.getResult() != null) {
//							String result = GsonUtils.toJson(ir.getResult().get(key1));
							String result = new ObjectMapper().writeValueAsString(ir.getResult().get(key1));
							ObjectMapper mapper = new ObjectMapper();
							Map<String, Object> resultMap = (Map) mapper.readValue(result, HashMap.class);
							lr.getRight().setCurrentRing(StringUtil.nullToInt(resultMap.get("ring").toString()));
							int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
							lr.getRight().setCommuniStatus(quality == 100 ? 1 : 0);
						} else {
							lr.getRight().setCurrentRing(0);
							lr.getRight().setAdvanceStatus(0);
						}
						lr.getRight().setAdvanceStatus(StringUtil
								.nullToInt(ir.getResult() != null ? ir.getResult().get(key2).toString() : null));
					}
				} catch (Exception e) {

				}
			}
		}
		return lr;
	}

}
