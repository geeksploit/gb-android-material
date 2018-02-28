package me.geeksploit.handlinginput;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static final class InputValidator implements TextView.OnEditorActionListener {

        private List<InputCondition> conditions = new ArrayList<>();
        private Map<TextView, TextInputLayout> textInputLayouts = new HashMap<>();

        InputValidator(InputCondition... conditions) {
            this.conditions.addAll(Arrays.asList(conditions));
        }

        void attach(TextInputLayout layout) {
            EditText editText = layout.getEditText();
            if (editText != null) {
                textInputLayouts.put(editText, layout);
                editText.setOnEditorActionListener(this);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            return false;
        }

        interface InputCondition {
            String evaluate(String input);
        }
    }
}
