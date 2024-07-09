package gift.service;


import gift.model.Gift;
import gift.model.GiftRequest;
import gift.model.GiftResponse;
import gift.repository.GiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GiftService {

    private final GiftRepository giftRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public List<GiftResponse> getAllGifts() {
        List<Gift> gifts = giftRepository.findAll();
        return gifts.stream()
                .map(GiftResponse::from)
                .collect(Collectors.toList());
    }

    public GiftResponse getGift(Long id) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gift not found with id " + id));
        return GiftResponse.from(gift);
    }


    public void addGift(GiftRequest giftRequest) {
        Gift gift = giftRequest.toEntity();
        giftRepository.save(gift);
    }

    @Transactional
    public void updateGift(GiftRequest giftReq,Long id) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gift not found with id " + id));
        gift.modifyGift(giftReq.getName(),giftReq.getPrice(),giftReq.getImageUrl());
        giftRepository.save(gift);
    }

    public void deleteGift(Long id) {
        giftRepository.deleteById(id);
    }
}


