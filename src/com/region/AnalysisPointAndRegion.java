package com.region;
import java.util.List;

/*
 * 围栏点判断类
 */
public class AnalysisPointAndRegion {
	private static double _dis =  0.0000000001;
	
	public static Boolean judgeMeetPoint(AyPoint ap,List<AyPoint> apl){
		//检测点为空或围栏为空，返回false
		if(ap == null || apl == null) return false;
		//检测围栏数组
		int len = apl.size();
		if(len <3) return false;
		//交点数
		int meetPointCount = 0;
		//围栏判断
		for(int k = 1 ; k < len; k++){
			AyPoint p1 = apl.get(k);
			AyPoint p2 = apl.get(k-1);
			//初步判断 大范围过滤
			if(    (ap.getX() > p1.getX() || ap.getX() < p2.getX() || (ap.getX() > p1.getX() || ap.getX() < p2.getX()))//x坐标在顶点线段范围外
					|| (ap.getY() < p1.getY() && ap.getY() < p2.getY()))
					continue;
			/*
			 * 检测点在线段范围内，进行交点计算
			 */
			//点在线上，认为在围栏内，则可返回true了
			if(judgePointInLine(p1,p2,ap))
				return true;
			//处理特殊情况，交点是端点的情况 
			double temp;
			//temp相当于被除数(斜率的分母)
			temp = p1.getX() - p2.getX();
			if (temp >= -_dis && temp <= _dis){
				//处理交点情况 
                double dx = ap.getX() - p1.getX();                                
                if(dx < -_dis || dx > _dis) continue;
                
                int[] indexs = new int[2];
                indexs[0] = 0;
                indexs[1] = 0;
                
                indexs = getNotSame(apl,k);
                
                AyPoint lineP1 = apl.get(indexs[0]);
                AyPoint lineP2 = apl.get(indexs[1]);
                
                if( k> indexs[0])
                	break;
                else
                	k = indexs[0] + 1;
                
                if( (ap.getY() > p1.getY())
                	&& ( (ap.getX() >= lineP1.getX() && ap.getX() <= lineP2.getX())
                		 || (ap.getX() >= lineP2.getX() && ap.getX() <= lineP2.getX())
                	   )
                   )
                	meetPointCount++;
            }else{
            	double kk;
            	double bb;
            	double meetPtX;
            	double meetPtY;
            	
            	kk = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
            	bb = p1.getY() - kk * p1.getX();
            	
            	meetPtY = kk * ap.getX() + bb;
            	meetPtX = ap.getX();
            	
            	//处理特殊情况，交点是端点的情况             	
                double dx = meetPtX - p1.getX();
                double dy = meetPtY - p1.getY();
                double dx2 = meetPtX - p2.getX();
                double dy2 = meetPtY - p2.getX();
                
                if(dx >= -_dis && dx <= _dis && dy >= -_dis && dy <= _dis){
                	AyPoint p3;
                	if(k == 1){
                		p3 = apl.get(len - 2);
                	}else{
                		p3 = apl.get(k - 2);
                	}
                	//提取交点的上下两点分别在垂线的两侧
                	if( (ap.getY() > meetPtY)
                		&& ((meetPtX >= p3.getY() && meetPtX <= p2.getX())
                			|| (meetPtX >= p2.getX() && meetPtX <= p3.getX())
                		   )
                	   ){
                		meetPointCount++;
                	}
                }else if(!(dx2 >= -_dis && dx2 <= _dis && dy2 >= -_dis && dy2 <= _dis)){
                	if(ap.getY() > meetPtY)
                		meetPointCount++;
                }
            }
		}
		if(meetPointCount % 2 == 1){
			return true;
		}
		return false;
	}
	
	/*
	 * 检测点是否在线上
	 * ap1和ap2是顶点
	 * ap是待检测点
	 */
	private static Boolean judgePointInLine(AyPoint ap1,AyPoint ap2,AyPoint ap){
		double dx1 = getDistance(ap1, ap2);
        double dx2 = getDistance(ap, ap1);
        double dx3 = getDistance(ap, ap2);
        double dx = dx3 + dx2 - dx1;

        if (dx >= -0.0000000001 && dx <= 0.0000000001)
        {
            return true;
        }        
		return false;
	}
	
	/*
	 * 求取两点之间的距离 
	 */
	private static double getDistance(AyPoint ap1,AyPoint ap2){
		double x = ap1.getX() - ap2.getX();
        if (x <= 0)
        {
            x = -x;
        }
        double y = ap1.getY() - ap2.getY();
        if (y <= 0)
        {
            y = -y;
        }

        return Math.sqrt(x * x + y * y);
	}
	
	/*
	 * 在链表中获取x轴不相同的点 
	 */
	private static int[] getNotSame(List<AyPoint> pts,int index){
		int[] indexs = new int[2];
		indexs[0] = -1;
		indexs[1] = -1;
		
		int size = pts.size();
		
		AyPoint buftpt;
		AyPoint tpt;
		
		tpt = pts.get(index);
		
		for(int i = index ; i < size ; i++){
			buftpt = pts.get(i);
			if(buftpt.getX() == tpt.getX()) continue;
			indexs[0] = i;
			break;
		}
		
		if(indexs[0] == -1){
			for(int i = 0 ; i < size ; i++){
				buftpt = pts.get(i);
				if(buftpt.getX() == tpt.getX()) continue;
				indexs[0] = i;
				break;
			}
		}
		
		for(int i = index ; i >= 0 ; i--){
			buftpt = pts.get(i);
			if(buftpt.getX() == tpt.getX()) continue;
			indexs[1] = i;
			break;
		}
		
		if(indexs[1] == -1){
			for(int i = size - 1  ; i >= 0 ; i--){
				buftpt = pts.get(i);
				if(buftpt.getX() == tpt.getX()) continue;
				indexs[0] = i;
				break;
			}
		}
		
		return indexs;
	}
}
