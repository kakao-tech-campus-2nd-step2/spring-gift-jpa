package gift.service;

import gift.exception.CustomException.ItemNotFoundException;
import gift.exception.ErrorCode;
import gift.model.item.Item;
import gift.model.item.ItemDTO;
import gift.model.item.ItemForm;
import gift.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Long insertItem(ItemForm form) {
        Item item = new Item(0L, form.getName(), form.getPrice(), form.getImgUrl());
        return itemRepository.save(item).getId();
    }

    @Transactional(readOnly = true)
    public ItemDTO findItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(
            ErrorCode.ITEM_NOT_FOUND));
        return new ItemDTO(item.getId(), item.getName(), item.getPrice(), item.getImgUrl());
    }

    @Transactional(readOnly = true)
    public Page<ItemDTO> getList(Pageable pageable) {
        Page<Item> list = itemRepository.findAllByOrderByIdDesc(pageable);
        return list.map(Item::toItemDTO);
    }

    @Transactional
    public Long updateItem(ItemDTO itemDTO) {
        Item item = new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice(),
            itemDTO.getImgUrl());
        return itemRepository.save(item).getId();
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
