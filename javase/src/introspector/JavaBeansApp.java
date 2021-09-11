package introspector;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class JavaBeansApp {

    public static void main(String[] args)throws Exception {
        Person person = new Person();
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor -> {
            if ("age".equals(propertyDescriptor.getName())){
                propertyDescriptor.setPropertyEditorClass(StringToIntegerEditor.class);
                PropertyEditor editor = propertyDescriptor.createPropertyEditor(person);
                editor.addPropertyChangeListener(new WriterPropertyListener(person, propertyDescriptor.getWriteMethod()));
                editor.setAsText("123");
            }
        });

        System.out.println(person.getAge());
    }


    static class WriterPropertyListener implements PropertyChangeListener{

        private final Object bean;
        private final Method setter;

        public WriterPropertyListener(Object bean, Method setter) {
            this.bean = bean;
            this.setter = setter;
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            PropertyEditor pe = (PropertyEditor)evt.getSource();
            try {
                setter.invoke(bean, pe.getValue());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}

