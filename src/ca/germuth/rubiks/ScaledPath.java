package ca.germuth.rubiks;

import android.graphics.Path;
import cubeViews.HiGamesView;

public class ScaledPath extends Path{
	
	private HiGamesView hv;
	private static int maxX = 414;
	private static int maxY = 480;
	
	private double xScale;
	private double yScale;
	
	public ScaledPath(HiGamesView hv){
		super();
		
		this.hv = hv;
		
		this.xScale = (double)this.hv.getWidth() / (double)maxX;
		this.yScale = (double)this.hv.getHeight() / (double)maxY;
	}

	@Override
	public void lineTo(float x, float y) {
		double xT = (double)x * this.xScale;
		double yT = (double)y * this.yScale;
		
		super.lineTo( (float) xT, (float) yT);
	}

	@Override
	public void moveTo(float x, float y) {
		double xT = (double)x * this.xScale;
		double yT = (double)y * this.yScale;
		
		super.moveTo( (float) xT, (float) yT);
	}
	
	
	
}
