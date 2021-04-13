package root.radium.bookdrop.SupportingClass;



public class BorrowDetails {

    String GBookID;
    String borrowBookThumbnail ;
    Long TimeStamp;



    Long returnTime;

    public BorrowDetails() {

    }

    public BorrowDetails(String GBookID, Long timeStamp , String borrowBookThumbnail, Long returnTime) {
        this.GBookID = GBookID;
        this.TimeStamp = timeStamp;
        this.borrowBookThumbnail = borrowBookThumbnail;
        this.returnTime = returnTime;
    }

    public String getGBookID() {
        return GBookID;
    }

    public void setGBookID(String GBookID) {
        this.GBookID = GBookID;
    }

    public Long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        TimeStamp = timeStamp;
    }
    public String getBorrowBookThumbnail() {
        return borrowBookThumbnail;
    }


    public void setBorrowBookThumbnail(String borrowBookThumbnail) {
        this.borrowBookThumbnail = borrowBookThumbnail;
    }
    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }


}
