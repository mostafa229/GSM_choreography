package example;

import myPackage3.*;

import javax.xml.namespace.QName;

/**
 * Created by Mostafa on 1/18/2016.
 */
public class HelloWorldClient {
  public static void main(String[] argv) {
    QName qName=new QName("http://tempuri.org/","BasicHttpBinding_IService1");
    myPackage3.IService1 service = new myPackage3.Service1().getPort(qName,IService1.class);
    //invoke business method
    System.out.println( service.getData(12));
    //myPackage3.Service1 service = new Service1().getPort(qName, GetData.class);
    //invoke business method
    //System.out.println(service.("Tehran", "iran"));
  }
}
