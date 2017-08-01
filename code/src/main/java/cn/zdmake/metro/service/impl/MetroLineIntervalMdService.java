package cn.zdmake.metro.service.impl;

import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.dao.IMetroLineIntervalMdDao;
import cn.zdmake.metro.model.MetroLineIntervalMd;
import cn.zdmake.metro.service.IMetroLineIntervalMdService;

/**
 * 地铁线路区间上传监测数据记录业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalMdService")
public class MetroLineIntervalMdService extends BaseService<MetroLineIntervalMd> implements IMetroLineIntervalMdService{
	
	private static Logger logger = Logger.getLogger(MetroLineIntervalMdService.class);

	@Autowired
	IMetroLineIntervalMdDao lineIntervalMdDao;

}
