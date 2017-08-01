package cn.zdmake.metro.base.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.model.MetroLineIntervalLr;
import cn.zdmake.metro.model.MetroLine;
import cn.zdmake.metro.model.MetroLineInterval;
import cn.zdmake.metro.vo.Jstree;
import cn.zdmake.metro.vo.Jstree.State;

/**
 * 通用权限树
 * @author MAJL
 *
 */
public class JsTreeUtil {
	
	/**
	 * 权限树json
	 * @param request
	 * @param city
	 * @param urls String[4] 0city 1line 2interval 3lr	str
	 * @param diss Boolean[4] 0city 1line 2interval 3lr  true/false
	 * @return
	 */
	public static String getTreeData(HttpServletRequest request, MetroCity city, String[] urls, Boolean[] diss){
		List<Jstree> tl = new ArrayList<Jstree>();
		Jstree tree = new Jstree();
		int ln = 0;
		//组装树
		if(CommonUtils.isNotNull(city)){
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			if(urls[0]!=null&&!"".equals(urls[0])){
				tree.setUrl(request.getContextPath()+urls[0]+"?cityId="+city.getId());
			}			
			State cityState = tree.new State();
			cityState.setOpened(true);			
			cityState.setDisabled(diss[0]!=null?diss[0]:false);
			if(!(diss[0]!=null?diss[0]:false)){
				cityState.setSelected(true);
			}
			tree.setState(cityState);
			//线路
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if(CommonUtils.isNotNull(city.getLineList())){
				for(MetroLine line : city.getLineList()){
					Jstree cityChild = new Jstree();
					cityChild.setId("line_"+line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					if(urls[1]!=null&&!"".equals(urls[1])){
						cityChild.setUrl(request.getContextPath()+urls[1]+"?lineId="+line.getId());
					}
					State lineState = cityChild.new State();
					lineState.setOpened(true);
					lineState.setDisabled(diss[1]!=null?diss[1]:false);
					cityChild.setState(lineState);
					//区间
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if(CommonUtils.isNotNull(line.getIntervalList())){
						for(MetroLineInterval interval : line.getIntervalList()){
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_"+interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							if(urls[2]!=null&&!"".equals(urls[2])){
								lineChild.setUrl(request.getContextPath()+urls[2]+"?intervalId="+interval.getId());
							}
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							intervalState.setDisabled(diss[2]!=null?diss[2]:false);
							lineChild.setState(intervalState);
							
							//左右线
							List<Jstree> intervalChilds = new ArrayList<Jstree>();
							for(MetroLineIntervalLr lr : interval.getIntervalLrList()){
								Jstree intervalChild = new Jstree();
								intervalChild.setId("lr_"+lr.getId()+lr.getLeftOrRight());
								if("l".equals(lr.getLeftOrRight())){
									intervalChild.setIcon("left");
									intervalChild.setText("左线");
								}else{
									intervalChild.setIcon("right");
									intervalChild.setText("右线");
								}
								intervalChild.setType("lr");
								if(urls[3]!=null&&!"".equals(urls[3])){
									intervalChild.setUrl(request.getContextPath()+urls[3]+"?intervalId="+interval.getId()+"&leftOrRight="+lr.getLeftOrRight());
								}
								State lrState = intervalChild.new State();
								lrState.setOpened(true);
								lrState.setDisabled(diss[3]!=null?diss[3]:false);
								if(ln==0&&diss[2]!=null&&diss[2]==true&&(diss[3]!=null?diss[3]:false)==false){
									lrState.setSelected(true);
									ln++;
								}
								intervalChild.setState(lrState);
								intervalChilds.add(intervalChild);
							}
							lineChild.setChildren(intervalChilds);
							lineChilds.add(lineChild);
						}
						cityChild.setChildren(lineChilds);
					}
					cityChilds.add(cityChild);
				}
				tree.setChildren(cityChilds);
			}
		}
		tl.add(tree);
		return GsonUtils.toJson(tl);
	}
}
