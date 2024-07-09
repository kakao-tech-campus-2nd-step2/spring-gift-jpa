package gift.service;

import gift.dto.WishDto;
import gift.entity.Wish;
import gift.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void save(WishDto request) {

        //1. 토큰 -> id 복호화
        long id = translateIdFromtoken(request.getToken());

        return wishRepository.save(request.getProductId(), id);

    }

    private long translateIdFromtoken(String token) {

    }

    public List<WishDto> getAll(String userId) {

        List<Wish> wishes = wishRepository.getAll(userId);

        List<WishDto> wishDtos = wishes.stream().map(WishDto::fromEntity).toList();
        return wishDtos;
    }

    //delete 보통 크게응답안하면 void로 하기도함
    public void delete(long id, String token) {
        wishRepository.delete(id, token);
    }
}