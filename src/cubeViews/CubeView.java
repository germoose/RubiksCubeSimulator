package cubeViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import ca.germuth.rubiks.Colour;
import ca.germuth.rubiks.Cube;

public class CubeView extends View{
	private Paint paint;
    private Cube myCube;
    
    public CubeView(Context context) {
        super(context);
        this.paint = new Paint();
        this.myCube = new Cube();
        
        paint.setColor(Color.BLACK);
    }
    
    public CubeView(Context context, AttributeSet attrs){
    	super(context, attrs);
    }
    
    public int colourToColor(Colour c) {
		switch(c) {
		case WHITE: return Color.WHITE;
		case YELLOW: return Color.YELLOW;
		case RED: return Color.RED;
		case BLUE: return Color.BLUE;
		case GREEN: return Color.GREEN;
		case ORANGE: return Color.rgb(255, 127, 0);
		default: return Color.BLACK;	
		}
	}
    
    @Override
    public void onDraw(Canvas canvas){
    	System.err.println("This method must be overriden");
    }
    
    public void drawRect(int x, int y, int width, int height, Canvas canvas, Paint paint){
    	canvas.drawRect(x, y, x + width, y + height, paint);
    }

	public Cube getMyCube() {
		return myCube;
	}

	public void setMyCube(Cube myCube) {
		this.myCube = myCube;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
}
