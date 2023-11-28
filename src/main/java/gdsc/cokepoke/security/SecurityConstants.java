package gdsc.cokepoke.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 700000;
    static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String JWT_SECRET = Encoders.BASE64.encode(key.getEncoded());


}
