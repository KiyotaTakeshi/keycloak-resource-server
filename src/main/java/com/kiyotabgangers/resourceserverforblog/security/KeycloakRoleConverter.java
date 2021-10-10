package com.kiyotabgangers.resourceserverforblog.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // access_token payload
        // @see https://jwt.io/#debugger-io?token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ1clFyczhPSDZzOU9rUDlMWmdfMk1FZmxYZGpQRFI0aENjVkhNWk5pbHNVIn0.eyJleHAiOjE2MzM4NDcyMTYsImlhdCI6MTYzMzg0NjkxNiwiYXV0aF90aW1lIjoxNjMzODQ2ODk4LCJqdGkiOiI2MGFlOGQ5Ny1kODM3LTQ1YzctYWY0MC05MTQ4MGU0YzNjYzIiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDAvYXV0aC9yZWFsbXMvc2FtcGxlIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImUxYTBmMzkwLTMzZGMtNDNjOS04NTNlLTQyOTUwNGNmY2E4YSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNhbXBsZSIsInNlc3Npb25fc3RhdGUiOiJkMTcwMDZiMC1lMzYzLTRmNzktOTliMy01NzZhNWQxYTFiYTIiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGV2ZWxvcGVyIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzYW1wbGUifQ.kxAkI0xntE3SxwqpkZDOaxljk-Klb8aT2UNzwEGUselTxpnks1SWkHWKc8WsFxLAwj49Y1z-Kxe3h5Vd0gLQyiFigsnVUaK9cAlNE5usRlNpECR1tJE3O_i-XM5n4F_lsy9X-sXWBv1fGTuH5ZyHDLhQ81Ot9PRnh_JTt_LSnsRLqsfC3VnESp3GWSeqX4qDFFzw_mNg6vX_fgHH12pmtg_-cAz2ea4pu86y9WcWCusM7dwbXkdPWjzzMKkTlyQjMLHfr_HBJnU7RbHlVtaZaZElfPinaVTTTzmNhXrDpsUvoO3FULAVcWvXaIj-5UspFmkET7u-8tjNT5JCnjxpew&publicKey=%7B%0A%20%20%22e%22%3A%20%22AQAB%22%2C%0A%20%20%22kty%22%3A%20%22RSA%22%2C%0A%20%20%22n%22%3A%20%22lBjZ18ea3Q5GzifFuM-HYGoE2KHLMekaKtchbSQcmlZ9XNDDM6vFPVJe_rTFjtigScImextTlkHQ98SmG73HV2zNoE91ZGbRCc7oFJe9VFOWio7qysdVkaKdUJMgv3mvDwHdln1g94eooAOnn35454gFNHKWub1rlqC4StQ6GNy43oPkUXhBp35AZXP4PdMfiTnIDTS-szX7fdeaDxXNE3AfBSpbj9hku4nonId8J8Npg14o_KSir_I8_kNHcN-Q8-NC5tpD1es9ju55JOBK1HtfskJo1-2-635t0z9gx2b5Ngi6fRgrlWF-qAOrchcXd-93ekw4xgcdzZYWg5Lm5Q%22%0A%7D
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }
        Collection<GrantedAuthority> returnValue =
                ((List<String>) realmAccess.get("roles"))
                        .stream().map(roleName -> "ROLE_" + roleName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return returnValue;
    }
}
