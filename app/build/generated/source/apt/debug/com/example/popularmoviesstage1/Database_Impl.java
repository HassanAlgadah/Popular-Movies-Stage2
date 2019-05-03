package com.example.popularmoviesstage1;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class Database_Impl extends Database {
  private volatile MoviesDAO _moviesDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Movie` (`movieId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id` TEXT, `title` TEXT, `overview` TEXT, `image` TEXT, `vote_average` TEXT, `release_date` TEXT, `tr1` TEXT, `tr2` TEXT, `tr3` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"8dca8aa7ec1c4c940ed5102188acded5\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Movie`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMovie = new HashMap<String, TableInfo.Column>(10);
        _columnsMovie.put("movieId", new TableInfo.Column("movieId", "INTEGER", true, 1));
        _columnsMovie.put("id", new TableInfo.Column("id", "TEXT", false, 0));
        _columnsMovie.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsMovie.put("overview", new TableInfo.Column("overview", "TEXT", false, 0));
        _columnsMovie.put("image", new TableInfo.Column("image", "TEXT", false, 0));
        _columnsMovie.put("vote_average", new TableInfo.Column("vote_average", "TEXT", false, 0));
        _columnsMovie.put("release_date", new TableInfo.Column("release_date", "TEXT", false, 0));
        _columnsMovie.put("tr1", new TableInfo.Column("tr1", "TEXT", false, 0));
        _columnsMovie.put("tr2", new TableInfo.Column("tr2", "TEXT", false, 0));
        _columnsMovie.put("tr3", new TableInfo.Column("tr3", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMovie = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMovie = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMovie = new TableInfo("Movie", _columnsMovie, _foreignKeysMovie, _indicesMovie);
        final TableInfo _existingMovie = TableInfo.read(_db, "Movie");
        if (! _infoMovie.equals(_existingMovie)) {
          throw new IllegalStateException("Migration didn't properly handle Movie(com.example.popularmoviesstage1.Movie).\n"
                  + " Expected:\n" + _infoMovie + "\n"
                  + " Found:\n" + _existingMovie);
        }
      }
    }, "8dca8aa7ec1c4c940ed5102188acded5", "1591b9aa4c5d2fe9fac0943709858da2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Movie");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Movie`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MoviesDAO moviesDAO() {
    if (_moviesDAO != null) {
      return _moviesDAO;
    } else {
      synchronized(this) {
        if(_moviesDAO == null) {
          _moviesDAO = new MoviesDAO_Impl(this);
        }
        return _moviesDAO;
      }
    }
  }
}
