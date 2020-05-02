package com.bz.blueauth.tools;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA3_256;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bz.blueauth.exception.AccessException;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptoTools {

    private String internalSecret;
    private int expireSecs;
    private Algorithm algorithm;

    private void setEnvVars() {       
        AppProperties prop = AppProperties.getInstance();
        this.internalSecret = prop.get("crypto.secret");
        this.expireSecs = Integer.parseInt(prop.get("crypto.expireSecs"));        
     }

    public CryptoTools(){
        this.setEnvVars();
        this.algorithm = Algorithm.HMAC256(this.internalSecret);
        this.internalSecret = this.internalSecret == null?"b-secret":this.internalSecret;
        this.expireSecs = this.expireSecs == 0?30:this.expireSecs;
    }

    public String jwtSign(String key, Map<String, ?> obj){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, this.expireSecs);
        
        String token = JWT.create()
                .withIssuer("blueAuth")
                .withClaim(key, obj)
                .withIssuedAt(now)
                .withExpiresAt(calendar.getTime())
                .sign(this.algorithm);

        return token;
    }    


    public Map<String, ?> jwtVerify(String key, String token){
        try {
                JWTVerifier verifier = JWT.require(this.algorithm)
                    .withIssuer("blueAuth")
                    .acceptExpiresAt(10)
                    .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(token);
                Claim keyClaim  = jwt.getClaim(key);
                return keyClaim.asMap();

        } catch (TokenExpiredException e) {
            throw new AccessException("Token Invalidated");
        }
    }

    public String jwtRefresh(String key, String token){
        Map<String, ?> res = this.jwtVerify(key, token);
        return this.jwtSign(key, res);
    }

    public String sha2Encprypt(String str){
        String sha3_256hex = new DigestUtils(SHA3_256).digestAsHex(str);
        return sha3_256hex;
    }

    /** 
    public static void main(String args[]){
        
            UserPO user = new UserPO("crtoledo","crtoledo@bluezinc.cl", "123456");

            System.out.println(user.omit(Arrays.asList("password")));
            
        
    }

    **/
}