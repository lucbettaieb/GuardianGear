package AzureDeployment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author  Luc Bettaieb and Michael Harkins
 * @email   luc.bettaieb@gmail.com
 * @version 0.1
 * 
 * Uses a Bayesian probability algorithm to determine the probability
 * of a car crash based on accelerometer data as well as other factors
 * 
 * TODO: remove other factors from Accelerometer and put them in another
 * class to add to the overall probability function of their being
 * and accident
 * **Use the different classes along with varying degrees of confidence
 * for each measurement to determine the overall probability.
 * Different measurements have different ratios as they are not all equally
 * important and not created equal.
 */
public class Accelerometer {
    //Experimentally derived probability constant 
    //(Amount of time the sensor is correct)
    public static final double PROB_ACCEL_CRASH = .7;
    //Speed limits
    public static final double SPD_DANGER = 45;
    public static final double SPD_VERY_DANGER = 120;
    private final List<Double> accelerations;
    
    private Calendar cal = new GregorianCalendar();
    
    public double belImpact(){
        return(PROB_ACCEL_CRASH * probCrash()) / probAccel();
    }
    
    public Accelerometer(List<Double> accelerations) {
        this.accelerations = new LinkedList(accelerations);
    }
    private double probCrash(){
        
        /*
        * Probabalistic function derived from 'dangerous' conditions.
        * Driving speed(int a dt), jerk spike (da/dt), time of day (dusk)
        *
        * TODO: Weather, distracted driving (cell phone call / text app open)
        *       and seizure checking
        */
        
        double probCrash = 0;
        double dx = 0.1;
        int sampleSize = 10;
        
        
        //double[] accels = new double[sampleSize];
        //System.arraycopy(getAccel(), 0, accels, 0, sampleSize);
        
        
        double spd = 0;
        for (Double acceleration : accelerations) {
            spd += acceleration * dx;
        }
//        for(int i = 0; i < sampleSize; i++) {
//            spd += accels[i]*dx;
//        }

        
        //Calculating 'jerk' using max differential over a scaled sampling
        //double jrk = (accelerations.get(0) - accelerations.get(accelerations.size() - 1) / (accelerations.size() * 10));
        //double jrk2 = (accels[0] - accels[sampleSize-1])/(sampleSize*10);
        
        double avgJerk = 0;
        for(int i = 0; i < accelerations.size()-1; i++){
            avgJerk += (accelerations.get(i)-accelerations.get(i+1)) / 2;
        }
        
        avgJerk /= ((accelerations.size()-1)*10);
        
        if (avgJerk < 0)
            avgJerk *= -1;
        
        int hr = cal.get(Calendar.HOUR_OF_DAY);
        
        //--------Deterministic Probability Calculations--------
        
        //Probabilty change based on speed levels.  High speed yeilds a more dangerous crash
        if (spd >= SPD_DANGER){ 
            probCrash += 0.1;
            
            if (spd >= SPD_DANGER + 10){ //TODO: This may need to be adjusted for better resolution
                probCrash += 1 - (SPD_DANGER/spd) + .2;
            }
        }
        
        if(hr > 17 && hr < 20) //If time is around 5-8PM (Twilight)
            probCrash += .14; //twilight constant.  SEEMS LEGIT.
        
        if(avgJerk > 1.5 || probCrash > 1 || spd >= SPD_VERY_DANGER)
            return 1;
        
        return probCrash;
    }
    
    private double probAccel(){
        return PROB_ACCEL_CRASH * probCrash() + (1-PROB_ACCEL_CRASH)*(1-probCrash());
    }
    
}