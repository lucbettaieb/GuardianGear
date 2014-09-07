package AzureDeployment;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author  Luc Bettaieb and Michael Harkins
 * @email   luc.bettaieb@gmail.com
 * @version 0.1
 * 
 * Program that runs continuously and checks sensor data using probabilistic 
 * algorithms to determine if a crash has occurred.
 */
public class SensorProcessing {
    
   
    public static void main(String[] kittens) throws InterruptedException{
        final Firebase dataStore = new Firebase("https://guardiangear.firebaseio.com/message");
        
        while (true) {
          dataStore.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Map<String, Map<String, Double>> data = (HashMap<String, Map<String, Double>>) snapshot.getValue();
				
				List<Double> x = new LinkedList<Double>();
				List<Double> y = new LinkedList<Double>();
				List<Double> z = new LinkedList<Double>();
				List<Double> hr = new LinkedList<Double>();
				for(Map.Entry<String, Map<String, Double>> entry : data.entrySet()) {
                                    
					for (Map.Entry<String, Double> entry2 : entry.getValue().entrySet()) {
                                          
					if(entry2.getKey().equalsIgnoreCase("AccelX")) {
						x.add(entry2.getValue());
					}
					else if(entry2.getKey().equalsIgnoreCase("AccelY")) {
						y.add(entry2.getValue());
					}
					else if(entry2.getKey().equalsIgnoreCase("AccelZ")) {
						z.add(entry2.getValue());
					}
					else if(entry2.getKey().equalsIgnoreCase("Heartrate")) {
						hr.add(entry2.getValue());
					}
				
					}
                                        
                                        System.out.println("X: "+x.toString());
                                        System.out.println("Y: "+y.toString());
                                        System.out.println("Z: "+z.toString());
                                        System.out.println("HR: "+hr.toString());
                                        
                                        Accelerometer accelX = new Accelerometer(x);
                                        Accelerometer accelY = new Accelerometer(y);
                                        Accelerometer accelZ = new Accelerometer(z);
                                        HeartRate heartrate = new HeartRate(hr);
                                        
                                        
                                        
                                        System.out.println("BelCrash accd X: " + accelX.belImpact());
                                        System.out.println("BelCrash accd Y: " + accelY.belImpact());
                                        System.out.println("BelCrash accd Z: " + accelZ.belImpact());
                                        System.out.println("BelCrash accd HR: " + heartrate.belImpact());
                                        
                                        //Combining sensor data
                                        
                                        //Test to see which of the acceleration values are the largest
                                        double bigXY = Math.max(accelX.belImpact(), accelY.belImpact());
                                        double bigXYZ = Math.max(bigXY, accelZ.belImpact());
                                         
                                        //Big XYZ now has the most importance
                                        //Now we must compare the most relevant axis of accelerometer data to the heart rate data
                                        System.out.println(bigXY);
                                        System.out.println(bigXYZ);
                                        if(bigXYZ >= .6 && heartrate.belImpact() >=.6){
                                            if(((bigXYZ - .10 <= heartrate.belImpact()) && (bigXYZ + .10 >= heartrate.belImpact()))
                                                    ||
                                            (((heartrate.belImpact() - .10) <= bigXYZ) && ((heartrate.belImpact() + .10) >= bigXYZ))){
                                                //Crash has occured
                                                //Send crash signal
                                                System.out.println("There has been a potential incident.  Calling emergency contact.");
                                                dataStore.getParent().child("emergency").setValue("true");
                                            
                                            }
                                            else {
                                                dataStore.getParent().child("emergency").setValue("false");
                                            }
                                        }
                                        else {
                                            dataStore.getParent().child("emrgency").setValue("false");
                                        }
                                        
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(SensorProcessing.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        
				}
			}

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
  
		Thread.sleep(1000);
	}
	}
}