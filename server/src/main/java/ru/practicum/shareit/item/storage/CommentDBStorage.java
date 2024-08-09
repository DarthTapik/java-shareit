package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDBStorage {
    private final CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getAllItemComment(Long itemId) {
        return commentRepository.findByItemId(itemId);
    }
}
