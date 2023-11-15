package ucr.ac.cr.boxb.model;

public class Bill {

    private  String idClient, name, type, iva, date, amount;

    public Bill() {
    }

    public Bill(String idClient, String name, String type, String iva, String date, String amount) {
        this.idClient = idClient;
        this.name = name;
        this.type = type;
        this.iva = iva;
        this.date = date;
        this.amount = amount;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bill{");
        sb.append("idClient='").append(idClient).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", iva='").append(iva).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", amount='").append(amount).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

