package byc.avt.movieappjetpack.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import byc.avt.movieappjetpack.model.Movie;

@Dao
public interface MovieDao {
    @Insert
    List<Long> insertAll(Movie... movies);

    @Query("SELECT * FROM movie")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM movie WHERE uId = :id")
    Movie getMovie(int id);

    @Query("DELETE FROM movie")
    void deleteAllMovie();
}
