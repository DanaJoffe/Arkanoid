package tests;
import java.io.*;

/**
 * Created by djoff on 20/06/2017.
 */
public class IOtest {
    public static void main(String[] args) {
        BufferedReader is = null ;
        try {
            is = new BufferedReader ( // wrapper that reads ahead
                    new InputStreamReader ( System .in) );

            String line ;
            while (( line = is . readLine () ) != null ) { // ’null ’->no more data in the stream
                System . out . print ( line ) ;
                }
            } catch ( IOException e ) {
            System . out . println (" Something went wrong with user input !");
            }

        // No need to close System .in

    }
}
