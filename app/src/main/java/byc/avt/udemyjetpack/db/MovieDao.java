package byc.avt.udemyjetpack.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import byc.avt.udemyjetpack.model.Movie;

@Dao
public interface MovieDao {
    @Insert
    List<Integer> insertAll(Movie... movies);

    @Query("SELECT * FROM movie")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM movie WHERE uId = :id")
    Movie getMovie(int id);

    @Query("DELETE FROM movie")
    void deleteAllMovie();
}
