package example;

import mockPackage.IService1;
import mockPackage.Service1;

import javax.xml.namespace.QName;

/**
 * Created by Mostafa on 1/23/2016.
 */
public class HelloWorldClient {
  public static void main(String[] argv) {
    QName qName=new QName("http://tempuri.org/","BasicHttpBinding_IService1");
    IService1 service = new Service1().getPort(qName, IService1.class);
    //invoke business method
    System.out.println(service.commitOrder());
    System.out.println(service.getTransferOption());
    System.out.println(service.setTransferOption());
  }
}
