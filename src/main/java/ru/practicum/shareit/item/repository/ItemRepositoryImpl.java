package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item create(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> getById(long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public Item update(long itemId, Item item) {
        items.put(itemId, item);
        return item;
    }

    @Override
    public List<ItemDto> allItemsFromUser(long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .map(ItemMapper::itemToDto)
                .toList();
    }

    @Override
    public List<ItemDto> search(String text) {
        return items.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase())
                                || item.getName().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::itemToDto)
                .toList();
    }
}
