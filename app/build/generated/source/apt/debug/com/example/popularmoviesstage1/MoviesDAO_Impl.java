package com.example.popularmoviesstage1;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MoviesDAO_Impl implements MoviesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMovie;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMovie;

  public MoviesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovie = new EntityInsertionAdapter<Movie>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Movie`(`movieId`,`id`,`title`,`overview`,`image`,`vote_average`,`release_date`,`tr1`,`tr2`,`tr3`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Movie value) {
        stmt.bindLong(1, value.movieId);
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getOverview() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getOverview());
        }
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImage());
        }
        if (value.getVote_average() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getVote_average());
        }
        if (value.getRelease_date() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getRelease_date());
        }
        if (value.getTr1() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getTr1());
        }
        if (value.getTr2() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getTr2());
        }
        if (value.getTr3() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getTr3());
        }
      }
    };
    this.__preparedStmtOfDeleteMovie = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Movie WHERE image = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertMovie(Movie movie) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovie.insert(movie);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteMovie(String id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMovie.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteMovie.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Movie>> getAllMovies() {
    final String _sql = "Select * from Movie";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Movie>>() {
      private Observer _observer;

      @Override
      protected List<Movie> compute() {
        if (_observer == null) {
          _observer = new Observer("Movie") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfMovieId = _cursor.getColumnIndexOrThrow("movieId");
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
          final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
          final int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("vote_average");
          final int _cursorIndexOfReleaseDate = _cursor.getColumnIndexOrThrow("release_date");
          final int _cursorIndexOfTr1 = _cursor.getColumnIndexOrThrow("tr1");
          final int _cursorIndexOfTr2 = _cursor.getColumnIndexOrThrow("tr2");
          final int _cursorIndexOfTr3 = _cursor.getColumnIndexOrThrow("tr3");
          final List<Movie> _result = new ArrayList<Movie>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Movie _item;
            _item = new Movie();
            _item.movieId = _cursor.getInt(_cursorIndexOfMovieId);
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item.setTitle(_tmpTitle);
            final String _tmpOverview;
            _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            _item.setOverview(_tmpOverview);
            final String _tmpImage;
            _tmpImage = _cursor.getString(_cursorIndexOfImage);
            _item.setImage(_tmpImage);
            final String _tmpVote_average;
            _tmpVote_average = _cursor.getString(_cursorIndexOfVoteAverage);
            _item.setVote_average(_tmpVote_average);
            final String _tmpRelease_date;
            _tmpRelease_date = _cursor.getString(_cursorIndexOfReleaseDate);
            _item.setRelease_date(_tmpRelease_date);
            final String _tmpTr1;
            _tmpTr1 = _cursor.getString(_cursorIndexOfTr1);
            _item.setTr1(_tmpTr1);
            final String _tmpTr2;
            _tmpTr2 = _cursor.getString(_cursorIndexOfTr2);
            _item.setTr2(_tmpTr2);
            final String _tmpTr3;
            _tmpTr3 = _cursor.getString(_cursorIndexOfTr3);
            _item.setTr3(_tmpTr3);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public Movie getSingleMovie(String id) {
    final String _sql = "SELECT * FROM Movie WHERE image = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfMovieId = _cursor.getColumnIndexOrThrow("movieId");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfOverview = _cursor.getColumnIndexOrThrow("overview");
      final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
      final int _cursorIndexOfVoteAverage = _cursor.getColumnIndexOrThrow("vote_average");
      final int _cursorIndexOfReleaseDate = _cursor.getColumnIndexOrThrow("release_date");
      final int _cursorIndexOfTr1 = _cursor.getColumnIndexOrThrow("tr1");
      final int _cursorIndexOfTr2 = _cursor.getColumnIndexOrThrow("tr2");
      final int _cursorIndexOfTr3 = _cursor.getColumnIndexOrThrow("tr3");
      final Movie _result;
      if(_cursor.moveToFirst()) {
        _result = new Movie();
        _result.movieId = _cursor.getInt(_cursorIndexOfMovieId);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _result.setTitle(_tmpTitle);
        final String _tmpOverview;
        _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
        _result.setOverview(_tmpOverview);
        final String _tmpImage;
        _tmpImage = _cursor.getString(_cursorIndexOfImage);
        _result.setImage(_tmpImage);
        final String _tmpVote_average;
        _tmpVote_average = _cursor.getString(_cursorIndexOfVoteAverage);
        _result.setVote_average(_tmpVote_average);
        final String _tmpRelease_date;
        _tmpRelease_date = _cursor.getString(_cursorIndexOfReleaseDate);
        _result.setRelease_date(_tmpRelease_date);
        final String _tmpTr1;
        _tmpTr1 = _cursor.getString(_cursorIndexOfTr1);
        _result.setTr1(_tmpTr1);
        final String _tmpTr2;
        _tmpTr2 = _cursor.getString(_cursorIndexOfTr2);
        _result.setTr2(_tmpTr2);
        final String _tmpTr3;
        _tmpTr3 = _cursor.getString(_cursorIndexOfTr3);
        _result.setTr3(_tmpTr3);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
