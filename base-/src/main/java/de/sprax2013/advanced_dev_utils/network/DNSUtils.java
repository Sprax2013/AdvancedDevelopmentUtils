package de.sprax2013.advanced_dev_utils.network;

import org.xbill.DNS.*;

public class DNSUtils {
    /**
     * Looks up a domain's DNS SRV-Record.
     *
     * @param service  the service (example: SSH, TS3, Minecraft, ...)
     * @param protocol The Protocol (example: UDP, TCP)
     *
     * @return the string
     */
    public static String lookupDNSRecord_SRV(String domain, String service, String protocol) {
        String query = "_" + service + "._" + protocol + "." + domain;

        try {
            Record[] records = new Lookup(query, Type.SRV).run();

            if (records != null) {
                for (Record record : records) {
                    SRVRecord srv = (SRVRecord) record;

                    String hostname = srv.getTarget().toString().replaceFirst("\\.$", "");
                    int port = srv.getPort();

                    return hostname + ":" + port;
                }
            }
        } catch (TextParseException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}