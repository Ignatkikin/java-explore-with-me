package ru.practicum.event.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public class EventSpecifications {

    public static Specification<Event> isPublished() {
        return (root, query, cb) -> cb.equal(root.get("state"), EventState.PUBLISHED);
    }

    public static Specification<Event> textInAnnotationOrDescription(String text) {
        return (root, query, cb) -> {
            String likeText = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("annotation")), likeText),
                    cb.like(cb.lower(root.get("description")), likeText)
            );
        };
    }

    public static Specification<Event> inCategories(List<Long> categories) {
        return (root, query, cb) -> root.get("category").get("id").in(categories);
    }

    public static Specification<Event> isPaid(Boolean paid) {
        return (root, query, cb) -> cb.equal(root.get("paid"), paid);
    }

    public static Specification<Event> rangeStart(LocalDateTime start) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("eventDate"), start);
    }

    public static Specification<Event> rangeEnd(LocalDateTime end) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("eventDate"), end);
    }

    public static Specification<Event> onlyAvailable() {
        return (root, query, cb) -> cb.or(
                cb.equal(root.get("participantLimit"), 0),
                cb.gt(cb.diff(root.get("participantLimit"), root.get("confirmedRequests")), 0)
        );
    }

    public static Specification<Event> initiators(List<Long> userIds) {
        return (root, query, cb) -> root.get("initiator").get("id").in(userIds);
    }

    public static Specification<Event> statesIn(List<EventState> states) {
        return (root, query, cb) -> root.get("state").in(states);
    }
}
