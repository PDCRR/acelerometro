package elliot.rotacion;

import java.util.List;





//import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
//import android.graphics.drawable.GradientDrawable.Orientation;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.net.MailTo;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * @author Ellioth
 *
 */

public class Rotacion extends Activity implements SensorEventListener {

	private long AntActualizacion=0, AntMovimiento=0;
    private float AntX=0, AntY=0, AntZ=0;
    private float ActX=0, ActY=0, ActZ=0;
    private MediaPlayer sonido;
    private Bitmap img;
    private SurfaceHolder imgHld; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}
	
	public void enviarEjes(View eje){
		//sonido= MediaPlayer.create( Rotacion.this, R.raw.disparo_1);
		if(eje.getId()==R.id.btnEjes){
			sonido= MediaPlayer.create( Rotacion.this, R.raw.disparo_1);
			sonido.start();
			
			/*((TextView) findViewById(R.id.txtEjeX)).setText("Acelerometro X: " + ActX);
            ((TextView) findViewById(R.id.txtEjeY)).setText("Acelerometro Y: " + ActY);
            ((TextView) findViewById(R.id.txtEjeZ)).setText("Acelerometro Z: " + ActZ);*/
		}
		//Toast.makeText(getApplicationContext(), "se apreto botón", Toast.LENGTH_SHORT).show();
	}
	
	public void onSensorChanged(SensorEvent event) {
        synchronized (this){
            long tiempoActual = event.timestamp;
            
            ActX = event.values[0];
            ActY = event.values[1];
            ActZ = event.values[2];
            
            if (AntX == 0 && AntY == 0 && AntZ == 0) {
                AntActualizacion = tiempoActual;
                AntMovimiento = tiempoActual;
                AntX = ActX;
                AntY = ActY;
                AntZ = ActZ;
            }
            
            long DifTiempo = tiempoActual - AntActualizacion;
            
            if (DifTiempo > 0) {
                float movimiento = Math.abs((ActX + ActY + ActZ) - (AntX - AntY - AntZ)) / DifTiempo;
                int limite = 1500;
                float MinMovimiento = 1E-1f;
                if (movimiento > MinMovimiento) {
                    /*if (tiempoActual- AntMovimiento >= limite) {
                        Toast.makeText(getApplicationContext(), "Hay movimiento de " + movimiento, Toast.LENGTH_SHORT).show();
                    }*/
                    AntMovimiento = tiempoActual ;
                }
                AntX = ActX;
                AntY = ActY;
                AntZ = ActZ;
                AntActualizacion = tiempoActual;
            }
        }
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
			Toast.makeText(getApplicationContext(), "Landscape Actived", Toast.LENGTH_SHORT).show();
		else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT)
			Toast.makeText(getApplicationContext(), "Portrait Actived", Toast.LENGTH_SHORT).show();
	}

	@Override
    protected void onResume(){
        super.onResume();
        SensorManager sm= (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors= sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size()>0){
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
            
        }
    }
    @Override
    protected void onStop() {
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE); 
        sm.unregisterListener(this);
        super.onStop();
    }
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
