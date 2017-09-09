package софия.ту.сървис;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;

/**
 * Съдържа методи, които на базата на математически операции подържат
 * актулизиран списъкa с потребителите на приложението
 * 
 * @author Иван Колев
 *
 */
public class СървисАктивниПотребители {

    private LoadingCache<String, UserStats> statsByUser = CacheBuilder.newBuilder()
	    .build(new CacheLoader<String, UserStats>() {

		@Override
		public UserStats load(String key) throws Exception {
		    return new UserStats();
		}

	    });

    /**
     * Маркира потребител. Ако даден е маркиран то тои се смята за активен в
     * момента.
     * 
     * @param потребителскоИме
     */
    public void маркирай(String потребителскоИме) {
	statsByUser.getUnchecked(потребителскоИме).mark();
    }

    /**
     * 
     * Проверява за всеки потребител дали разликата между текущият момент и
     * последният му вход е по малка от пет секунди. Ако не е даденият потребител се
     * маркира като неактивен.
     * 
     * @return Множество от потребители
     */
    public Set<String> вземиАктивнитеПотребители() {
	Set<String> active = Sets.newTreeSet();
	for (String user : statsByUser.asMap().keySet()) {
	    // has the user checked in within the last 5 seconds?
	    if ((System.currentTimeMillis() - statsByUser.getUnchecked(user).lastAccess()) < 5000) {
		active.add(user);
	    }
	}
	return active;
    }

    private static class UserStats {

	private AtomicLong lastAccess = new AtomicLong(System.currentTimeMillis());

	private void mark() {
	    lastAccess.set(System.currentTimeMillis());
	}

	private long lastAccess() {
	    return lastAccess.get();
	}

    }

}
