package de.sprax2013.advanced_dev_utils.misc;

import java.lang.reflect.Field;

public class ReflectionUtils {
    /**
     * Gets an {@link Field} and sets it <i>Accessible</i>
     *
     * @param obj  The Object that contains the field to get the value from
     * @param name The name of the field
     *
     * @return The field or null if failed. <b>Keep in mind that the value of the
     * field could also be null.</b>
     */
    public static Field getField(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);

            return field;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}