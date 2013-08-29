package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.germuth.rubiks.R;

public class ScoreScreenTitleBar extends RelativeLayout{
	private ImageView logo;
	private TextView title;
	private Button leaderboards;
	private Button close;
	
	public ScoreScreenTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(
	            Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate( R.layout.score_screen_title_bar,  this );
			    
	}
}
