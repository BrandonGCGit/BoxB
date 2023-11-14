package ucr.ac.cr.boxb.model;

public class Client {

    private String idClient, name, documentID;

    public Client() {
    }

    public Client(String name, String documentID) {
        this.name = name;
        this.documentID = documentID;
    }

    public Client(String idClient, String name, String documentID) {
        this.idClient = idClient;
        this.name = name;
        this.documentID = documentID;
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

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public String toString() {
        return "Client: " +
                "Id= " + idClient +
                "Name= " + name +
                "DocumentID= " + documentID;
    }
}
