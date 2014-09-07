package AzureDeployment;

import com.firebase.client.Firebase;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luc Bettaieb
 * 
 * Gonna push some JSON
 */
public class FakeDataSender {
    public static void main(String[] kittens) throws InterruptedException{
        System.out.println("Fake data, go!");
        Thread.sleep(1000);
        
        Firebase bravo = new Firebase("https://guardiangear.firebaseio.com/message");
        Map<String, Double> chunk = new HashMap<String, Double>();
        
        chunk.put("AccelX", 21.9 * 0.1);
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 70.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 0.5);
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 72.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 0.7);
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 71.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 1);
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 69.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 1.7);
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 66.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 1.4);
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 72.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 0);  
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 70.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 0);      //Constant
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 71.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 10);      //Crash
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 160.0);
        bravo.push().setValue(chunk);
        
        Thread.sleep(1000);
        
        chunk.put("AccelX", 21.9 * 20);      //Crash
        chunk.put("AccelY", 21.9 * 0.1);
        chunk.put("AccelZ", 21.9 * 0.1);
        chunk.put("Heartrate", 200.0);
        bravo.push().setValue(chunk);
        
     Thread.sleep(1000);   
    }
    
}
