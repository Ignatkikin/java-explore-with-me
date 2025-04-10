package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.CategoryRequestDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.CategoryService;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toCategory(categoryRequestDto);
        CategoryDto categoryDto = categoryMapper.toCategoryDto(categoryRepository.save(category));
        log.info("Category с id {} успешно создана", categoryDto.getId());
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryRequestDto categoryRequestDto, Long categoryId) {
        Category oldCategory = checkCategory(categoryId);
        oldCategory.setName(categoryRequestDto.getName());
        CategoryDto categoryDto = categoryMapper.toCategoryDto(categoryRepository.save(oldCategory));
        log.info("Category с id {} успешно обновлена", categoryDto.getId());
        return categoryDto;
    }

    @Override
    public void delete(Long categoryId) {
        checkCategory(categoryId);
        categoryRepository.deleteById(categoryId);
        log.info("Category с id {} успешно удалена", categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.getContent().stream().map(categoryMapper::toCategoryDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long categoryId) {
        return categoryMapper.toCategoryDto(checkCategory(categoryId));
    }

    private Category checkCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category с id " + categoryId + "не найдена"));
    }
}
