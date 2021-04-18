package root.radium.bookdrop.SupportingClass;

public class Users {
    String name, mobileNo, id, department, img, role ,UID;

    public Users() {

    }

    public Users(String name, String mobileNo, String id, String department, String img, String role) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.id = id;
        this.department = department;
        this.img = img;
        this.role = role;

    }
    public Users(String UID) {
       this.UID = UID;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getUID() { return UID; }

    public void setUID(String UID) { this.UID = UID; }

}
