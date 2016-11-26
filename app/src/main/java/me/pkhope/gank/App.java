package me.pkhope.gank;

import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;

/**
 * Created by pkhope on 16/11/26.
 */

public class App extends Application {

    private static LiteOrm liteOrm = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (liteOrm == null){
            DataBaseConfig config = new DataBaseConfig(this, "ganks.db");
            config.dbVersion = 1;
            config.onUpdateListener = null;
            liteOrm = LiteOrm.newSingleInstance(config);
            liteOrm.setDebugged(true);
        }
    }

    public static LiteOrm getDB(){
        return liteOrm;
    }
}
