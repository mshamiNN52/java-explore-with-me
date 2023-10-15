package ru.practicum.mainservice.mapper;

import javax.annotation.processing.Generated;
import ru.practicum.mainservice.dto.category.CategoryDto;
import ru.practicum.mainservice.dto.category.NewCategoryDto;
import ru.practicum.mainservice.model.Category;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-15T16:17:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toModel(NewCategoryDto newCategoryDto) {
        if ( newCategoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( newCategoryDto.getName() );

        return category.build();
    }

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    @Override
    public Category forUpdate(CategoryDto categoryDto, Category category) {
        if ( categoryDto == null ) {
            return category;
        }

        if ( categoryDto.getName() != null ) {
            category.setName( categoryDto.getName() );
        }

        return category;
    }
}
