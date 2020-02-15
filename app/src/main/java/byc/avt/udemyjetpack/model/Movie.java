package byc.avt.udemyjetpack.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Movie implements Parcelable {
    @SerializedName("id")
    private String id_movie;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("backdrop_path")
    private String cover;
    @SerializedName("release_date")
    private String releaseDate;

    @PrimaryKey(autoGenerate = true)
    public int uId;

    public Movie() {

    }

    public Movie(String id_movie, String title, String popularity, String description, String poster, String cover, String releaseDate) {
        this.id_movie = id_movie;
        this.title = title;
        this.popularity = popularity;
        this.description = description;
        this.poster = poster;
        this.cover = cover;
        this.releaseDate = releaseDate;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String director) {
        this.popularity = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_movie);
        dest.writeString(this.title);
        dest.writeString(this.popularity);
        dest.writeString(this.description);
        dest.writeString(this.poster);
        dest.writeString(this.cover);
        dest.writeString(this.releaseDate);
    }

    protected Movie(Parcel in) {
        this.id_movie = in.readString();
        this.title = in.readString();
        this.popularity = in.readString();
        this.description = in.readString();
        this.poster = in.readString();
        this.cover = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
