package gift.service;


import gift.dto.PagingResponse;
import gift.model.gift.*;
import gift.repository.GiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public PagingResponse<GiftResponse> getAllGifts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<Gift> gifts = giftRepository.findAll(pageRequest);
        List<GiftResponse> giftResponses = gifts.stream()
                .map(GiftResponse::from)
                .collect(Collectors.toList());
        return new PagingResponse<>(page, giftResponses, size, gifts.getTotalElements(), gifts.getTotalPages());
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
    public void updateGift(GiftRequest giftReq, Long id) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gift not found with id " + id));
        gift.modify(giftReq.getName(), giftReq.getPrice(), giftReq.getImageUrl());
        giftRepository.save(gift);
    }


    public void deleteGift(Long id) {
        giftRepository.deleteById(id);
    }
}


