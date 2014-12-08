package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;

public interface ConnectedApiClient {
    void invoke(GoogleApiClient gac);
}
