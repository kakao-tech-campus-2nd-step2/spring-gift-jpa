package gift.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addGiftToUser(Long userId, Long giftId, int quantity) {
        String query = "INSERT INTO user_gifts (user_id, gift_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, userId, giftId, quantity);

    }

    public void removeGiftFromUser(Long userId, Long giftId) {
        String query = "DELETE FROM user_gifts WHERE user_id = ? AND gift_id = ?";
        jdbcTemplate.update(query, userId, giftId);
    }

    public List<UserGift> getGiftsForUser(Long userId) {
        String query = "SELECT * FROM user_gifts WHERE user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, new BeanPropertyRowMapper<>(UserGift.class));
    }
}
