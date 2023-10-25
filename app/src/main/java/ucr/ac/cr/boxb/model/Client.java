package ucr.ac.cr.boxb.model;

public class Client {

    private String name, documentID;

    public Client() {
    }

    public Client(String name, String documentID) {
        this.name = name;
        this.documentID = documentID;
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
                "Name='" + name +
                "DocumentID='" + documentID;
    }
}
