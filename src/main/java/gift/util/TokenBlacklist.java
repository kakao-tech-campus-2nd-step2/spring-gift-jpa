package gift.util;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklist {
    private final Set<String> blacklist = new HashSet<>();

    public void add(String token) {
        blacklist.add(token);
    }

    public boolean contains(String token) {
        return blacklist.contains(token);
    }
}
