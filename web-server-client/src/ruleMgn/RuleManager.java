package ruleMgn;

import dao.OrderArtifact;
import dao.serviceObjects.Order;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;

import java.util.Scanner;

/**
 * Created by hp-user on 7/8/2015.
 */
//@Component
public class RuleManager {
    private KnowledgeBuilder kbuilder;
    private WorkingMemoryEntryPoint entryPointStoreOne;
    private WorkingMemoryEntryPoint entryPointStoreTwo;
    private StatefulKnowledgeSession knowledgeSession;

    /*public static void main(String[] args) {
        RuleManager RuleManager = new RuleManager();
        RuleManager.init();
        RuleManager.mockFeed();
        RuleManager.dispose();
//    RuleManager.feed();
    }*/

    public void dispose() {
        knowledgeSession.dispose();
    }

    public void init() {
        System.out.println("init called!!!!!!!!!!!!!!!");
        kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("sales.drl"), ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            System.err.println(kbuilder.getErrors().toString());
        }
        KnowledgeBaseConfiguration configuration = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        configuration.setOption(EventProcessingOption.STREAM);
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(configuration);
        knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        knowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
        entryPointStoreOne = knowledgeSession.getWorkingMemoryEntryPoint("StoreOne");
        entryPointStoreTwo = knowledgeSession.getWorkingMemoryEntryPoint("StoreTwo");
    }
    public OrderArtifact  orderArtifact;
    public void createOrder(Order order){
        orderArtifact=new OrderArtifact();
        orderArtifact.buyer=order.buyer;
        orderArtifact.seller=order.seller;
        orderArtifact.count=order.count;
        orderArtifact.deliveryDate=order.deliveryDate;
        orderArtifact.itemPrice=order.itemPrice;
        orderArtifact.totalPrice=order.totalPrice;
        orderArtifact.type=order.type;
        this.insertFact("StoreOne",orderArtifact);
        //System.out.println("order created");
    }
    public void setStatus(String status){
        orderArtifact.lastStatusRecieved=status;
        orderArtifact=new OrderArtifact(orderArtifact);
        //knowledgeSession.fireAllRules();
        this.insertFact("StoreOne",orderArtifact);
    }

    /*public void mockFeed() {
        Sale sale = new Sale("banana", 10, 90);
        this.insertFact("StoreOne", sale);
        System.out.println("first one inserted!");
        System.out.println("waiting");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Sale sale1 = new Sale("banana", 10, 90);
        this.insertFact("StoreOne", sale1);
        System.out.println("second one inserted!");
    }*/

    public void insertFact(String entry, OrderArtifact orderArtifact) {
        WorkingMemoryEntryPoint currentEntry = null;

        try {
            currentEntry = (WorkingMemoryEntryPoint) this.getClass().getDeclaredField("entryPoint" + entry).get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //System.out.println("come to insert fact");
        knowledgeSession.insert(orderArtifact);
        knowledgeSession.fireAllRules();
        //System.out.println("rule fired");
    }

    /*private void feed() {
        String out = "";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("entry point ?");
            String entry = scanner.next();
            WorkingMemoryEntryPoint currentEntry = null;

            try {
                currentEntry = (WorkingMemoryEntryPoint) this.getClass().getDeclaredField("entryPoint" + entry).get(this);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Sale sale = getSaleInput();

            currentEntry.insert(sale);
            knowledgeSession.fireAllRules();
            System.out.println("type 'exit' to exit!");
            out = scanner.next();
        }
        while (!out.equals("exit"));
        knowledgeSession.dispose();
    }*/

   /* private Sale getSaleInput() {

        Scanner in = new Scanner(System.in);
        System.out.println("enter article name:");
        String name = in.next();
        System.out.println("enter amount:");
        long amount = in.nextLong();
        System.out.println("enter quantity:");
        int quantity = in.nextInt();

        System.out.println("*******************************************");
        return new Sale(name, amount, quantity);

    }*/

/*    private void insertEvent(WorkingMemoryEntryPoint entryPoint, Sale sale) {
//        sale.setArticle(article);
//        sale.setAmount(amount);
//        sale.setQuantity(quantity);
        entryPoint.insert(sale);
    }

    private void insertEvent(WorkingMemoryEntryPoint entryPoint, Sale sale, String article, long amount, int quantity) {
        sale.setArticle(article);
        sale.setAmount(amount);
        sale.setQuantity(quantity);
        entryPoint.insert(sale);
    }*/

    private void destroy() {
        System.out.println("destroy called!!!!!!!");
        knowledgeSession.dispose();
    }
}
