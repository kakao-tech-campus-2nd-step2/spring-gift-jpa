package gift.service;


import gift.model.Gift;
import gift.model.GiftDao;
import gift.model.GiftRequest;
import gift.model.GiftResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftService {

    private final GiftDao giftDao;

    public GiftService(GiftDao giftDao) {
        this.giftDao = giftDao;
    }

    public List<GiftResponse> getAllGifts() {
        List<Gift> gifts = giftDao.findAll();
        return gifts.stream()
                .map(GiftResponse::from)
                .collect(Collectors.toList());
    }

    public GiftResponse getGift(Long id) {
        Gift gift = giftDao.findById(id);
        return GiftResponse.from(gift);
    }


    public void addGift(GiftRequest giftRequest) {
        Gift gift = giftRequest.toEntity();
        giftDao.create(gift);
    }

    public void updateGift(GiftRequest giftReq,Long id) {
        Gift gift = giftReq.toEntity(id);
        giftDao.updateById(gift,id);
    }

    public void deleteGift(Long id) {
        giftDao.deleteById(id);
    }
}

