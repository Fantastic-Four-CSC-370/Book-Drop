package root.radium.bookdrop.SupportingClass;

public class student {
    String name, mobileNo, id, department, img, role;

    public student() {

    }

    public student(String name, String mobileNo, String id, String department, String img) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.id = id;
        this.department = department;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public String getImg() {
        return img;
    }

}
