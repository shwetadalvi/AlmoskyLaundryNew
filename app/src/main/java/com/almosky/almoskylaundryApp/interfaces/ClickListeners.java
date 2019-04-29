package com.almosky.almoskylaundryApp.interfaces;

import android.view.View;

public interface ClickListeners {
    interface CategoryItemEvents<T> {
        void onClickedEdit(T items);

        void onDeletedItem(T items);
    }

    interface ItemClick<T> {
        void onClickedItem(T item);
    }

    interface ItemClickWithView<T>{
        void onClickedItem(View view, T item);
    }
    interface TimeClickView<T>{
        void onClickedTime(View view, T item);
    }

}
