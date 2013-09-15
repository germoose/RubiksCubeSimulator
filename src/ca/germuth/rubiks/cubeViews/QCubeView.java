package ca.germuth.rubiks.cubeViews;

import ca.germuth.rubiks.cube.Colour;
import ca.germuth.rubiks.cube.Cube;
import ca.germuth.rubiks.cube.GenericCube;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class QCubeView extends CubeView {
    
    public QCubeView(Context context) {
        super(context);
        
    }
    
    public QCubeView(Context context, AttributeSet attrs){
    	super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
    	
    	//canvas.super.drawRect(0, 0, 467, 639, canvas, paint);
    	if( super.getMyCube() == null){
    		super.setMyCube(new GenericCube(3));
    	}
    	
    	GenericCube myCube = super.getMyCube();
    	Paint paint = super.getPaint();
    	
		paintFace(canvas, 95, 25, myCube.getTop()); //paints the top
		paintSide(canvas, false, myCube.getLeft()); //paints the left
		paintSide(canvas, true, myCube.getRight()); //paints the right
		paintFace(canvas, 95, 160, myCube.getFront()); //paints the front
		
    	canvas.drawLine(50, 70, 275, 70, paint);
		canvas.drawLine(50, 115, 275, 115, paint);
		
		canvas.drawLine(50, 205, 275, 205, paint);
		canvas.drawLine(50, 250, 275, 250, paint);
			
		canvas.drawLine(140, 25, 140, 295, paint);
		canvas.drawLine(185, 25, 185, 295, paint);
    }
    
    private void paintFace(Canvas canvas, int x, int y, Colour[][] side) {
    	
    	Colour temp = null;
    	Paint paint = super.getPaint();
    	
    	temp = side[0][0];
    	paint.setColor(colourToColor(temp));
    	super.drawRect(x, y, 45, 45, canvas, paint);
    	
    	temp = side[0][1];
    	paint.setColor(colourToColor(temp));
    	super.drawRect((x+45), y, 45, 45, canvas, paint);
    	temp = side[0][2];
    	paint.setColor(colourToColor(temp));
    	super.drawRect((x+90), y, 45, 45, canvas, paint);
    	
    	temp = side[1][0];
    	paint.setColor(colourToColor(temp));
    	super.drawRect(x, y+45, 45, 45, canvas, paint);
    	temp = side[1][1];
    	paint.setColor(colourToColor(temp));
    	super.drawRect((x+45), y+45, 45, 45, canvas, paint);
    	temp = side[1][2];
    	paint.setColor(colourToColor(temp));
    	super.drawRect(x+90, y+45, 45, 45, canvas, paint);
    	
    	temp = side[2][0];
    	paint.setColor(colourToColor(temp));
    	super.drawRect(x, y+90, 45, 45, canvas, paint);
    	temp = side[2][1];
    	paint.setColor(colourToColor(temp));
    	super.drawRect((x+45), y+90, 45, 45, canvas, paint);
    	temp = side[2][2];
    	paint.setColor(colourToColor(temp));
    	super.drawRect((x+90), y+90, 45, 45, canvas, paint);
    	
    	paint.setColor(Color.BLACK);
    	paint.setStyle(Paint.Style.STROKE);
    	
    	super.drawRect(x, y, 135, 135, canvas, paint);
    	
    	paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    
    private void paintSide(Canvas canvas, boolean isRight, Colour[][] side) {
    	Colour temp = null;
    	Paint paint = super.getPaint();
    	
		if(isRight){
			
			temp = side[0][0];
			paint.setColor(colourToColor(temp));
			super.drawRect(230, 25, 45, 45, canvas, paint);

			temp = side[1][0];
			paint.setColor(colourToColor(temp));
			super.drawRect(230, 25+45, 45, 45, canvas, paint);
			
			
			temp = side[2][0];
			paint.setColor(colourToColor(temp));
			super.drawRect(230, 25+90, 45, 90, canvas, paint);
			
			
			temp = side[2][1];
			paint.setColor(colourToColor(temp));
			super.drawRect(230, 25+180, 45, 45, canvas, paint);

			temp = side[2][2];
			paint.setColor(colourToColor(temp));
			super.drawRect(230, 25+225, 45, 45, canvas, paint);
			
		}
		else {
			
			temp = side[0][2];
			paint.setColor(colourToColor(temp));
			super.drawRect(50, 25, 45, 45, canvas, paint);

			temp = side[1][2];
			paint.setColor(colourToColor(temp));
			super.drawRect(50, 25+45, 45, 45, canvas, paint);
			
			
			temp = side[2][2];
			paint.setColor(colourToColor(temp));
			super.drawRect(50, 25+90, 45, 90, canvas, paint);
			
			
			temp = side[2][1];
			paint.setColor(colourToColor(temp));
			super.drawRect(50, 25+180, 45, 45, canvas, paint);

			temp = side[2][0];
			paint.setColor(colourToColor(temp));
			super.drawRect(50, 25+225, 45, 45, canvas, paint);
		}
	}
}