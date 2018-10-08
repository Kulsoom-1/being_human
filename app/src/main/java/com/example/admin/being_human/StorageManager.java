package com.example.admin.being_human;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kulsoom on 11-Jan-18.
 */

public class StorageManager {
    SharedPreferences _preference;
    Context _context;
    SharedPreferences.Editor _editor;

    public StorageManager(Context context) {
        this._context = context;
        _preference = PreferenceManager.getDefaultSharedPreferences(this._context);
    }

    public void saveData(String key, String value) {
        _editor = _preference.edit();
        _editor.putString(key, value);
        _editor.commit();
    }

    public String getData(String key) {
        return _preference.getString(key, null);
    }
}
