package com.sportspartner.util.gcm_notification;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by yujiaxiao on 11/3/17.
 */

public class MyInstanceIDService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        refreshAllTokens();
    }



    private void refreshAllTokens() {
   /*     // assuming you have defined TokenList as
        // some generalized store for your tokens
        ArrayList<TokenList> tokenList = TokensList.get();
        InstanceID iid = InstanceID.getInstance(this);
        for(tokenItem : tokenList) {
            tokenItem.token = iid.getToken(tokenItem.authorizedEntity,tokenItem.scope,tokenItem.options);
            // send this tokenItem.token to your server*/
        }

}
