package root.radium.bookdrop.SupportingClass;



public class BorrowDetails {

    String GBookID;



    String borrowBookThumbnail ;
    Long TimeStamp ;

    public BorrowDetails() {

    }

    public BorrowDetails(String GBookID, Long timeStamp , String borrowBookThumbnail) {
        this.GBookID = GBookID;
        this.TimeStamp = timeStamp;
        this.borrowBookThumbnail = borrowBookThumbnail;
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


}
