package root.radium.bookdrop.SupportingClass;

import java.io.Serializable;

public class Book implements Serializable {

    private String ID, Title,Authors,PublishedDate,BookDescription,BookSubTitle,Thumbnail,Preview,ISBN, pageCount ;

    public Book(){

    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        PublishedDate = publishedDate;
    }

    public String getBookDescription() {
        return BookDescription;
    }

    public void setBookDescription(String bookDescription) {
        BookDescription = bookDescription;
    }

    public String getBookSubTitle() {
        return BookSubTitle;
    }

    public void setBookSubTitle(String bookSubTitle) {
        BookSubTitle =bookSubTitle;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getPreview() {
        return Preview;
    }

    public void setPreview(String preview) {
        Preview = preview;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public Book(String ID ,String Title, String Authors, String PublishedDate, String BookDescription, String BookSubTitle,
                String Thumbnail, String Preview , String pageCount , String ISBN) {
        this.Title = Title;
        this.Authors = Authors;
        this.PublishedDate = PublishedDate;
        this.BookDescription = BookDescription;
        this.BookSubTitle = BookSubTitle;
        this.Thumbnail = Thumbnail;
        this.Preview = Preview;
        this.pageCount = pageCount;
        this.ISBN = ISBN;
        this.ID = ID;

    }


}
