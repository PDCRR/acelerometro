package elliot.rotacion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class moviment extends SurfaceView {

	private Bitmap image;
	private SurfaceHolder imgHld;
	public moviment(Context cont){
		super(cont);
		image = BitmapFactory.decodeResource(getResources(), R.drawable.unnamed);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(image, 100,200, null );
	}
}
