package com.pz.supportchat.utils;

public class StringUtils {


    // For some reason in version 4.10 Smack authors decided to remove this helper methods,
    // and provided no known to me alternative.
    // Since they are quite useful I migrated them from version 4.0.4.

    public static String parseBareAddress(final String xmppAddress) {
        if (xmppAddress == null) {
            return null;
        }

        int slashIndex = xmppAddress.indexOf("/");
        if (slashIndex < 0) {
            return xmppAddress;
        } else if (slashIndex == 0) {
            return "";
        } else {
            return xmppAddress.substring(0, slashIndex);
        }
    }
}
