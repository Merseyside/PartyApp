package com.pchmn.materialchips.views;


import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.AppCompatEditText;

public class ChipsInputEditText extends AppCompatEditText {

    private FilterableListView filterableListView;

    public ChipsInputEditText(Context context) {
        super(context);
        //initView();
    }

    public ChipsInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initView();
    }

    private void initView() {
        setSingleLine(false);
        setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        setMinLines(2);
        setMaxLines(2);
    }

    public boolean isFilterableListVisible() {
        return filterableListView != null && filterableListView.getVisibility() == VISIBLE;
    }

    public FilterableListView getFilterableListView() {
        return filterableListView;
    }

    public void setFilterableListView(FilterableListView filterableListView) {
        this.filterableListView = filterableListView;
    }
}
