package cn.zdmake.metro.base.utils;


/**
 * Created by wangcan on 2017/2/13.
 */
public class MathUtil {

    public static double getHdeviation(double x0, double y0, double x1, double y1, double x2, double y2) {
        double deltaX = (x2-x1);
        double deltaY = (y2-y1);
        double abs = Math.abs(deltaX*(y0-y1) - deltaY*(x0-x1));
        double px = deltaX * deltaX;
        double py = deltaY * deltaY;
        double sqrt = Math.sqrt(px + py);
        return abs/sqrt * 1000.0D;
    }

    public static double getVdeviation(double z0, double z1, double z2, double mileage0, double mileage1, double mileage2) {
    	double deltaZ;
        double min = mileage0-mileage1;
        double max = mileage2-mileage0;
        if(min < max){
            deltaZ = z0 -z1;
        }else{
            deltaZ = z0 -z2;
        }
        
        return deltaZ*1000.0D;
    }

    public static Boolean checkStatus(Integer category, Integer status){

        if(category == null){
            return true;
        }
        if(status>2 && status<=3){
            if(category == 2 || category==3 || category==6 || category == 7 ){
                return true;
            }
        }else if(status>3 && status<=4){
            if(category == 1 || category==3 || category==5 || category == 7 ){
                return true;
            }
        }else {
            if(category == 4 || category==5 || category==6 || category == 7 ){
                return true;
            }
        }
        return false;
    }


	public static Boolean isZero(String MapX, String MapY, String MapZ)
	{
		if ((MapX.equals("0")) && (MapY.equals("0")) && (MapZ.equals("0"))) {
			return true;
		}
		return false;
	}
	
}