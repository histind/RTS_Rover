/* LICENSE: You can do whatever you want with this, on four conditions.
 * 1) Share and share alike. This means source, too.
 * 2) Acknowledge attribution to spiritplumber@gmail.com in your code.
 * 3) Email me to tell me what you're doing with this code! I love to know people are doing cool stuff!
 * 4) You may NOT use this code in any sort of weapon.
 */

package re.serialout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.WindowManager;

public class Output extends Activity {

	static public final char cr = (char) 13; // because i don't want to type that in every time
	static public final char lf = (char) 10; // because i don't want to type that in every time
	public String datatosend = "";
	
	/*public boolean onCreateOptionsMenu(Menu menu) {
		// these show up in the primary screen: out of order for display reasons
		menu.add(0, 0, 0, "EXITING");
		return true;
	}
	public boolean onPrepareOptionsMenu(Menu menu) {
		android.os.Process.killProcess(android.os.Process.myPid());
		return true;
	}*/

	
	// copy and paste the function below to operate the audio serial stuff from another application; edit to suit
	public void example() {
		Intent serialout = new Intent();
		serialout.setClassName("re.serialout", "re.serialout.Output");
		serialout.putExtra("BAUD", "9600");
		serialout.putExtra("DATA","String_to_send");
		serialout.putExtra("CHD", "60");
		startActivity(serialout);
	}
	
	// or from commandline: $ am start -a android.intent.action.MAIN -n re.serialout/re.serialout.Output -e DATA String_to_send -e BAUD 4800
	// copy and paste the function above to operate the audio serial stuff from another application; edit to suit

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//String datatosend = "";
		//this.getWindow().
       // addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//example();
		AudioSerialOutMono.activate();
		//example();
		try{
			Bundle bundle = getIntent().getExtras();
			if (bundle.containsKey("BAUD"))
				AudioSerialOutMono.new_baudRate = Integer.parseInt(bundle.getString("BAUD"));
			if (bundle.containsKey("CHD"))
				AudioSerialOutMono.new_characterdelay = Integer.parseInt(bundle.getString("CHD"));
			if (bundle.containsKey("DATA"))
				datatosend = (bundle.getString("DATA"));
			AudioSerialOutMono.new_levelflip = true;
			AudioSerialOutMono.UpdateParameters(true);
			AudioSerialOutMono.output(cr+datatosend+cr);
			// (data+cr+lf)
			//example();
			while (AudioSerialOutMono.isPlaying())
			{
				SystemClock.sleep(50);
			}
			//example();
		}catch(Exception e){e.printStackTrace();}
		
		
		
		
		//android.os.Process.killProcess(android.os.Process.myPid()); // NUKE
		super.onCreate(savedInstanceState);
		this.finish();
		//example();
	}
}

