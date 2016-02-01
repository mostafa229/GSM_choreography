package example;

import myPackage2.GlobalWeather;
import myPackage2.GlobalWeatherSoap;

import javax.xml.namespace.QName;

/**
 * Created by Mostafa on 1/17/2016.
 */
public class HelloWorldClient {

  public static void main(String[] argv) {
    QName qName=new QName("http://www.webserviceX.NET","GlobalWeatherSoap");
    myPackage2.GlobalWeatherSoap service = new GlobalWeather().getPort(qName, GlobalWeatherSoap.class);
    //invoke business method
    System.out.println(service.getWeather("Tehran", "iran"));
  }
}
