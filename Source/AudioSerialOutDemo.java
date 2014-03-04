/* LICENSE: You can do whatever you want with this, on four conditions.
 * 1) Share and share alike. This means source, too.
 * 2) Acknowledge attribution to spiritplumber@gmail.com in your code.
 * 3) Email me to tell me what you're doing with this code! I love to know people are doing cool stuff!
 * 4) You may NOT use this code in any sort of weapon.
 */

package re.serialout;

import java.io.*;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AudioSerialOutDemo extends Activity {

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

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		AudioSerialOutMono.activate();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		final EditText editbox = (EditText) findViewById(R.id.EditText01);
		final EditText baudbox = (EditText) findViewById(R.id.EditText02);
		final EditText charbox = (EditText) findViewById(R.id.EditText03);
		final Button savebutton = (Button) findViewById(R.id.Button01 );
		final CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox1);
		// Open CMD file
		String line = "";
		//File file = getBaseContext().getFileStreamPath("audioSerial_CMD.txt");
		//if(file.exists()){
			// do something? read last command?
	//	}
		//else {
			//try {
				//FileOutputStream cmdFileWrite = openFileOutput("audioSerial_CMD.txt", MODE_WORLD_WRITEABLE);
			//}catch(FileNotFoundException ex) {
				//ex.printStackTrace();
			//}
		//}
		// Get string from command file
		/*try {
			FileInputStream fis = openFileInput("audioSerial_CMD.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			line = br.readLine();
			String str = "";
			do {
				if(line != null){
					if(!(str.equals(line))){
						editbox.setText(line);
						baudbox.setText("4800");
						charbox.setText("002");
						// Output serial through audio
						try{
							AudioSerialOutMono.new_baudRate = Integer.parseInt(baudbox.getText().toString());
						}catch(Exception e){
							AudioSerialOutMono.new_baudRate = 4800;
							baudbox.setText("4800");
						}
						try{
							AudioSerialOutMono.new_characterdelay = Integer.parseInt(charbox.getText().toString());
						}catch(Exception e){
							AudioSerialOutMono.new_characterdelay = 2;
							charbox.setText("002");
						}
						
						AudioSerialOutMono.new_levelflip = checkbox.isChecked();
						AudioSerialOutMono.UpdateParameters(true);
						AudioSerialOutMono.output(cr+editbox.getText().toString()+cr);
					}
				}
				str = new String(line);
				br.close();
				br = new BufferedReader(new InputStreamReader(fis));
				line = br.readLine();
				if(line.equals("abort")) {
					break;
				}
				
			} while(line != null);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		editbox.setText(line);
		baudbox.setText("9600");
		charbox.setText("060");
		checkbox.setChecked(true);
		// Output serial through audio
		/*try{
			AudioSerialOutMono.new_baudRate = Integer.parseInt(baudbox.getText().toString());
		}catch(Exception e){
			AudioSerialOutMono.new_baudRate = 4800;
			baudbox.setText("9600");
		}
		try{
			AudioSerialOutMono.new_characterdelay = Integer.parseInt(charbox.getText().toString());
		}catch(Exception e){
			AudioSerialOutMono.new_characterdelay = 2;
			charbox.setText("060");
		}*/
		
		AudioSerialOutMono.new_levelflip = checkbox.isChecked();
		AudioSerialOutMono.UpdateParameters(true);
		/*Thread readScript = new Thread(new Runnable() {
		    public void run() {
		    	
		    	boolean test = false;
		    	FileInputStream fis = null;
				try {
					fis = openFileInput("audioSerial_CMD.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		    	
		    	//try {
				while(true) {
					FileInputStream fis = null;
					try {
						fis = openFileInput("audioSerial_CMD.txt");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(fis != null) {
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					}
					FileInputStream fis = null;
					BufferedReader br = null;
				try {
					String line = "ABCDEFG";
					char[] chArry = new char[10];
					boolean opened = false;
					while(true) {
						
						try {
							if(fis == null && test) {
							fis = openFileInput("audioSerial_CMD.txt");
							//opened = true;
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//br.close();
						if ((fis != null) && (br == null) && (!opened)) {
						//br = new BufferedReader(new InputStreamReader(fis));
						opened = true;
						//opened = true;
						//fis.close();
						}
						
						if(br != null) {
							if(br.ready()){
								br.read(chArry, 0, 10);
								line = new String(chArry);
								//br.close();
							}
						}
						//editbox.setText(line);
						//baudbox.setText("9600");
						//charbox.setText("060");
						//checkbox.setChecked(true);
						AudioSerialOutMono.new_baudRate = 9600;
						AudioSerialOutMono.new_characterdelay = 60;
						AudioSerialOutMono.new_levelflip = true;
						AudioSerialOutMono.UpdateParameters(true);
						AudioSerialOutMono.output(cr + line + cr);
						//br = new BufferedReader(new InputStreamReader(fis));
						//if(line != null) {
						
						//}
						Thread.sleep(100);
						//if(fis != null) {
						//	fis.close();
						//}
					}
					//br.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if(br != null) {
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//this.run();
						}
					}
				}
		    }
		    //} catch(InterruptedException e2) {
		    	//e2.printStackTrace();
		    //}
		    }
		  });
		readScript.setPriority(Thread.MAX_PRIORITY);
		readScript.start();
		*/
		  
		//AudioSerialOutMono.output(cr+editbox.getText().toString()+cr);
		savebutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				try{
					AudioSerialOutMono.new_baudRate = Integer.parseInt(baudbox.getText().toString());
				}catch(Exception e){
					AudioSerialOutMono.new_baudRate = 9600;
					baudbox.setText("9600");
				}
				try{
					AudioSerialOutMono.new_characterdelay = Integer.parseInt(charbox.getText().toString());
				}catch(Exception e){
					AudioSerialOutMono.new_characterdelay = 60;
					charbox.setText("060");
				}
				
				AudioSerialOutMono.new_levelflip = checkbox.isChecked();
				AudioSerialOutMono.UpdateParameters(true);
				AudioSerialOutMono.output(cr+editbox.getText().toString()+cr);
			}
		});
		//example();
		
	}
	
	/*public void onStart(Bundle savedInstanceState){
		AudioSerialOutMono.activate();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		final EditText editbox = (EditText) findViewById(R.id.EditText01);
		final EditText baudbox = (EditText) findViewById(R.id.EditText02);
		final EditText charbox = (EditText) findViewById(R.id.EditText03);
		final Button savebutton = (Button) findViewById(R.id.Button01 );
		final CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox1);
		try {
			FileInputStream fis = openFileInput("audioSerial_CMD.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = br.readLine();
			String str = "";
			while (line != null){
					if(!(str.equals(line))){
						editbox.setText(line);
						baudbox.setText("4800");
						charbox.setText("002");
						// Output serial through audio
						try{
							AudioSerialOutMono.new_baudRate = Integer.parseInt(baudbox.getText().toString());
						}catch(Exception e){
							AudioSerialOutMono.new_baudRate = 4800;
							baudbox.setText("4800");
						}
						try{
							AudioSerialOutMono.new_characterdelay = Integer.parseInt(charbox.getText().toString());
						}catch(Exception e){
							AudioSerialOutMono.new_characterdelay = 2;
							charbox.setText("002");
						}
						
						AudioSerialOutMono.new_levelflip = checkbox.isChecked();
						AudioSerialOutMono.UpdateParameters(true);
						AudioSerialOutMono.output(cr+editbox.getText().toString()+cr);
				}
				str = line;
				br.close();
				br = new BufferedReader(new InputStreamReader(fis));
				line = br.readLine();
				
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	}

	



