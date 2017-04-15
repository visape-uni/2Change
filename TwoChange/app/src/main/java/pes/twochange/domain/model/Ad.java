package pes.twochange.domain.model;

import android.net.Uri;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kredes on 07/04/2017.
 */

public class Ad extends Model {
    private static final int MAX_IMAGES = 4;
    private static final String OUT_OF_BOUNDS_MESSAGE = "Image index must be between 0 and " + MAX_IMAGES;

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public enum ProductState {
        NEW, ALMOST_NEW, GOOD, BAD, BROKEN
    }

    private Profile user;

    private String title;
    private String description;

    private int year;
    private ProductState state;
    private BigDecimal price;
    private int rating;
    private String status;

    private List<Uri> imageUris;
    private List<String> imageIds;

    private List<String> wants;


    /*
         --------------
        | CONSTRUCTORS |
         --------------
     */
    public Ad() {
        rating = 100;
        imageUris = new ArrayList<>(MAX_IMAGES);
        wants = new ArrayList<>();
        imageIds = new ArrayList<>(MAX_IMAGES);

        for (int i = 0; i < MAX_IMAGES; ++i) {
            imageIds.add(i, null);
            imageUris.add(i, null);
        }
    }

    public Ad(Profile user, String title, String description) {
        this();
        this.user = user;
        this.title = title;
        this.description = description;
    }

    /*
         ---------------------
        | GETTERS AND SETTERS |
         ---------------------
     */
    public Profile getUser() {
        return user;
    }
    public void setUser(Profile user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Uri> getImageUris() {
        return imageUris;
    }
    public void setImageAt(int index, Uri image) {
        if (index < 0 || index > MAX_IMAGES - 1) throw new IndexOutOfBoundsException(OUT_OF_BOUNDS_MESSAGE);
        imageUris.add(index, image);
    }

    public BigDecimal getPrice() { return price; };
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    /*
         ----------------------
        | OTHER PUBLIC METHODS |
         ----------------------
     */
    public static int rate(Ad ad) {
        return 0;
    }
}
