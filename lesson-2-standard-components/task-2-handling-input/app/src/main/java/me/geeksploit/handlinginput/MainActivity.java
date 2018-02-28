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

        InputValidator.InputCondition checkMaxLength = new InputValidator.InputCondition() {
            @Override
            public String evaluate(String input) {
                final int MAX_LENGTH = 10;
                if (input.length() > MAX_LENGTH)
                    return getString(R.string.input_error_length_long, MAX_LENGTH);
                else
                    return null;
            }
        };
    }

    private static final class InputValidator implements TextView.OnEditorActionListener {

        private static final String ERROR_MESSAGES_DELIMITER = " / ";
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

            String input = v.getText().toString();

            StringBuilder errorMessage = new StringBuilder();
            for (InputCondition c : conditions)
                buildMessage(errorMessage, c.evaluate(input));

            textInputLayouts.get(v).setError(errorMessage.toString().trim());

            return false;
        }

        private void buildMessage(StringBuilder sb, String messagePart) {
            if (messagePart == null) return;
            if (sb.length() > 0) sb.append(ERROR_MESSAGES_DELIMITER);

            sb.append(messagePart);
        }

        interface InputCondition {
            String evaluate(String input);
        }
    }
}
