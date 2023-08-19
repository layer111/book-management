package com.example.bookmanagement.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.*;

//ám chỉ đây là 1 bảng
@Entity
public class Book {
	//thuoc tinh id tự động tăng 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
// colum là ám chỉ đây là cột 
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//đặt cái định dạng cho date 
    private Date publishedDate;

    @Column(nullable = false)
    private Double price;

    private Integer numberOfPages;

    private String photos;

    @OneToMany(mappedBy="book")
    private List<Review> reviews;

    public Book() {

    }

    public Book(String title, String author, String description, Date publishedDate, Integer numberOfPages, BookCategory category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedDate = publishedDate;
        this.numberOfPages = numberOfPages;
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    //add khoa ngoại nè
    @ManyToOne
    @JoinColumn(name = "category_id")
    private BookCategory category;

    public List<Review> getReviews() {
        return reviews;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return photos;
    }

    public String getPhotoPath() {
        if (photos == null || id == null) return null;

        return "/book-photos/" + id + "/" + photos;
    }

    public Map<String, Double> getPercentByStar() {
        Map<String, Double> cntMap = new HashMap<>();
        cntMap.put("1", 0.00);
        cntMap.put("2", 0.00);
        cntMap.put("3", 0.00);
        cntMap.put("4", 0.00);
        cntMap.put("5", 0.00);
        if(reviews.size() == 0) return cntMap;
        for(Review review: reviews) {
            cntMap.put(review.getStar() + "", cntMap.get(review.getStar() + "") + 1);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        cntMap.replaceAll((s, v) -> Double.valueOf(df.format(cntMap.get(s) * 100 / reviews.size())));
        return cntMap;
    }

    public Integer getAvgRating() {
        double sum = 0;
        for(Review review: reviews) {
            sum += review.getStar();
        }
        if(sum == 0) return 0;
        return (int) sum / reviews.size();
    }

}
