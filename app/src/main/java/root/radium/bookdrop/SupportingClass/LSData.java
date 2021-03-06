package root.radium.bookdrop.SupportingClass;

public class LSData {
    String UID , Role ;
    public LSData() {

    }


    public LSData(String UID, String role) {
        this.UID = UID;
        Role = role;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
