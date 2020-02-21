import synthesizer.GuitarString;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final int keyNumber = 37;
    
    
    public static void main(String[] args) {
        /* create guitar strings, from 110Hz to 880Hz */
        GuitarString[] stringArray = new GuitarString[keyNumber];
        for (int i = 0; i < keyNumber; i += 1) {
            stringArray[i] = new GuitarString(440 * Math.pow(2, (i - 24.0) / 12));
        }
        
        while (true) {
            
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyIndex = keyboard.indexOf(key);
                if (keyIndex != -1) {
                    stringArray[keyIndex].pluck();
                }
            }
            
            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < keyNumber; i += 1) {
                sample += stringArray[i].sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);
            
            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < keyNumber; i += 1) {
                stringArray[i].tic();
            }
        }
    }
}
