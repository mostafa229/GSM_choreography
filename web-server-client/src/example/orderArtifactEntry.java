package example;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import dao.OrderArtifact;
import dao.Stage;
import dao.serviceObjects.Order;
import dao.serviceObjects.TransferOption;
import ruleMgn.RuleManager;
import myPackage3.IService1;

/**
 * Created by Mostafa on 1/16/2016.
 */
@WebService()
public class orderArtifactEntry {
  private OrderArtifact orderArtifact;
  private RuleManager ruleManager;
  @WebMethod
  public String sayHelloWorldFrom(Integer from) {
    String result = "Hello, world, from " + from;
    System.out.println(result);
    QName qName=new QName("http://tempuri.org/","BasicHttpBinding_IService1");
    myPackage3.IService1 service = new myPackage3.Service1().getPort(qName, IService1.class);
    //invoke business method
    result= service.getData(from);
    System.out.println("result after call"+result);
    Integer p2=Integer.parseInt(result.split("\\s")[2]);
    RuleManager RuleManager=new RuleManager();
    RuleManager.init();
    //RuleManager.mockFeed();
    System.out.println("rule called with "+from+" and "+p2);
    //RuleManager.addValueFromService(from,p2*10);
    RuleManager.dispose();

    return result;
  }
  @WebMethod
  public String createOrder(Order order){
    if(ruleManager==null){
      ruleManager=new RuleManager();
      ruleManager.init();
      System.out.println("rule initiated");
    }
    ruleManager.createOrder(order);
    return "ok";
  }
  @WebMethod
  public String updateStatus(String status){
    if(ruleManager!=null){
      if(ruleManager.orderArtifact.isOpen("buyAndTransfer")){
        ruleManager.setStatus(status);
        return  "ok";
      }
      else {
        return "stage is not open";
      }
    }
    else
      return "no process created";
  }
  @WebMethod
  public String setOptions(TransferOption[] tos){

    return  "ok";
  }

  public static void main(String[] argv) {
    Object implementor = new orderArtifactEntry();
    String address = "http://localhost:9000/orderArtifactEntry";
    Endpoint.publish(address, implementor);
  }
}
