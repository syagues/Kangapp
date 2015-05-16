package projecte.kangapp;

import android.content.SearchRecentSuggestionsProvider;

public class MyContentProvider extends SearchRecentSuggestionsProvider{
    public final static String AUTHORITY = "com.infotech.provider";
    public final static int MODE = DATABASE_MODE_QUERIES;




    public MyContentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }


}
