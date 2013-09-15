package ca.germuth.rubiks.cubeViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import ca.germuth.rubiks.cube.Colour;
import ca.germuth.rubiks.cube.Cube;
import ca.germuth.rubiks.cube.GenericCube;
import ca.germuth.rubiks.util.Chronometer;

public class HiGamesView extends CubeView{
	private int inspection;
	private Chronometer chrono;
	private Handler solvedHandler;
	private boolean solving;
	private static String DEBUG_TAG="DEBUG";
	
	private static float tempX;
	private static float tempY;
	
	private static float temp2X;
	private static float temp2Y;
	
	public HiGamesView(Context context) {
		super(context);
		this.inspection = -1;
		this.solvedHandler = null;
	}
	
	public HiGamesView(Context context, AttributeSet attrs){
    	super(context, attrs);
    	this.inspection = -1;
    	this.solving = false;
    }
	
	public void drawNumber(Canvas canvas, Paint paint){
		paint.setColor(this.colourToColor(Colour.WHITE));
		paint.setTextSize(400);
		paint.setAlpha(130);
		int w = this.getWidth();
		w /= 4;
		int h = this.getHeight();
		h = 3 * h / 4;
		if( this.inspection > 9){
			w =- 25;
		}
		canvas.drawText(this.inspection+"", w, h, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int x = event.getActionMasked();
		//index = event.getActionIndex();
		switch(x){
		case MotionEvent.ACTION_DOWN:
			tempX = event.getX();
			tempY = event.getY();
			System.out.println(tempX + ", " + tempY);
			break;
		case MotionEvent.ACTION_UP:
			if (this.inspection == 0 || this.inspection == -1) {
				float startX = tempX;
				float startY = tempY;
				float finalX = event.getX();
				float finalY = event.getY();
				// R TURNS
				if ((close(startX, 358) && close(startY, 170)
						&& close(finalX, 337) && close(finalY, 30))
						|| (close(startX, 337) && close(startY, 410)
								&& close(finalX, 358) && close(finalY, 270))) {
					super.getMyCube().RTurn();
					this.invalidate();
				}
				if ((close(startX, 337) && close(startY, 30)
						&& close(finalX, 358) && close(finalY, 170))
						|| (close(startX, 358) && close(startY, 270)
								&& close(finalX, 337) && close(finalY, 410))) {
					super.getMyCube().RPrimeTurn();
					this.invalidate();
				}
				// L TURNS
				if ((close(startX, 95) && close(startY, 30)
						&& close(finalX, 80) && close(finalY, 170))
						|| (close(startX, 78) && close(startY, 270)
								&& close(finalX, 95) && close(finalY, 410))) {
					super.getMyCube().LTurn();
					this.invalidate();
				}
				if ((close(startX, 95) && close(startY, 410)
						&& close(finalX, 78) && close(finalY, 270))
						|| (close(startX, 80) && close(startY, 170)
								&& close(finalX, 95) && close(finalY, 30))) {
					super.getMyCube().LPrimeTurn();
					this.invalidate();
				}
				// U TURNS
				if ((close(startX, 358) && close(startY, 270)
						&& close(finalX, 78) && close(finalY, 270))) {
					super.getMyCube().UTurn();
					this.invalidate();
				}
				if ((close(startX, 78) && close(startY, 270)
						&& close(finalX, 358) && close(finalY, 270))) {
					super.getMyCube().UPrimeTurn();
					this.invalidate();
				}
				// F TURNS
				if ((close(startX, 80) && close(startY, 170)
						&& close(finalX, 358) && close(finalY, 170))) {
					super.getMyCube().FTurn();
					this.invalidate();
				}
				if ((close(startX, 358) && close(startY, 170)
						&& close(finalX, 80) && close(finalY, 170))) {
					super.getMyCube().FPrimeTurn();
					this.invalidate();
				}

				// D TURNS
				if ((close(startX, 95) && close(startY, 410)
						&& close(finalX, 337) && close(finalY, 410))) {
					super.getMyCube().DTurn();
					this.invalidate();
				}
				if ((close(startX, 337) && close(startY, 410)
						&& close(finalX, 95) && close(finalY, 410))) {
					super.getMyCube().DPrimeTurn();
					this.invalidate();
				}

			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN: 
			temp2X = event.getX();
			temp2Y = event.getY();
			break;
		case MotionEvent.ACTION_POINTER_UP: 
			float endX = event.getX();
			float beginX = temp2X;
			if( endX >= beginX + 100){
				super.getMyCube().yTurn();
				this.invalidate();
			}
			if( endX <= beginX - 100){
				super.getMyCube().yPrimeTurn();
				this.invalidate();
			}
			
			float endY = event.getY();
			float beginY = temp2Y;
			if( endY >= beginY + 100){
				super.getMyCube().xPrimeTurn();
				this.invalidate();
			}
			if( endY <= beginY - 100){
				super.getMyCube().xTurn();
				this.invalidate();
			}
			
		break;
		default: 
			break;
		}
		return true;
	     // Be sure to call the superclass implementation
	    //return super.onTouchEvent(event);
	}
	
	private boolean close(float num, float around){
		int diff = (int) (num - around);
		if( diff <= 40 && diff >= -40){
			return true;
		}
		return false;
	}

	@Override
    public void onDraw(Canvas canvas) {
    	
    	GenericCube myCube = super.getMyCube();
    	Paint paint = super.getPaint();
		
    	paint.setStyle(Style.FILL);
    	
    	Colour[][] right = myCube.getRight();
    	
    	Colour temp = right[0][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath ScaledPath = new ScaledPath(this);
    	ScaledPath.moveTo(363, 17); // used for first point
    	ScaledPath.lineTo(373, 69);
    	ScaledPath.lineTo(354, 70);
    	ScaledPath.lineTo(363, 17); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[0][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(347, 122); // used for first point
    	ScaledPath.lineTo(355, 121);
    	ScaledPath.lineTo(361, 143);
    	ScaledPath.lineTo(345, 144);
    	ScaledPath.lineTo(347, 122); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[0][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(341, 207); // used for first point
    	ScaledPath.lineTo(349, 220);
    	ScaledPath.lineTo(341, 230);
    	ScaledPath.lineTo(333, 217);
    	ScaledPath.lineTo(341, 207); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[1][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(378, 78); // used for first point
    	ScaledPath.lineTo(390, 140);
    	ScaledPath.lineTo(367, 150);
    	ScaledPath.lineTo(378, 78); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[1][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(363, 205); // used for first point
    	ScaledPath.lineTo(374, 219);
    	ScaledPath.lineTo(364, 233);
    	ScaledPath.lineTo(352, 220);
    	ScaledPath.lineTo(363, 205); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[1][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(360, 294); // used for first point
    	ScaledPath.lineTo(357, 317);
    	ScaledPath.lineTo(347, 314);
    	ScaledPath.lineTo(345, 296);
    	ScaledPath.lineTo(360, 294); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[2][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(393, 151); // used for first point
    	ScaledPath.lineTo(409, 219);
    	ScaledPath.lineTo(393, 288);
    	ScaledPath.lineTo(373, 220);
    	ScaledPath.lineTo(393, 151); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[2][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(390, 300); // used for first point
    	ScaledPath.lineTo(376, 360);
    	ScaledPath.lineTo(366, 316);
    	ScaledPath.lineTo(369, 283);
    	ScaledPath.lineTo(390, 300); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = right[2][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(363, 423); // used for first point
    	ScaledPath.lineTo(374, 371);
    	ScaledPath.lineTo(355, 360);
    	ScaledPath.lineTo(356, 402);
    	ScaledPath.lineTo(363, 423); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	Colour[][] left = myCube.getLeft();

    	
    	temp = left[0][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(66, 220); // used for first point
    	ScaledPath.lineTo(74, 202);
    	ScaledPath.lineTo(83, 219);
    	ScaledPath.lineTo(74, 233);
    	ScaledPath.lineTo(66, 220); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);

    	temp = left[0][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(54, 143); // used for first point
    	ScaledPath.lineTo(61, 123);
    	ScaledPath.lineTo(66, 121);
    	ScaledPath.lineTo(71, 142);
    	ScaledPath.lineTo(54, 143); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[0][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(41, 69); // used for first point
    	ScaledPath.lineTo(53, 16);
    	ScaledPath.lineTo(59, 73);
    	ScaledPath.lineTo(41, 69); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[1][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(64, 319); // used for first point
    	ScaledPath.lineTo(55, 300);
    	ScaledPath.lineTo(70, 300);
    	ScaledPath.lineTo(64, 319); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[1][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(42, 219); // used for first point
    	ScaledPath.lineTo(53, 198);
    	ScaledPath.lineTo(63, 220);
    	ScaledPath.lineTo(54, 236);
    	ScaledPath.lineTo(42, 219); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[1][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(26, 142); // used for first point
    	ScaledPath.lineTo(39, 78);
    	ScaledPath.lineTo(53, 142);
    	ScaledPath.lineTo(36, 156);
    	ScaledPath.lineTo(26, 142); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[2][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(42, 371); // used for first point
    	ScaledPath.lineTo(53, 423);
    	ScaledPath.lineTo(60, 382);
    	ScaledPath.lineTo(59, 365);
    	ScaledPath.lineTo(42, 371); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[2][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(40, 361); // used for first point
    	ScaledPath.lineTo(25, 300);
    	ScaledPath.lineTo(48, 280);
    	ScaledPath.lineTo(50, 307);
    	ScaledPath.lineTo(40, 361); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = left[2][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(7, 219); // used for first point
    	ScaledPath.lineTo(23, 151);
    	ScaledPath.lineTo(36, 219);
    	ScaledPath.lineTo(23, 287);
    	ScaledPath.lineTo(7, 219); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	
 
    	
    	
    	Colour[][] back = myCube.getBack();
    	
    	temp = back[0][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(90, 214); // used for first point
    	ScaledPath.lineTo(159, 214);
    	ScaledPath.lineTo(159, 162);
    	ScaledPath.lineTo(90, 162);
    	ScaledPath.lineTo(90, 214); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[0][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(174, 217); // used for first point
    	ScaledPath.lineTo(242, 217);
    	ScaledPath.lineTo(242, 206);
    	ScaledPath.lineTo(174, 206);
    	ScaledPath.lineTo(174, 217); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[0][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(257, 214); // used for first point
    	ScaledPath.lineTo(326, 214);
    	ScaledPath.lineTo(326, 163);
    	ScaledPath.lineTo(261, 163); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[1][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(156, 151); // used for first point
    	ScaledPath.lineTo(152, 92);
    	ScaledPath.lineTo(77, 92);
    	ScaledPath.lineTo(82, 151); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(156, 151);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[1][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(246, 144); // used for first point
    	ScaledPath.lineTo(246, 121);
    	ScaledPath.lineTo(168, 121);
    	ScaledPath.lineTo(171, 144);
    	ScaledPath.lineTo(242, 144); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[1][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(263, 92); // used for first point
    	ScaledPath.lineTo(338, 92);
    	ScaledPath.lineTo(335, 152);
    	ScaledPath.lineTo(260, 152); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(263, 92);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[2][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(152, 78); // used for first point
    	ScaledPath.lineTo(149, 19);
    	ScaledPath.lineTo(64, 19);
    	ScaledPath.lineTo(74, 78); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(152, 78);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[2][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(171, 72); // used for first point
    	ScaledPath.lineTo(166, 50);
    	ScaledPath.lineTo(250, 50);
    	ScaledPath.lineTo(244, 72);
    	ScaledPath.lineTo(171, 72); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = back[2][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(263, 77); // used for first point
    	ScaledPath.lineTo(267, 18);
    	ScaledPath.lineTo(350, 18);
    	ScaledPath.lineTo(343, 77);
    	ScaledPath.lineTo(263, 77); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	
    	
    	
    	Colour[][] bottum = myCube.getBottom();
    	
    	temp = bottum[0][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(152, 362); // used for first point
    	ScaledPath.lineTo(150, 422);
    	ScaledPath.lineTo(61, 422);
    	ScaledPath.lineTo(75, 362);
    	ScaledPath.lineTo(152, 362);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[0][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(249, 390); // used for first point
    	ScaledPath.lineTo(246, 367);
    	ScaledPath.lineTo(169, 367);
    	ScaledPath.lineTo(166, 390);
    	ScaledPath.lineTo(249, 390); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[0][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(266, 420); // used for first point
    	ScaledPath.lineTo(263, 362);
    	ScaledPath.lineTo(342, 362);
    	ScaledPath.lineTo(349, 420); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(266, 420);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[1][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(152, 347); // used for first point
    	ScaledPath.lineTo(156, 288);
    	ScaledPath.lineTo(81, 288);
    	ScaledPath.lineTo(75, 347); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(152, 347);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[1][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(170, 317); // used for first point
    	ScaledPath.lineTo(173, 295);
    	ScaledPath.lineTo(243, 295);
    	ScaledPath.lineTo(246, 317);
    	ScaledPath.lineTo(170, 317); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[1][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(263, 347); // used for first point
    	ScaledPath.lineTo(259, 289);
    	ScaledPath.lineTo(335, 289);
    	ScaledPath.lineTo(337, 347); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(263, 347);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[2][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(159, 225); // used for first point
    	ScaledPath.lineTo(90, 225);
    	ScaledPath.lineTo(87, 276);
    	ScaledPath.lineTo(157, 276); // there is a setLastPoint action but i found it not to work as expected
    	ScaledPath.lineTo(159, 225);
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[2][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(174, 225); // used for first point
    	ScaledPath.lineTo(241, 225);
    	ScaledPath.lineTo(241, 250);
    	ScaledPath.lineTo(174, 250);
    	ScaledPath.lineTo(174, 225); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	temp = bottum[2][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath.reset();
    	ScaledPath.moveTo(257, 225); // used for first point
    	ScaledPath.lineTo(325, 225);
    	ScaledPath.lineTo(332, 276);
    	ScaledPath.lineTo(259, 276);
    	ScaledPath.lineTo(257, 225); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(ScaledPath, paint);
    	
    	
    	
    	
    	Colour[][] top = myCube.getTop();
    	
    	temp = top[0][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath one = new ScaledPath(this);
    	one.moveTo(60, 6); // used for first point
    	one.lineTo(148, 6);
    	one.lineTo(145, 56);
    	one.lineTo(50, 56);
    	one.lineTo(60, 6); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(one, paint);
    	
    	temp = top[0][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath two = new ScaledPath(this);
    	two.moveTo(166, 6); // used for first point
    	two.lineTo(250, 6);
    	two.lineTo(254, 56);
    	two.lineTo(162, 56);
    	two.lineTo(166, 6); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(two, paint);
    	
    	temp = top[0][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath three = new ScaledPath(this);
    	three.moveTo(267, 6); // used for first point
    	three.lineTo(355, 6);
    	three.lineTo(367, 56);
    	three.lineTo(272, 56);
    	three.lineTo(267, 6); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(three, paint);
    	
    	temp = top[1][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath four = new ScaledPath(this);
    	four.moveTo(47, 66); // used for first point
    	four.lineTo(143, 66);
    	four.lineTo(138, 127);
    	four.lineTo(35, 127);
    	four.lineTo(47, 66); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(four, paint);
    	
    	temp = top[1][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath UC = new ScaledPath(this);
    	UC.moveTo(160, 65); // used for first point
    	UC.lineTo(255, 65);
    	UC.lineTo(258, 126);
    	UC.lineTo(157, 126);
    	UC.lineTo(160, 65); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(UC, paint);
    	
    	temp = top[1][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath UMR = new ScaledPath(this);
    	UMR.moveTo(272, 65); // used for first point
    	UMR.lineTo(368, 65);
    	UMR.lineTo(380, 126);
    	UMR.lineTo(279, 126);
    	UMR.lineTo(272, 65); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(UMR, paint);
    	
    	temp = top[2][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath UFL = new ScaledPath(this);
    	UFL.moveTo(32, 140); // used for first point
    	UFL.lineTo(137, 140);
    	UFL.lineTo(130, 212);
    	UFL.lineTo(17, 212);
    	UFL.lineTo(32, 140); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(UFL, paint);
    	
    	temp = top[2][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath UMF = new ScaledPath(this);
    	UMF.moveTo(156, 140); // used for first point
    	UMF.lineTo(259, 140);
    	UMF.lineTo(264, 212);
    	UMF.lineTo(151, 212);
    	UMF.lineTo(156, 140); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(UMF, paint);
    	
    	temp = top[2][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath UFR = new ScaledPath(this);
    	UFR.moveTo(278, 140); // used for first point
    	UFR.lineTo(383, 140);
    	UFR.lineTo(398, 212);
    	UFR.lineTo(285, 212);
    	UFR.lineTo(278, 140); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(UFR, paint);
    	
    	
    	
    	
    	
    	Colour[][] front = myCube.getFront();
    	
    	temp = front[0][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FUL = new ScaledPath(this);
    	FUL.moveTo(17, 227); // used for first point
    	FUL.lineTo(129, 229);
    	FUL.lineTo(138, 299);
    	FUL.lineTo(32, 299);
    	FUL.lineTo(17, 227); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FUL, paint);
    	
    	temp = front[0][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FUM = new ScaledPath(this);
    	FUM.moveTo(152, 227); // used for first point
    	FUM.lineTo(264, 227);
    	FUM.lineTo(260, 298);
    	FUM.lineTo(156, 298);
    	FUM.lineTo(152, 227); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FUM, paint);
    	
    	temp = front[0][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FUR = new ScaledPath(this);
    	FUR.moveTo(286, 227); // used for first point
    	FUR.lineTo(398, 227);
    	FUR.lineTo(384, 300);
    	FUR.lineTo(278, 300);
    	FUR.lineTo(286, 227); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FUR, paint);
    	
    	temp = front[1][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FML = new ScaledPath(this);
    	FML.moveTo(34, 313); // used for first point
    	FML.lineTo(137, 313);
    	FML.lineTo(142, 373);
    	FML.lineTo(47, 373);
    	FML.lineTo(34, 313); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FML, paint);
    	
    	temp = front[1][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FC = new ScaledPath(this);
    	FC.moveTo(158, 313); // used for first point
    	FC.lineTo(257, 313);
    	FC.lineTo(255, 372);
    	FC.lineTo(161, 372);
    	FC.lineTo(158, 313); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FC, paint);
    	
    	temp = front[1][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FMR = new ScaledPath(this);
    	FMR.moveTo(279, 313); // used for first point
    	FMR.lineTo(380, 313);
    	FMR.lineTo(369, 373);
    	FMR.lineTo(273, 373);
    	FMR.lineTo(279, 313); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FMR, paint);
    	
    	temp = front[2][0];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FDL = new ScaledPath(this);
    	FDL.moveTo(50, 384); // used for first point
    	FDL.lineTo(143, 384);
    	FDL.lineTo(149, 433);
    	FDL.lineTo(61, 433);
    	FDL.lineTo(50, 384); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FDL, paint);
    	
    	temp = front[2][1];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FDM = new ScaledPath(this);
    	FDM.moveTo(163, 384); // used for first point
    	FDM.lineTo(253, 384);
    	FDM.lineTo(251, 433);
    	FDM.lineTo(166, 433);
    	FDM.lineTo(163, 384); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FDM, paint);
    	
    	temp = front[2][2];
    	paint.setColor(colourToColor(temp));
    	ScaledPath FDR = new ScaledPath(this);
    	FDR.moveTo(272, 384); // used for first point
    	FDR.lineTo(366, 384);
    	FDR.lineTo(355, 433);
    	FDR.lineTo(268, 433);
    	FDR.lineTo(272, 384); // there is a setLastPoint action but i found it not to work as expected
    	canvas.drawPath(FDR, paint);
    	
    	super.getMyCube().checkSolved();
    	if( super.getMyCube().isSolved() && this.solving ){
    		this.solvedHandler.sendEmptyMessage(0);
    	}  	
    	
    	if( this.inspection != -1 && !this.solving){
    		drawNumber(canvas, paint);
    	}
	}

	/**
	 * @param inspection the inspection to set
	 */
	public void setInspection(int inspection) {
		this.inspection = inspection;
	}
	
	public void setChrono(Chronometer chron){
		this.chrono = chron;
	}
	
	public Chronometer getChrono(){
		return this.chrono;
	}

	/**
	 * @return the solvedHandler
	 */
	public Handler getSolvedHandler() {
		return solvedHandler;
	}
	/**
	 * @param solvedHandler the solvedHandler to set
	 */
	public void setSolvedHandler(Handler solvedHandler) {
		this.solvedHandler = solvedHandler;
	}

	/**
	 * @return the solving
	 */
	public boolean isSolving() {
		return solving;
	}

	/**
	 * @param solving the solving to set
	 */
	public void setSolving(boolean solving) {
		this.solving = solving;
	}
}
