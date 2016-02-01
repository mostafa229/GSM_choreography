package dao;

import dao.serviceObjects.Order;
import dao.serviceObjects.TransferOption;
import mockPackage.IService1;
import mockPackage.Service1;


import javax.xml.namespace.QName;
import java.util.Date;

public class OrderArtifact extends GSM_informationModel {
	public String type;
	public int count;
	public double itemPrice;
	public double totalPrice;
	public Date deliveryDate;
	public Company buyer;
	public Company seller;
	public String lastStatusRecieved="";
	public TransferOption transferOption;
	public  boolean getAndCheckedConfirmed;
	public boolean getAndCheckedRejected;
	public boolean paymentDone;
	public boolean purchaseDoneAndStored;
	public boolean purchaseDone;
	public boolean storedDone;
	public boolean transferTypeSelected;
	public boolean optionsGetFromCRM;
	public boolean optionsDeliverd;
	public boolean operationFinished;
	public OrderArtifact(OrderArtifact oa){
		this.type=oa.type;
		this.lastStatusRecieved=oa.lastStatusRecieved;
		this.itemPrice=oa.itemPrice;
		this.totalPrice=oa.totalPrice;
		this.stages=oa.stages;
		this.transferOption=oa.transferOption;
		this.deliveryDate=oa.deliveryDate;
		this.buyer=oa.buyer;
		this.seller=oa.seller;
		this.count=oa.count;
		this.operationFinished=oa.operationFinished;
		this.getAndCheckedConfirmed=oa.getAndCheckedConfirmed;
		this.getAndCheckedRejected=oa.getAndCheckedRejected;
		this.paymentDone=oa.paymentDone;
		this.purchaseDone=oa.purchaseDone;
		this.purchaseDoneAndStored=oa.purchaseDoneAndStored;
		this.storedDone=oa.storedDone;
		this.transferTypeSelected=oa.transferTypeSelected;
		this.optionsGetFromCRM=oa.optionsGetFromCRM;
		this.optionsDeliverd=oa.optionsDeliverd;

	}
	public OrderArtifact() {
		Stage initStage=new Stage(null,"getAndCheck",true);
		this.initStage=initStage;
		this.stages=new Stage[9];
		this.stages[0]=initStage;
		this.stages[1]=new Stage(initStage,"payment",false);
		this.stages[2]=new Stage(initStage,"buyAndTransfer",false);
		this.stages[3]=new Stage(this.stages[2],"buying",false);
		this.stages[4]=new Stage(this.stages[2],"shipping",false);

		this.stages[5]=new Stage(initStage,"transferType",false);
		this.stages[6]=new Stage(this.stages[5],"gettingOption",false);
		this.stages[7]=new Stage(this.stages[5],"settingOption",false);

		this.stages[8]=new Stage(initStage,"transferToIran",false);

	}
	public boolean isOpen(String stageName){
		for (int i = 0; i < stages.length; i++) {
			Stage stage = stages[i];
			if(stage.name.equals(stageName))
					return stage.status;
		}
		return false;
	}
	public void openStage(String stageName){
		for (int i = 0; i < stages.length; i++) {
			Stage stage = stages[i];
			if(stage.name.equals(stageName))
				stage.status=true;
		}
	}
	public void closeStage(String stageName){
		for (int i = 0; i < stages.length; i++) {
			Stage stage = stages[i];
			if(stage.name.equals(stageName))
				stage.status=false;
		}
	}
	public boolean isLastStatus(String s){
		return lastStatusRecieved.equals(s);
	}
	public Order getOrder(){
		Order order=new Order();
		order.buyer=this.buyer;
		order.seller=this.seller;
		order.count=this.count;
		order.deliveryDate=this.deliveryDate;
		order.itemPrice=this.itemPrice;
		order.totalPrice=this.totalPrice;
		order.type=this.type;
		return order;
	}
	public boolean connectToForeignStore(){
		try{
			String ret="";
			QName qName=new QName("http://tempuri.org/","BasicHttpBinding_IService1");
			IService1 service = new Service1().getPort(qName, IService1.class);
			ret= service.commitOrder();
			if(ret.equals("accepted")){
				System.out.println("order accepted");
				this.getAndCheckedConfirmed=true;
				this.stages[0].status=false;
				this.stages[1].status=true;
			}
			else
				this.getAndCheckedRejected=true;
			return ret.equals("accepted");
		}
		catch (Exception e){
			return false;
		}
	}
	public boolean getTransferOptionFromCrm(){
		try{
			QName qName=new QName("http://tempuri.org/","BasicHttpBinding_IService1");
			IService1 service = new Service1().getPort(qName, IService1.class);
			//invoke business method
			if(service.getTransferOption().equals("mock options")){
				this.transferOption=new TransferOption();
				this.optionsGetFromCRM=true;
				return  true;
			}
			else
				System.out.println("kkkkkkkkkkkkkkkkkkkk");
			return  false;

		}
		catch (Exception e){
				return  false;
		}
	}
	public boolean setTransferOption(){
		try{
			QName qName=new QName("http://tempuri.org/","BasicHttpBinding_IService1");
			IService1 service = new Service1().getPort(qName, IService1.class);
			//invoke business method
			service.setTransferOption();
			{
				this.transferOption=new TransferOption();
				this.optionsDeliverd=true;
			}
			return  true;
		}
		catch (Exception e){
			return  false;
		}
	}
}