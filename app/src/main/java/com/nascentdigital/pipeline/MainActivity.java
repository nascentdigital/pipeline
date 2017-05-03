package com.nascentdigital.pipeline;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] strings = new String[] {
                "foo",
                "bar"
        };
        String[] some = Pipeline.from(strings)
                .where(s -> s.startsWith("f"))
                .toArray(String.class);
        if (some.length > 0) {

        }
    }
}
