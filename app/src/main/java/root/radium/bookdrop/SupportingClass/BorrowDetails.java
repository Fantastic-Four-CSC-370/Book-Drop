package root.radium.bookdrop.SupportingClass;



public class BorrowDetails {

    String GBookID;
    String borrowBookThumbnail;
    String documentKey;
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
    public BorrowDetails(String documentKey) {
        this.documentKey = documentKey;
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

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }


}
