package AzureDeployment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author  Luc Bettaieb and Michael Harkins
 * @email   luc.bettaieb@gmail.com
 * @version 0.1
 * 
 * Uses a Bayseian probability algorithm to determine the probability
 * of a car crash based on heart rate spikes
 */
public class HeartRate {
    //Experimentally derived probability constant 
    //(Amount of time the sensor is correct)
    public static final double PROB_HRM_CRASH = .7;
    
    //slope that the HRM deems dangerous, needs to be tweaked probs
    private static final double DANGER_ZONE = 1; 
    
    private final List<Double> heartrates;

    
    public HeartRate(final List<Double> heartrates) {
        this.heartrates = new LinkedList(heartrates);
    }
    
    public double belImpact(){
        return(PROB_HRM_CRASH * probCrash()) / probHRM();
    }

    private double probCrash() {
        double probCrash = 0;
        double hrAvg = 0;
        
        for (Double heartrate : heartrates) {
            hrAvg += heartrate;
        }
        hrAvg /= heartrates.size();
        
        //Estimates the slope of the general dataset by taking the min
        //point and finding a line with the average point.
        double slopeHR = (Collections.min(heartrates) - hrAvg)/(heartrates.size());
        
        if(slopeHR < 0)
            slopeHR *= -1;
        
        //--------Deterministic Probability Calculations--------
        
        //Checks if the slope estimation is in the danger zone
        if(slopeHR >= DANGER_ZONE){
           probCrash += .1;
           
           if (slopeHR >= DANGER_ZONE + 0.2){
               probCrash += 1 - (DANGER_ZONE / slopeHR) + .2;
           }
        }
        
        //Compute the difference between max and min HR to see 
        //the intensity of change within the sample set.
        double diff = Collections.max(heartrates) - Collections.min(heartrates);
        if(diff >= 70){
            probCrash += .3;
            
            if(diff >= 100)
                return 1;
        }
        
        if (probCrash > 1)
            probCrash = 1;

        return probCrash;
    }

    private double probHRM() {
        return PROB_HRM_CRASH * probCrash() + (1-PROB_HRM_CRASH)*(1-probCrash());
    }
}