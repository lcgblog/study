package introspector;

import java.beans.PropertyEditorSupport;

public class StringToIntegerEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println("setAsText" + text);
        setValue(Integer.valueOf(text));
    }
}
