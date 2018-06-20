package com.region;
import java.util.ArrayList;
import java.util.List;

public class TestRegion {
	public static void main(String[] args){
		check();
	}
	
	private static void check(){
		AyPoint p = new AyPoint(117.18137,39.13884);
		List<AyPoint> apl = new ArrayList<AyPoint>();
		//117.18137,39.13884,117.18568,39.13874,117.18560,39.13709,117.18139,39.13724
		apl.add(new AyPoint(117.18137,39.13884));
		apl.add(new AyPoint(117.18568,39.13874));
		apl.add(new AyPoint(117.18560,39.13709));
		apl.add(new AyPoint(117.18139,39.13724));
		//apl.add(new AyPoint(117.1171,39.392));
		boolean b = AnalysisPointAndRegion.judgeMeetPoint(p, apl);
		String isIn = b?"内":"外";
		System.out.print("点在围栏  " + isIn);
	}
}
