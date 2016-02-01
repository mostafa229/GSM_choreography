package ruleMgn;

/**
 * Created by hp-user on 7/8/2015.
 */
public class Sale {
    private String article;
    private long amount;
    private int quantity;
    public Sale(){}

    public Sale(String article, long amount, int quantity) {
        this.article = article;
        this.amount = amount;
        this.quantity = quantity;

    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
