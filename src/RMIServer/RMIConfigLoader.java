package RMIServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class RMIConfigLoader{

  private String rmiName;
  private int rmiPort;
  private int mainUDP,secUDP;
  private int pingFrequency;
  private int retries;
  private String dbIP;
  private int dbPort;

  RMIConfigLoader(){
    Properties prop = new java.util.Properties();
    FileInputStream input = null;

    try{
      input = new FileInputStream("rmi.properties");

      prop.load(input);

      this.rmiName = prop.getProperty("rmiName");
      this.rmiPort = Integer.parseInt(prop.getProperty("rmiPort"));
      this.mainUDP = Integer.parseInt(prop.getProperty("mainUDP"));
      this.secUDP = Integer.parseInt(prop.getProperty("secUDP"));
      this.pingFrequency = Integer.parseInt(prop.getProperty("pingFrequency"));
      this.retries = Integer.parseInt(prop.getProperty("retries"));
      this.dbIP = prop.getProperty("dbIP");
      this.dbPort = Integer.parseInt(prop.getProperty("dbPort"));

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

  int getRMIPort() { return this.rmiPort; }

  String getRMIName() { return this.rmiName; }

  int getMainUDP() { return this.mainUDP; }

  int getSecUDP() { return this.secUDP; }

  int getPingFrequency() { return this.pingFrequency; }

  int getRetries() { return this.retries; }

  String getDBIP() { return this.dbIP; }

  int getDBPort() { return this.dbPort; }

}
