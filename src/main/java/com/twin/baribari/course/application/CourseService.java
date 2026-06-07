package com.twin.baribari.course.application;

import com.twin.baribari.course.application.dto.PinRequest;
import com.twin.baribari.course.domain.Course;
import com.twin.baribari.course.domain.CourseRepository;
import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.domain.Pins;

import java.util.List;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Course upload(
        final String imageUrl,
        final String title,
        final String description,
        final List<PinRequest> pinRequests
    ) {
        final List<Pin> pins = IntStream.range(0, pinRequests.size())
            .mapToObj(i -> new Pin(pinRequests.get(i).latitude(), pinRequests.get(i).longitude(), i))
            .toList();

        final Course course = new Course(imageUrl, title, description, new Pins(pins));
        return courseRepository.save(course);
    }
}
