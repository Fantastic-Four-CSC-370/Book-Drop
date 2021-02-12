package root.radium.bookdrop.SupportingClass;

public class Book {
    private String Title,Authors,PublishedDate,BookDescription,Categories,Thumbnail,Preview,Url;
    private int pageCount;
    public Book(){

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

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Book(String Title, String Authors, String PublishedDate, String BookDescription, String Categories,
                String Thumbnail, String Preview , int pageCount , String Url) {
        this.Title = Title;
        this.Authors = Authors;
        this.PublishedDate = PublishedDate;
        this.BookDescription = BookDescription;
        this.Categories = Categories;
        this.Thumbnail = Thumbnail;
        this.Preview = Preview;
        this.pageCount = pageCount;
        this.Url = Url;
    }
}
