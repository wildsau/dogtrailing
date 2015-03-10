package de.wildsau.dogtrailing;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import de.wildsau.dogtrailing.entities.DaoMaster;
import de.wildsau.dogtrailing.entities.DaoSession;

/**
 * Created by becker on 10.03.2015.
 */
public class DogTrailingApplication extends Application {


    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    private void setupDatabase() {
        //TODO: Do not always Dropit!
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dogtrailing-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
