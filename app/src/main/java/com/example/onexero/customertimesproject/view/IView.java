package com.example.onexero.customertimesproject.view;

import android.database.Cursor;

public interface IView {
void setAdapterCursor(Cursor cursor);
    void showMessage(String msg);
}
