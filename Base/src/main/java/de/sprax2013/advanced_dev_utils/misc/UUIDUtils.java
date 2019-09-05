package de.sprax2013.advanced_dev_utils.misc;

import java.util.UUID;

public class UUIDUtils {
    /**
     * Add dashes (<code>-</code>) to a UUID-String<br>
     * <i>Example 1 (already has dashes):
     * <code>407b28ed-e7bd-4516-93d9-3361fecb7889 -> 407b28ed-e7bd-4516-93d9-3361fecb7889</code></i><br>
     * <i>Example 2 (wrong placed dashes):
     * <code>-407b28ede7bd451693d93361fecb78-89 -> 407b28ed-e7bd-4516-93d9-3361fecb7889</code></i><br>
     * <i>Example 3 (no dashes):
     * <code>407b28ede7bd451693d93361fecb7889 -> 407b28ed-e7bd-4516-93d9-3361fecb7889</code></i><br>
     *
     * @param uuid The UUID as String
     *
     * @return The UUID with dashes (<code>-</code>) as String
     */
    public static String addDashesToUUID(String uuid) {
        return UUID.fromString(uuid.replace("-", "").replaceFirst(
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"))
                .toString();
    }
}