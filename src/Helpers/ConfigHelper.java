package Helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {

    private int Port;
    private String RMIName;

    public ConfigHelper(){
        Properties prop = new java.util.Properties();
        InputStream input = null;

        try{
            input = getClass().getResourceAsStream("/Helpers/config.properties");

            if(input == null){
                System.out.println("File could not be located!");
                return;
            }

            prop.load(input);

            this.Port= Integer.parseInt(prop.getProperty("Port"));
            this.RMIName=prop.getProperty("RMIName");

        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            if (input != null){
                try {
                    input.close();
                } catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    public int getPort() { return this.Port; }
    public String getRMIName() { return this.RMIName; }


}
