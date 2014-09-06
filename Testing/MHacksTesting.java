package mhacks.testing;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Luc Bettaieb
 */
public class MHacksTesting {

    public static final double PROB_ACCEL_CRASH = .7; //experimentally derived constant, based on sensor noise characteristics.  
                                                      //essentially the amount of time the sensor gives an accurate reading.
    public static final double SPD_DANGER = 45;       //set an arbitrary 'danger speed'
    public static final double SPD_VERY_DANGER = 100;
    
    public static void main(String[] args) {
        System.out.println("Impact: " + impact());
        System.out.println("Prob Crash " + probCrash());
        System.out.println("Prob Accel " + probAccel());
        System.out.println("accel " + getAccel());
    }
    
    private static double impact(){ //returns the amount of belief that there is a crash given the X accelerometer data only
        return (PROB_ACCEL_CRASH * probCrash()) / probAccel();
    }
    
    private static double probCrash(){ //function based on speed, time and other dangerous factors
        double probCrash = 0;
        double dx = 0.1;
        Calendar calendar = new GregorianCalendar();
        
        double[] accels = new double[10]; //get acceleration samples
        for(int i = 0; i < 10; i++){
            accels[i] = getAccel();
        }
        
        double spd = 0;                   //get speed sample
        for(int i = 0; i < 10; i++) {
            spd += accels[i]*dx;          //speed in mph (hopefully) //TODO: needs conver probably
        }
        System.out.println("SPD "+ spd);
        if(spd >= SPD_DANGER){
            probCrash += .1;
            
            if (spd >= probCrash + 10){
                probCrash += 1 - (SPD_DANGER/spd); //if the spd is very fast, increase by an appropriate factor 
            }  
            
            if (spd >= SPD_VERY_DANGER){
                probCrash = .99;
            }
        }
        
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        if (hr > 17 && hr < 20 ){  //Check if the time is around 5-8PM (Twilight-ish times, higher risk of accidents)
            probCrash +=.3;
        }
        
        if(probCrash > 1){
            probCrash = 1;
        }
        
        //TODO: Get weather information
        //TODO: Seizure checking
        //TODO: Distracted driving (cell phone use, if in a call or texting application open)
        
        
        
        return probCrash;
    }
    
    private static double probAccel(){
        return PROB_ACCEL_CRASH * probCrash() + (1-PROB_ACCEL_CRASH)*(1-probCrash());
    }
    
    
    //(9.8 m/s^2 = 21.9 mi/hr^2)
    private static double getAccel(){ 
        double dummy = Math.random()*5;
        //fake data function to test algorithm
        //this should be dynamically updating
        //System.out.println("dummy "+ dummy);
        return (21.9) * dummy;
        //g * __num g's *
}
}
