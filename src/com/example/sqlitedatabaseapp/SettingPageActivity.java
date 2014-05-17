package com.example.sqlitedatabaseapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class SettingPageActivity extends Activity {
	private Switch mySwitch;
	private TextView switchStatus;
	private Spinner spinner1, spinner2,spinner;
	  private Button btnSubmit;
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
		    setContentView(R.layout.setting_activity_database);
	        // The activity is being created.
		    switchStatus = (TextView) findViewById(R.id.labelSpinner4);
		    mySwitch = (Switch) findViewById(R.id.togglebutton);
			   
			   //set the switch to ON 
			   mySwitch.setChecked(true);
			   //attach a listener to check for changes in state
			   mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			  
			    @Override
			    public void onCheckedChanged(CompoundButton buttonView,
			      boolean isChecked) {
			  
			     if(isChecked){
			      switchStatus.setText("Primary");
			     }else{
			      switchStatus.setText("Secondary");
			     }
			  
			    }
			   });
			    
			   //check the current state before we display the screen
			   if(mySwitch.isChecked()){
			    switchStatus.setText("Primary");
			   }
			   else {
			    switchStatus.setText("Secondary");
			   }
			   addListenerOnButton();
	    }
	   
	// get the selected dropdown list value
	   public void addListenerOnButton() {
	  
		spinner = (Spinner) findViewById(R.id.spinner);
	 	spinner1 = (Spinner) findViewById(R.id.spinner1);
	 	//spinner2 = (Spinner) findViewById(R.id.spinner2);
	 	btnSubmit = (Button) findViewById(R.id.btnSubmit);
	 	mySwitch = (Switch) findViewById(R.id.togglebutton);
	  
	 	btnSubmit.setOnClickListener(new OnClickListener() {
	  
	 	  @Override
	 	  public void onClick(View v) {
	  
		    	Intent intent = new Intent(SettingPageActivity.this,SearchResultsActivity.class);
		    	intent.setAction("Settings");
		    	intent.putExtra("spinner", String.valueOf(spinner.getSelectedItem()));
		    	intent.putExtra("spinner1", String.valueOf(spinner1.getSelectedItem()));
		    	intent.putExtra("switchStatus", String.valueOf(switchStatus.getText()));
		    	startActivity(intent);
	 	    Toast.makeText(SettingPageActivity.this,
	 		"OnClickListener : " + 
	                 "\nSpinner  : "+ String.valueOf(spinner.getSelectedItem()) + 
	                 "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
	                 "\nSwitchStatus : "+ String.valueOf(switchStatus.getText()) +
	                 "\nmySwitch : "+ String.valueOf(mySwitch.getText()),
	 			Toast.LENGTH_SHORT).show();
	    	//Intent intent = new Intent();
	 	    
	 	  }

	 	});
	   }
	   //ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
	   /*@Override
	   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	       Toast.makeText(this, "Monitored switch is " + (isChecked ? "on" : "off"),
	               Toast.LENGTH_SHORT).show();
	    }*/
	   
	  /* public void onToggleClicked(View view) {
		    // Is the toggle on?
		    boolean on = ((ToggleButton) view).isChecked();
		    
		    if (on) {
		        // Enable vibrate
		    	Toast.makeText(this, "On toggle presses", Toast.LENGTH_SHORT).show();
		    } else {
		        // Disable vibrate
		    	Toast.makeText(this, "Off toggle presses", Toast.LENGTH_SHORT).show();
		    }
		}*/
	   
	    public void onCheckedChanged(CompoundButton buttonView,    boolean isChecked) {
			  
			     if(isChecked){
			      switchStatus.setText("Primary");
			     }else{
			      switchStatus.setText("Secondary");
			     }
			  
			    }
	    @Override
	    protected void onStart() {
	        super.onStart();
	        // The activity is about to become visible.
	    }
	    @Override
	    protected void onResume() {
	        super.onResume();
	        // The activity has become visible (it is now "resumed").
	    }
	    @Override
	    protected void onPause() {
	        super.onPause();
	        // Another activity is taking focus (this activity is about to be "paused").
	    }
	    @Override
	    protected void onStop() {
	        super.onStop();
	        // The activity is no longer visible (it is now "stopped")
	    }
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        // The activity is about to be destroyed.
	    }
	}