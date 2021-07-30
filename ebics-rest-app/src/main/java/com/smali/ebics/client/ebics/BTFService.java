package com.smali.ebics.client.ebics;

import org.ebics.client.order.EbicsService;

public class BTFService extends EbicsService {

    public BTFService(String serviceName, String serviceOption, String scope, String containerType, String messageName,
            String messageNameVariant, String messageNameVersion, String messageNameFormat) {
        super(serviceName, serviceOption, scope, containerType, messageName, messageNameVariant, messageNameVersion,
                messageNameFormat);
    }

    public BTFService() {
        super("SCT", null, null, null, "pain.001", null, null, null);
    }

    @Override
    public String toString() {
        return "BTFService []";
    }    
    
}
